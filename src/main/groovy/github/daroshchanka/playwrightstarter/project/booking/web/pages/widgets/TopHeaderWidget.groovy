package github.daroshchanka.playwrightstarter.project.booking.web.pages.widgets

import github.daroshchanka.playwrightstarter.core.web.BaseWebPage
import github.daroshchanka.playwrightstarter.core.web.UiElement
import groovy.transform.InheritConstructors
import groovy.util.logging.Log4j2

@Log4j2
@InheritConstructors
class TopHeaderWidget extends BaseWebPage {

  private UiElement headerLogo = UiElement.byTestId('header-logo')
  private UiElement userCurrencyPickerIcon = UiElement.byTestId('header-currency-picker-trigger')
  private UiElement userCurrency = UiElement.byXpath("$userCurrencyPickerIcon//span")
  private UiElement userLanguagePickerIcon = UiElement.byTestId('header-language-picker-trigger')
  private UiElement signUpButton = UiElement.byTestId('header-sign-up-button')
  private UiElement signInButton = UiElement.byTestId('header-sign-in-button')
  private UiElement navigationMenu = UiElement.byTestId('header-xpb')
  private UiElement navigationMenuAccommodations = UiElement.byXpath("$userCurrencyPickerIcon//a[@id='accommodations']")
  private UiElement navigationMenuFlights = UiElement.byXpath("$userCurrencyPickerIcon//a[@id='flights']")
  private UiElement navigationMenuAttractions = UiElement.byXpath("$userCurrencyPickerIcon//a[@id='attractions']")
  private UiElement navigationMenuAirportTaxis = UiElement.byXpath("$userCurrencyPickerIcon//a[@id='airport_taxis']")

  boolean isLoaded() {
    boolean result = [headerLogo, userLanguagePickerIcon, navigationMenuAccommodations].every {
      it.isVisible(pofPage)
    }
    log.info("[TopHeaderWidget] Check if widget loaded - $result")
    result
  }
}
