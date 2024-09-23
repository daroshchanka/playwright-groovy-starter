package github.daroshchanka.playwrightstarter.core.web

import com.microsoft.playwright.Browser
import com.microsoft.playwright.Browser.NewContextOptions
import com.microsoft.playwright.BrowserContext
import com.microsoft.playwright.BrowserType
import com.microsoft.playwright.Page
import github.daroshchanka.playwrightstarter.core.PlaywrightConfig
import groovy.util.logging.Log4j2
import org.testng.annotations.AfterTest
import org.testng.annotations.BeforeClass
import org.testng.annotations.BeforeMethod
import org.testng.annotations.BeforeTest

@Log4j2
class BaseWebTest {

  protected Browser browser
  protected BrowserContext browserContext
  protected Page page

  @BeforeTest
  void initBrowser() {
    browser = PlaywrightLauncher.newBrowser(withBrowserName(), withLaunchOptions())
  }

  @BeforeClass
  void initBrowserContext() {
    if (null != browserContext) {
      browserContext.close()
    }
    def options = withNewContextOptions()
    log.info("Launch BrowserContext with options ${newContextOptionsToString(options)}")
    browserContext = browser.newContext(options)
  }

  @AfterTest
  void closeBrowser() {
    browser.close()
  }

  @BeforeClass
  void initPage() {
    if (withNewContextPerClass()) {
      newPage()
    }
  }

  @BeforeMethod
  void initPageBeforeMethod() {
    if (withNewContextPerClass()) {
      return
    }
    initBrowserContext()
    newPage()
  }

  protected boolean withNewContextPerClass() {
    true
  }

  /**
   * Possible to override on the higher level
   * */
  protected withBrowserName() {
    PlaywrightConfig.browserName
  }

  /**
   * Possible to override on the higher level
   * */
  protected BrowserType.LaunchOptions withLaunchOptions() {
    PlaywrightConfig.launchOptions
  }

  /**
   * Possible to override on the higher level
   * */
  protected NewContextOptions withNewContextOptions() {
    PlaywrightConfig.newContextOptions
  }

  protected void newPage() {
    page = browserContext.newPage()
    PlaywrightSharedContext.setPage(page)
  }

  private static String newContextOptionsToString(NewContextOptions options) {
    try {
      options.getProperties().tap {
        remove('class')
        put('viewportSize', options?.viewportSize?.get()?.getProperties()?.tap { remove('class') })
      }.findAll { it.value != null }.toString()
    } catch (Exception ignored) {
      options.getProperties().findAll { it.value != null }.toString()
    }
  }
}
