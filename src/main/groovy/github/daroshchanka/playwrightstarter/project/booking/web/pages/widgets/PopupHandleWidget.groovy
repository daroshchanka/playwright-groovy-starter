package github.daroshchanka.playwrightstarter.project.booking.web.pages.widgets

import github.daroshchanka.playwrightstarter.core.web.BaseWebPage
import github.daroshchanka.playwrightstarter.core.web.UiElement
import groovy.transform.InheritConstructors
import groovy.util.logging.Log4j2

import static github.daroshchanka.playwrightstarter.core.web.UiElement.byXpath

@Log4j2
@InheritConstructors
class PopupHandleWidget extends BaseWebPage {

  private UiElement popup = byXpath("(//div[@role='dialog'])[last()]")
  private UiElement popupCloseIcon = byXpath("$popup//button")

  void handlePopup() {
    try {
      popup.waitForVisible(pofPage, 2)
      log.debug('Closing popup')
      popupCloseIcon.click(pofPage)
      popup.waitForDetached(pofPage)
    } catch (ignored) {
    }
  }
}
