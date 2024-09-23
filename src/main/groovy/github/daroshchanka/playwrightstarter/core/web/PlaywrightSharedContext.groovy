package github.daroshchanka.playwrightstarter.core.web

import com.microsoft.playwright.Page

class PlaywrightSharedContext {

  private static ThreadLocal<Page> PAGE = new ThreadLocal<>()

  static setPage(Page page) {
    PAGE.set(page)
  }

  static Page getPage() {
    PAGE.get()
  }
}
