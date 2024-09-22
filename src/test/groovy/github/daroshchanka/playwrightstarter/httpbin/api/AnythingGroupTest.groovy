package github.daroshchanka.playwrightstarter.httpbin.api

import github.daroshchanka.playwrightstarter.project.httpbin.api.services.AnythingService
import github.daroshchanka.playwrightstarter.project.httpbin.utils.data.AnythingDto
import github.daroshchanka.playwrightstarter.project.httpbin.utils.data.generators.AnythingGenerator
import groovy.json.JsonSlurper
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test

class AnythingGroupTest extends BaseHttpbinApiTest {

  AnythingService apiService

  @BeforeClass
  void initApiService() {
    apiService = new AnythingService(httpClient)
  }

  @Test
  void 'anything GET'() {
    def query = [ids: '1,2,3', enabled: true]
    def response = apiService.getAnything(query)

    assert response.status == 200
    def expected = [
        ids: query.ids,
        enabled: query.enabled.toString(),
    ]
    def json = response.json as Map
    assert json.args == expected
  }

  @Test
  void 'anything POST'() {
    AnythingDto input = AnythingGenerator.generate()
    def response = apiService.postAnything(input)

    assert response.status == 200
    def json = response.json as Map
    def actual = new JsonSlurper().parseText(json.data as String) as AnythingDto
    assert actual == input
  }

  @Test
  void 'anything PUT'() {
    AnythingDto input = AnythingGenerator.generate()
    def id = 100000
    def response = apiService.putAnything(id, input)

    assert response.status == 200
    def json = response.json as Map
    def actual = new JsonSlurper().parseText(json.data as String) as AnythingDto
    assert actual == input
    assert json.headers.Id == id.toString()
  }

  @Test
  void 'anything DELETE'() {
    def ids = [10000, 999912, 234234]
    def response = apiService.deleteAnything(ids)

    assert response.status == 200
    def jsonDataOutput = new JsonSlurper().parseText(response.json.data as String)
    assert jsonDataOutput.ids == ids
  }

}
