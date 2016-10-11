import groovyx.net.http.HttpResponseException
import spock.lang.Specification

import static org.jfrog.artifactory.client.ArtifactoryClient.create

class PluginConfigTest extends Specification {
  def 'list installed plugins test'() {
    setup:
    def baseurl = 'http://localhost:8088/artifactory'
    def artifactory = create(baseurl, 'admin', 'password')

    when:
    artifactory.plugins().execute('listPlugins')

    then:
    notThrown(HttpResponseException)
  }

  def 'download plugin test'(){
    setup:
    def baseurl = 'http://localhost:8088/artifactory/api/plugins/execute'
    def params = '?params=name=pluginsConfig'
    def auth = "Basic ${'admin:password'.bytes.encodeBase64()}"
    def conn = null

    when:
    conn = new URL("${baseurl}/downloadPlugin${params}").openConnection()
    conn.requestMethod = 'GET'
    conn.setRequestProperty('Authorization', auth)
    assert conn.responseCode == 200
    conn.disconnect()

    then:
    notThrown(HttpResponseException)
  }
}