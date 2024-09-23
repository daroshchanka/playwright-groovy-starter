package github.daroshchanka.playwrightstarter.project.booking.web.pages

import com.microsoft.playwright.Page
import github.daroshchanka.playwrightstarter.core.web.BaseWebPage
import github.daroshchanka.playwrightstarter.core.web.UiElement
import github.daroshchanka.playwrightstarter.project.booking.web.pages.widgets.PopupHandleWidget
import github.daroshchanka.playwrightstarter.project.booking.web.pages.widgets.SearchWidget
import github.daroshchanka.playwrightstarter.project.booking.web.pages.widgets.TopHeaderWidget
import groovy.transform.InheritConstructors
import groovy.util.logging.Log4j2

import static github.daroshchanka.playwrightstarter.core.web.UiElement.byXpath

@Log4j2
@InheritConstructors
class LandingPage extends BaseWebPage {

  private UiElement heroBanner = byXpath("//div[contains(@class, 'hero-banner')]")
  private UiElement promoSection = byXpath("//div[contains(@class, 'promo-section')]")

  private TopHeaderWidget topHeader
  private SearchWidget search
  @Delegate
  private PopupHandleWidget popupHandler

  LandingPage(Page page) {
    super(page)
    topHeader = new TopHeaderWidget(page)
    search = new SearchWidget(page)
    popupHandler = new PopupHandleWidget(page)
  }

  LandingPage navigate() {
    log.info('Navigate to Landing page')
    page.navigate('/')
    handlePopup()
    waitForNetworkIdle()
    this
  }

  boolean isLoaded() {
    boolean result = [heroBanner, promoSection].every { it.isVisible(pofPage) }
    log.info("Check Landing page is loaded - $result")
    result
  }

  SearchResultsPage doSearch(Map query) {
    search.doSearch(query)
    handlePopup()
    new SearchResultsPage(page)
  }

}
