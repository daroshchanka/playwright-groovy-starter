package github.daroshchanka.playwrightstarter.core.api

import groovy.util.logging.Log4j2

@Log4j2
class BaseApiService {

  protected HttpClient httpClient

  BaseApiService(HttpClient httpClient) {
    this.httpClient = httpClient
  }

}
