package freeagent

import grails.test.*
import freeagent.test.MockWeb
import org.junit.*

class HttpServiceTests extends GrailsUnitTestCase {
    MockWeb mockWeb
    HttpService httpService
    def configurationService
    @Before
    public void init() {
        //set port for mock web server to 8099
        mockWeb = new MockWeb(8099)
    }

    @After
    public void cleanup() {
        mockWeb.stop()
    }

    void testInstantiation() {
        assertNotNull httpService

    }

    void testGetResponse() {
        mockWeb.response = 'test/data/valid_response.xml'
        mockWeb.status = 200
        httpService.configurationService = configurationService

        def xml = httpService.getResponse('/')
        assertEquals 'Valid', xml.text().trim()
    }

    void testGet() {
        mockWeb.response = 'test/data/valid_response.xml'
        mockWeb.status = 200

        def xml = httpService.get('/')
        assertEquals 'Valid', xml.text().trim()
    }


    void testCachedResponse(){
        mockWeb.response = 'test/data/valid_response.xml'
        mockWeb.status = 200

        //call the cacheable method
        def xml = httpService.get('/', [:])

        mockWeb.response = 'test/data/valid_response2.xml'
        mockWeb.status = 200
        def xml2 = httpService.get('/', [:])

        assertEquals xml.text().trim(), xml2.text().trim()
        assertTrue(xml.text().trim() == xml2.text().trim())
        assertFalse (xml2.text().trim() == httpService.getResponse('/').text().trim())
    }

    void testPost(){
        mockWeb.response = 'test/data/valid_response.xml'
        mockWeb.status = 200

        def xml = httpService.post('/', '<request>body</request>')
    }



}
