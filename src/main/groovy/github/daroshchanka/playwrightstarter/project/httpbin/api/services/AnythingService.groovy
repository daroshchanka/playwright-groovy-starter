package github.daroshchanka.playwrightstarter.project.httpbin.api.services

import github.daroshchanka.playwrightstarter.core.api.BaseApiService
import github.daroshchanka.playwrightstarter.core.api.Response
import github.daroshchanka.playwrightstarter.project.httpbin.utils.data.AnythingDto
import groovy.transform.InheritConstructors
import groovy.util.logging.Log4j2

@Log4j2
@InheritConstructors
class AnythingService extends BaseApiService {

  private resources = [
      get   : '/anything',
      post  : '/anything',
      put   : '/anything',
      delete: '/anything',
  ]

  Response getAnything(Map query) {
    log.info('Get anything')
    httpClient.get(this.resources.get, query)
  }

  Response deleteAnything(List<Integer> ids) {
    log.info('Delete anything')
    httpClient.delete(this.resources.delete, null, null, [ids: ids])
  }

  Response putAnything(int id, AnythingDto data) {
    log.info('Put anything')
    this.httpClient.put(this.resources.put, data, [id: id])
  }

  Response postAnything(AnythingDto data) {
    log.info('Post anything')
    httpClient.post(this.resources.post, data)
  }
}
