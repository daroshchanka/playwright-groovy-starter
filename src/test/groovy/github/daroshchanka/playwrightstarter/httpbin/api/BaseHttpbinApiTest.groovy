package github.daroshchanka.playwrightstarter.httpbin.api

import com.microsoft.playwright.APIRequest
import github.daroshchanka.playwrightstarter.core.api.HttpClient
import github.daroshchanka.playwrightstarter.httpbin.HttpbinTestConfig
import org.testng.annotations.AfterClass
import org.testng.annotations.BeforeClass

class BaseHttpbinApiTest {

  HttpClient httpClient

  @BeforeClass
  void initHttpClient() {
    httpClient = new HttpClient(withNewApiContextOptions())
  }

  @AfterClass
  void closeHttpClient() {
    httpClient.close()
  }

  APIRequest.NewContextOptions withNewApiContextOptions() {
    new APIRequest.NewContextOptions(
        baseURL: HttpbinTestConfig.instance.apiBaseUrl
    )
  }

}
