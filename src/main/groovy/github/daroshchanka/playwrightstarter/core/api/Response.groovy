package github.daroshchanka.playwrightstarter.core.api

import com.microsoft.playwright.APIResponse
import groovy.json.JsonSlurper
import groovy.transform.Memoized
import groovy.util.logging.Log4j2

@Log4j2
class Response {

  private APIResponse response

  @Lazy
  private JsonSlurper jsonSlurper = new JsonSlurper()

  Response(APIResponse response) {
    this.response = response
  }

  @Memoized
  def getJson() {
    def json = jsonSlurper.parseText(response.text())
    log.debug("JSON: $json")
    json
  }

  @Memoized
  Serializable getStatus() {
    response.status()
  }

  @Memoized
  String getText() {
    response.text()
  }

  @Memoized
  byte[] getBody() {
    response.body()
  }

  Map<String, String> getHeaders() {
    response.headers()
  }

  APIResponse getWrapped() {
    response
  }


}
