package github.daroshchanka.playwrightstarter.booking

import groovy.util.logging.Log4j2
import org.yaml.snakeyaml.Yaml

@Log4j2
@Singleton(strict = false)
class BookingTestConfig {

  private Map envConfigs

  private BookingTestConfig() {
    def env = System.getProperty('ENV') == null ? 'dev' : System.getProperty('ENV')
    def globalConfigs = new Yaml()
        .load(new File('target/test-classes/booking-config.yaml').text)
    envConfigs = (globalConfigs[env] ?: globalConfigs['dev']) as Map
    log.info("[BookingTestConfig] Load booking-config.yaml: for [$env] - $envConfigs")
  }

  static String getWebBaseUrl() {
    getInstance().envConfigs.web.baseUrl
  }

  static String getApiBaseUrl() {
    getInstance().envConfigs.api.baseUrl
  }
}
