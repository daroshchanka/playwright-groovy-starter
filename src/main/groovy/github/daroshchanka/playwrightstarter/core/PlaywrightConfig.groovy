package github.daroshchanka.playwrightstarter.core

import com.microsoft.playwright.Browser
import com.microsoft.playwright.BrowserType
import com.microsoft.playwright.options.ViewportSize
import groovy.transform.Memoized
import groovy.util.logging.Log4j2
import org.yaml.snakeyaml.Yaml

@Log4j2
class PlaywrightConfig {

  @Lazy
  private static Map config = new Yaml()
      .load(new File('target/test-classes/playwright-config.yaml').text) as Map

  @Memoized
  private static Map getBrowserConfigs() {
    def browserName = System.getProperty('playwright-browser')
    Map configs = config.browser.find {it.name == browserName } as Map
    log.info("[PlaywrightConfig] Load playwright-config.yaml: for [$browserName] - $configs")
    configs
  }

  @Memoized
  static String getBrowserName() {
    getBrowserConfigs().type
  }

  @Memoized
  static BrowserType.LaunchOptions getLaunchOptions() {
    Map options = getBrowserConfigs()['launch-options'] as Map
    options ? new BrowserType.LaunchOptions(options) : new BrowserType.LaunchOptions()
  }

  @Memoized
  static Browser.NewContextOptions getNewContextOptions() {
    //the power of Groovy metaprogramming, adding missing Map constructor to ViewportSize
    ViewportSize.metaClass.constructor = { Map input -> new ViewportSize(input.width as int, input.height as int) }
    Map options = getBrowserConfigs()['new-context-options'] as Map
    options ? new Browser.NewContextOptions(options) : new Browser.NewContextOptions()
  }

  @Memoized
  static Map getReportingConfigs() {
    config.reporting as Map
  }
}
