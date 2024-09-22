package github.daroshchanka.playwrightstarter.core.web

import com.microsoft.playwright.Page
import com.microsoft.playwright.options.LoadState
import groovy.transform.Memoized
import groovy.util.logging.Log4j2

@Log4j2
class BaseWebPage {

  final Page page

  BaseWebPage(Page page) {
    this.page = page
  }

  BaseWebPage goTo(String url) {
    page.navigate(url)
    this
  }

  @Memoized
  protected PageOrFrame getPofPage() {
    new PageOrFrame(page)
  }

  void waitForNetworkIdle(int timeoutSec = 10) {
    log.debug("[Page] wait for state 'networkidle' timeoutSec: $timeoutSec")
    page.waitForLoadState(
        LoadState.NETWORKIDLE,
        new Page.WaitForLoadStateOptions(timeout: timeoutSec * 1000)
    )
  }

  void waitForDocumentLoaded(int timeoutSec = 10) {
    log.debug("[Page] wait for state 'domcontentloaded' timeoutSec: $timeoutSec")
    page.waitForLoadState(
        LoadState.DOMCONTENTLOADED,
        new Page.WaitForLoadStateOptions(timeout: timeoutSec * 1000)
    )
  }
}
