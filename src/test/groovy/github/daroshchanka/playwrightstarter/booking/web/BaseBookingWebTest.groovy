package github.daroshchanka.playwrightstarter.booking.web

import com.microsoft.playwright.Browser
import github.daroshchanka.playwrightstarter.booking.BookingTestConfig
import github.daroshchanka.playwrightstarter.core.web.BaseWebTest

class BaseBookingWebTest extends BaseWebTest {

  @Override
  protected Browser.NewContextOptions withNewContextOptions() {
    super.withNewContextOptions().tap {
      baseURL = BookingTestConfig.instance.webBaseUrl
      locale = 'en-Gb'
    }
  }

}
