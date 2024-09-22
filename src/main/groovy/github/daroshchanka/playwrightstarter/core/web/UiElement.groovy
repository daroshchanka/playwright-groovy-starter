package github.daroshchanka.playwrightstarter.core.web

import com.microsoft.playwright.Locator
import com.microsoft.playwright.options.WaitForSelectorState
import groovy.util.logging.Log4j2

@Log4j2
class UiElement {

  protected String locator

  protected UiElement(String xpath) {
    locator = xpath
  }

  static UiElement byTestId(String testId) {
    new UiElement("//*[@data-testid='$testId']")
  }

  static UiElement byId(String id) {
    new UiElement("//*[@id='$id']")
  }

  static UiElement byXpath(String xpath) {
    new UiElement(xpath)
  }

  @Override
  String toString() {
    locator
  }

  Locator get(PageOrFrame pof) {
    pof.getElement(locator).first()
  }

  List<UiElement> getList(PageOrFrame pof) {
    int count = pof.getElement(locator).count()
    List result = []
    count.times {
      result << new UiElement("($locator)[${it + 1}]")
    }
    log.debug("[$locator] getList, found - $count")
    result
  }

  boolean isVisible(PageOrFrame pof) {
    boolean result = get(pof).isVisible()
    log.debug("[$locator] isVisible - $result")
    result
  }

  String getText(PageOrFrame pof) {
    def result = get(pof).textContent()
    log.debug("[$locator] getText - $result")
    result
  }

  String getAttribute(PageOrFrame pof, String name) {
    def result = get(pof).getAttribute(name)
    log.debug("[$locator] getAttribute($name) - $result")
    result
  }

  boolean isChecked(PageOrFrame pof) {
    boolean result = get(pof).isChecked()
    log.debug("[$locator] isChecked - $result")
    result
  }

  void waitForVisible(PageOrFrame pof, int timeoutSec = 10) {
    log.debug("[$locator] waitForVisible($timeoutSec)")
    get(pof).waitFor(
        new Locator.WaitForOptions(state: WaitForSelectorState.VISIBLE, timeout: timeoutSec * 1000)
    )
  }

  void waitForAttached(PageOrFrame pof, int timeoutSec = 10) {
    log.debug("[$locator] waitForAttached($timeoutSec)")
    get(pof).waitFor(
        new Locator.WaitForOptions(state: WaitForSelectorState.ATTACHED, timeout: timeoutSec * 1000)
    )
  }

  void waitForDetached(PageOrFrame pof, int timeoutSec = 10) {
    log.debug("[$locator] waitForDetached($timeoutSec)")
    get(pof).waitFor(
        new Locator.WaitForOptions(state: WaitForSelectorState.DETACHED, timeout: timeoutSec * 1000)
    )
  }

  void click(PageOrFrame pof) {
    log.debug("[$locator] click")
    get(pof).click()
  }

  void clear(PageOrFrame pof) {
    log.debug("[$locator] clear")
    get(pof).clear()
  }

  void fill(PageOrFrame pof, String text) {
    log.debug("[$locator] fill($text)")
    get(pof).with {
      clear()
      fill(text)
    }
  }

  void setOption(PageOrFrame pof, List<String> options) {
    log.debug("[$locator] setOption($options)")
    get(pof).selectOption(options as String[])
  }

}
