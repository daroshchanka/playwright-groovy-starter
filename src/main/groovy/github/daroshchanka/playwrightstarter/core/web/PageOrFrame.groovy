package github.daroshchanka.playwrightstarter.core.web

import com.microsoft.playwright.Frame
import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page

class PageOrFrame {

  Page page
  Frame frame

  PageOrFrame(Page page) {
    this.page = page
  }

  PageOrFrame(Frame frame) {
    this.frame = frame
  }

  Locator getElement(String selector) {
    if (page) {
      page.locator(selector)
    } else if(frame) {
      frame.locator(selector)
    } else {
      throw new IllegalStateException("Page or Frame should be defined")
    }
  }
}
