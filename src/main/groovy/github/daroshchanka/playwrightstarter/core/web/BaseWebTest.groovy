package github.daroshchanka.playwrightstarter.core.web

import com.microsoft.playwright.Browser
import com.microsoft.playwright.BrowserContext
import com.microsoft.playwright.BrowserType
import com.microsoft.playwright.Page
import github.daroshchanka.playwrightstarter.core.PlaywrightConfig
import github.daroshchanka.playwrightstarter.core.reporting.AllureTestListener
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
    browserContext = browser.newContext(withNewContextOptions())
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

  protected withBrowserName() {
    PlaywrightConfig.browserName
  }

  protected BrowserType.LaunchOptions withLaunchOptions() {
    PlaywrightConfig.launchOptions
  }

  protected Browser.NewContextOptions withNewContextOptions() {
    PlaywrightConfig.newContextOptions
  }

  protected void newPage() {
    page = browserContext.newPage()
    AllureTestListener.setPage(page)
  }

}
