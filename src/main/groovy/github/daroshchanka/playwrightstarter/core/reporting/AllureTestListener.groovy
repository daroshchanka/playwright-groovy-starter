package github.daroshchanka.playwrightstarter.core.reporting

import com.microsoft.playwright.Page
import github.daroshchanka.playwrightstarter.core.PlaywrightConfig
import groovy.util.logging.Log4j2
import io.qameta.allure.Allure
import io.qameta.allure.listener.TestLifecycleListener
import io.qameta.allure.model.Status
import io.qameta.allure.model.TestResult
import org.apache.logging.log4j.ThreadContext

@Log4j2
class AllureTestListener implements TestLifecycleListener {
  protected static final String LOG_PATH = 'target/test-logs/'
  private static final ThreadLocal<String> TEST_UUID = new ThreadLocal<>()
  private static final TEST_HEADER_LOG = '-'.repeat(120)
  private static ThreadLocal<Page> PAGE = new ThreadLocal<>()

  @Override
  void afterTestStart(TestResult result) {
    log.info(TEST_HEADER_LOG)
    log.info("TEST STARTED: $result.fullName")
    log.info(TEST_HEADER_LOG)
    TEST_UUID.set(UUID.randomUUID().toString())
    ThreadContext.put('uuid', TEST_UUID.get())
  }

  @Override
  void beforeTestStop(TestResult result) {
    ThreadContext.remove('uuid')
    attachLogs()

    def attachScreenshotsOption = PlaywrightConfig.getReportingConfigs()?['attach-screenshots'] ?: 'only-on-failure'
    if (attachScreenshotsOption != 'off') {
      if (attachScreenshotsOption != 'on') {
        if (result.status != Status.PASSED) {
          attachScreenshot()
        }
      } else {
        attachScreenshot()
      }
    }
    log.info(TEST_HEADER_LOG)
    log.info("TEST COMPLETED: $result.name - ${result.status.value().toUpperCase()}")
    log.info(TEST_HEADER_LOG)
  }

  private static void attachLogs() {
    File logFile = new File("$LOG_PATH/test-${TEST_UUID.get()}.log")
    try {
      logFile.withInputStream {
        Allure.addAttachment('logs', it)
      }
    } catch (FileNotFoundException ignored) {
      log.warn("There is no log file ${logFile.getName()}")
    }
  }

  private static void attachScreenshot() {
    Page page = PAGE.get()
    if (page) {
      try {
        ByteArrayInputStream stream = new ByteArrayInputStream(page.screenshot())
        Allure.addAttachment('screenshot.png', stream)
      } catch (Exception e) {
        log.warn('attachScreenshot error', e)
      }
    }
  }

  static setPage(Page page) {
    PAGE.set(page)
  }
}
