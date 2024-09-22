package github.daroshchanka.playwrightstarter.core.api

import com.microsoft.playwright.APIRequest
import com.microsoft.playwright.APIRequestContext
import com.microsoft.playwright.Playwright
import com.microsoft.playwright.options.RequestOptions
import groovy.json.JsonOutput
import groovy.util.logging.Log4j2

@Log4j2
class HttpClient implements Closeable {

  private Playwright playwright
  private APIRequestContext context

  HttpClient(APIRequest.NewContextOptions options = null) {
    log.debug('Playwright.create() --->')
    playwright = Playwright.create()
    log.debug('<--- Playwright.create()')
    context = playwright.request().newContext(options)
  }

  Response get(String url, Map query = [:], Map headers = [:]) {
    doRequest('get', url, query, headers)
  }

  Response delete(String url, Map query = [:], Map headers = [:], data = [:]) {
    doRequest('delete', url, query, headers, data)
  }

  Response head(String url, Map query = [:], Map headers = [:]) {
    doRequest('head', url, query, headers)
  }

  Response post(String url, data = [:], Map headers = [:]) {
    doRequest('post', url, null, headers, data)
  }

  Response put(String url, data = [:], Map headers = [:]) {
    doRequest('put', url, null, headers, data)
  }

  Response patch(String url, data = [:], Map headers = [:]) {
    doRequest('patch', url, null, headers, data)
  }

  Response doRequest(String method, String url, Map query = [:], Map headers = [:], data = [:]) {
    RequestOptions options = RequestOptions.create()
    query.each {
      options.setQueryParam(it.key as String, it.value as String)
    }
    headers.each {
      options.setHeader(it.key as String, it.value as String)
    }
    if (data) {
      options.setData(data)
    }
    doRequest(method, url, options)
  }

  Response doRequest(String method, String url, RequestOptions options) {
    options.setMethod(method)
    log.debug("${method.toUpperCase()} $url --> \n${optionsToString(options)}")
    def playwrightResponse = context.fetch(url, options)
    def result = new Response(playwrightResponse)
    log.debug("<-- ${method.toUpperCase()} $url - ${result.status}")
    result
  }

  private static String optionsToString(RequestOptions options) {
    try {
      JsonOutput.toJson(options.getProperties().findAll { it.key != 'class' })
    } catch (Exception ignored) {
      options.getProperties().toString()
    }
  }

  @Override
  void close() throws IOException {
    context.dispose()
    playwright.close()
  }

}
