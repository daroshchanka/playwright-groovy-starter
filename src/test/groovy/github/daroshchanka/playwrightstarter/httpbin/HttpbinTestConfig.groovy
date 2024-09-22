package github.daroshchanka.playwrightstarter.httpbin

import groovy.util.logging.Log4j2
import org.yaml.snakeyaml.Yaml

@Log4j2
@Singleton(strict = false)
class HttpbinTestConfig {

  private Map envConfigs

  private HttpbinTestConfig() {
    def env = System.getProperty('ENV') == '' ? 'dev' : System.getProperty('ENV')
    def globalConfigs = new Yaml()
        .load(new File('target/test-classes/httpbin-config.yaml').text)
    envConfigs = (globalConfigs[env] ?: globalConfigs['dev']) as Map
    log.info("[HttpbinTestConfig] Load httpbin-config.yaml: for [$env] - $envConfigs")
  }

  static String getWebBaseUrl() {
    getInstance().envConfigs.web.baseUrl
  }

  static String getApiBaseUrl() {
    getInstance().envConfigs.api.baseUrl
  }
}
