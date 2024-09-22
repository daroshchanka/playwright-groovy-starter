package github.daroshchanka.playwrightstarter.booking.web

import com.microsoft.playwright.Browser
import github.daroshchanka.playwrightstarter.core.web.BaseWebPage
import io.qameta.allure.Feature
import org.testng.annotations.Test

@Feature('Booking.com language detection by browser locale')
class BookingDetectLocaleTest extends BaseBookingWebTest {

  @Override
  protected Browser.NewContextOptions withNewContextOptions() {
    super.withNewContextOptions().tap {
      locale = 'fr-FR'
    }
  }

  @Test(priority = 201)
  void 'should redirect to the target location index page'() {
    BaseWebPage baseWebPage = new BaseWebPage(page)
    baseWebPage.goTo('/').waitForNetworkIdle()
    assert baseWebPage.page.url().contains('/index.fr.html')
  }
}
