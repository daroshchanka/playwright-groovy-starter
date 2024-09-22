package github.daroshchanka.playwrightstarter.core.web

import com.microsoft.playwright.Browser
import com.microsoft.playwright.BrowserType
import com.microsoft.playwright.Playwright

class PlaywrightLauncher {

  static Browser newBrowser(String browserName, BrowserType.LaunchOptions launchOptions) {
    Playwright playwright = Playwright.create()
    BrowserType browserType = switch (browserName) {
      case 'firefox' -> playwright.firefox()
      case 'webkit' -> playwright.webkit()
      default -> playwright.chromium()
    }
    browserType.launch(launchOptions)
  }
}
