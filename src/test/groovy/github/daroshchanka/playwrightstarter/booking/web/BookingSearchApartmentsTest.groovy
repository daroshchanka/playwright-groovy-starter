package github.daroshchanka.playwrightstarter.booking.web

import github.daroshchanka.playwrightstarter.project.booking.web.pages.LandingPage
import io.qameta.allure.Feature
import org.testng.annotations.Test

import java.time.LocalDate

@Feature('Booking.com search apartments')
class BookingSearchApartmentsTest extends BaseBookingWebTest {

  //with new context per method
  @Override
  boolean withNewContextPerClass() { false }

  @Test(priority = 101)
  void 'simple search query'() {
    def landingPage = new LandingPage(page)
    assert landingPage.navigate().isLoaded()

    def searchQuery = [
        where: 'London',
        when : [
            from: [day: 7, month: 'next'],
            to  : [day: 21, month: 'next'],
        ]
    ]
    def searchResultsPage = landingPage.doSearch(searchQuery)
    assert searchResultsPage.isLoaded()
    assert searchResultsPage.getSearchResultCardsCount() >= 25
    assert searchResultsPage.getAssertiveHeaderText() ==~ /$searchQuery.where: \d,\d+ properties found/
  }

  @Test(priority = 102)
  void 'complex search query'() {
    def landingPage = new LandingPage(page)
    assert landingPage.navigate().isLoaded()

    def searchQuery = [
        where    : 'Iceland',
        when     : [
            from       : [day: LocalDate.now().dayOfMonth, month: 'current'],
            to         : [day: 15, month: 'next'],
            flexibility: '7',
        ],
        occupancy: [
            adults  : 1,
            children: [0, 7, 17],
            rooms   : 2,
            pets    : true,
        ]
    ]
    def searchResultsPage = landingPage.doSearch(searchQuery)
    assert searchResultsPage.isLoaded()
    assert searchResultsPage.getSearchResultCardsCount() <= 25
    assert searchResultsPage.getAssertiveHeaderText() ==~ /$searchQuery.where: \d+ properties found/

    def searchQuery2 = [
        occupancy: [
            pets: false,
        ]
    ]
    searchResultsPage.doSearch(searchQuery2)
    assert searchResultsPage.getSearchResultCardsCount() >= 25
    assert searchResultsPage.getAssertiveHeaderText() ==~ /$searchQuery.where: \d+ properties found/
  }

}
