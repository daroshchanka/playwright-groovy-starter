package github.daroshchanka.playwrightstarter.project.httpbin.utils.data

import groovy.transform.Canonical

@Canonical
class AnythingDto implements Serializable {

  String keyString
  Boolean keyBoolean
  Integer keyNumber
  List<String> keyArrayString
  List<Map<String, ?>> keyArrayObj
}
