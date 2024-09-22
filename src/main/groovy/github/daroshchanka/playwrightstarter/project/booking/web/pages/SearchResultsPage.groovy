package github.daroshchanka.playwrightstarter.project.booking.web.pages

import com.microsoft.playwright.Page
import github.daroshchanka.playwrightstarter.core.web.BaseWebPage
import github.daroshchanka.playwrightstarter.core.web.UiElement
import github.daroshchanka.playwrightstarter.project.booking.web.pages.widgets.SearchWidget
import github.daroshchanka.playwrightstarter.project.booking.web.pages.widgets.TopHeaderWidget
import groovy.transform.InheritConstructors
import groovy.util.logging.Log4j2

@Log4j2
@InheritConstructors
class SearchResultsPage extends BaseWebPage {

  private UiElement assertiveHeader = UiElement.byXpath("//h1[@aria-live='assertive']")
  private UiElement mapTrigger = UiElement.byTestId('map-trigger')
  private UiElement searchResultItem = UiElement.byTestId('property-card')
  private TopHeaderWidget topHeader
  private SearchWidget search

  SearchResultsPage(Page page) {
    super(page)
    topHeader = new TopHeaderWidget(page)
    search = new SearchWidget(page)
  }

  boolean isLoaded() {
    boolean result = [assertiveHeader, mapTrigger].every { it.isVisible(pofPage) }
    log.info("Check SearchResults page is loaded - $result")
    result
  }

  String getAssertiveHeaderText() {
    String result = assertiveHeader.getText(pofPage)
    log.info("Get assertive header text - $result")
    result
  }

  int getSearchResultCardsCount() {
    int result = searchResultItem.getList(pofPage).size()
    log.info("Get search results visible cards count - $result")
    result
  }

  SearchResultsPage doSearch(Map query) {
    search.doSearch(query)
    this
  }


}
