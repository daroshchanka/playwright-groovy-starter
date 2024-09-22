package github.daroshchanka.playwrightstarter.project.booking.web.pages.widgets

import github.daroshchanka.playwrightstarter.core.web.BaseWebPage
import github.daroshchanka.playwrightstarter.core.web.UiElement
import groovy.transform.InheritConstructors
import groovy.util.logging.Log4j2

import static github.daroshchanka.playwrightstarter.core.web.UiElement.byId
import static github.daroshchanka.playwrightstarter.core.web.UiElement.byTestId
import static github.daroshchanka.playwrightstarter.core.web.UiElement.byXpath

@Log4j2
@InheritConstructors
class SearchWidget extends BaseWebPage {

  private UiElement searchSubmitButton = byXpath("//div[(contains(@class, 'hero-banner-searchbox')) or " +
      "(@data-testid='searchbox-layout-wide')]//button[@type='submit']")
  private UiElement destinationContainer = byTestId('destination-container')
  private UiElement destinationInput = byXpath("$destinationContainer//input")

  private UiElement getDestinationAutocompleteResult(int number) {
    return byXpath("(//li[contains(@id, 'autocomplete-result-')])[$number]")
  }

  private UiElement datesContainer = byTestId('searchbox-dates-container')
  private UiElement datesContainerButton = byXpath("$datesContainer//button")
  private UiElement datesContainerCalendar = byId('calendar-searchboxdatepicker')

  private UiElement getDatesContainerCalendarMonth(int number) {
    return byXpath("($datesContainerCalendar//table)[$number]")
  }

  private UiElement getDatepickerDay(int dayNumber, int calendarMonth) {
    return byXpath("(${getDatesContainerCalendarMonth(calendarMonth)}//td[@role='gridcell']/span)[$dayNumber]")
  }

  private UiElement getFlexibleDaysOption(option) {
    return byXpath("//*[@data-testid='flexible-dates-container']//input[@value=$option]/..")
  }
  private UiElement occupancyConfigButton = byTestId('occupancy-config')
  private UiElement occupancyConfigPopup = byTestId('occupancy-popup')
  private UiElement occupancyConfigPopupDoneButton = byXpath("$occupancyConfigPopup/button")
  private UiElement occupancyConfigAdultsInput = byId('group_adults')
  private UiElement occupancyConfigAdultsDecrementIcon = byXpath("$occupancyConfigAdultsInput/..//button[1]")
  private UiElement occupancyConfigAdultsIncrementIcon = byXpath("$occupancyConfigAdultsInput/..//button[2]")
  private UiElement occupancyConfigChildrenInput = byId('group_children')
  private UiElement occupancyConfigChildrenDecrementIcon = byXpath("$occupancyConfigChildrenInput/..//button[1]")
  private UiElement occupancyConfigChildrenIncrementIcon = byXpath("$occupancyConfigChildrenInput/..//button[2]")

  private UiElement getOccupancyConfigChildAgeDropdown(int number) {
    return byXpath("(//select[@name='age'])[$number]")
  }
  private UiElement occupancyConfigRoomsInput = byId('no_rooms')
  private UiElement occupancyConfigRoomsDecrementIcon = byXpath("$occupancyConfigRoomsInput/..//button[1]")
  private UiElement occupancyConfigRoomsIncrementIcon = byXpath("$occupancyConfigRoomsInput/..//button[2]")
  private UiElement occupancyPetsCheckboxHidden = byXpath("//input[@type='checkbox'][@name='pets']")
  private UiElement occupancyPetsCheckboxSwitcher = byXpath("//label[@for='pets']//span[1]")

  boolean isLoaded() {
    destinationContainer.isVisible(pofPage)
  }

  void doSearch(Map query) {
    log.info("Do search $query")
    if (query.where) {
      fillWhere(query.where as String)
    }
    if (query.when) {
      fillWhen(query.when as Map)
    }
    if (query.occupancy) {
      fillOccupancy(query.occupancy as Map)
    }
    searchSubmitButton.click(pofPage)
    waitForNetworkIdle()
  }

  private fillWhere(String where) {
    destinationContainer.click(pofPage)
    destinationInput.fill(pofPage, where)
    sleep(500)
    getDestinationAutocompleteResult(1).click(pofPage)
  }

  private fillWhen(Map when) {
    if (!datesContainerButton.isVisible(pofPage)) {
      datesContainerButton.click(pofPage)
    }
    if (when.flexibility) {
      getFlexibleDaysOption(when.flexibility).click(pofPage)
    }
    def fromMonth = when.from.month == 'next' ? 2 : 1
    getDatepickerDay(when.from.day as int, fromMonth).click(pofPage)

    def toMonth = when.to.month == 'next' ? 2 : 1
    getDatepickerDay(when.to.day as int, toMonth).click(pofPage)
  }

  private fillOccupancy(Map occupancy) {
    if (!occupancyConfigPopup.isVisible(pofPage)) {
      occupancyConfigButton.click(pofPage)
    }
    adjustValueForOccupancy(
        occupancy.adults as Integer, occupancyConfigAdultsInput, occupancyConfigAdultsDecrementIcon, occupancyConfigAdultsIncrementIcon
    )
    if (occupancy.children) {
      List children = occupancy.children as List
      int childrenCount = children.size()
      log.debug("Children count - $childrenCount")
      log.debug("Children - $children")

      adjustValueForOccupancy(
          childrenCount, occupancyConfigChildrenInput, occupancyConfigChildrenDecrementIcon, occupancyConfigChildrenIncrementIcon
      )
      children.eachWithIndex { def entry, int i ->
        getOccupancyConfigChildAgeDropdown(i + 1).setOption(pofPage, [entry.toString()])
      }
    }
    adjustValueForOccupancy(
        occupancy.rooms as Integer, occupancyConfigRoomsInput, occupancyConfigRoomsDecrementIcon, occupancyConfigRoomsIncrementIcon
    )

    if (occupancy.pets == true) {
      if (!occupancyPetsCheckboxHidden.isChecked(pofPage)) {
        occupancyPetsCheckboxSwitcher.click(pofPage,)
      }
    } else if (occupancy.pets == false) {
      if (occupancyPetsCheckboxHidden.isChecked(pofPage)) {
        occupancyPetsCheckboxSwitcher.click(pofPage,)
      }
    }
    occupancyConfigPopupDoneButton.click(pofPage)
  }

  private adjustValueForOccupancy(Integer targetValue, UiElement input, UiElement decrement, UiElement increment) {
    if (targetValue) {
      int currentValue = Integer.parseInt(input?.getAttribute(pofPage, 'value'))
      int valueDelta = targetValue - currentValue
      def changeValueIcon = valueDelta > 0 ? increment : decrement
      log.debug("Set [$input] value $currentValue -> $targetValue")
      Math.abs(valueDelta).times {
        changeValueIcon.click(pofPage)
      }
    }
  }

}
