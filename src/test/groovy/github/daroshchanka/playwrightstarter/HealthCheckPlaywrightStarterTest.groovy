package github.daroshchanka.playwrightstarter

import com.microsoft.playwright.Page
import com.microsoft.playwright.Playwright
import groovy.json.JsonSlurper
import groovy.util.logging.Log4j2
import org.testng.annotations.AfterClass
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test

@Log4j2
class HealthCheckPlaywrightStarterTest {

  Playwright playwright

  @BeforeClass
  void initPlaywright() {
    playwright = Playwright.create()
  }

  @AfterClass
  void tearDownPlaywright() {
    playwright.close()
  }

  @Test
  void 'playwright API context OK'() {
    log.debug('debug log API')
    def response = playwright.request().newContext().get('https://httpbin.org/get')
    assert response.status() == 200
    assert new JsonSlurper().parseText(response.text()).headers['User-Agent'].contains('Playwright')
  }

  @Test
  void 'playwright WEB context OK'() {
    log.info('info log WEB')
    Page page = playwright.chromium().launch().newPage()
    page.navigate('https://todomvc.com/examples/react/dist/')
    assert page.url().contains('https://todomvc.com/examples/react/dist/')
  }

}
