package freeagent

import grails.test.*
import freeagent.test.MockWeb
import org.junit.*

class ContactServiceTests extends GrailsUnitTestCase {
    MockWeb mockWeb
    ContactService contactService
    void testInstantiation() {
        assertNotNull contactService

    }

    void testGetContacts() {
        //set port for mock web server to 8099
        mockWeb = new MockWeb(8099, '/contacts')
        try {
            mockWeb.response = 'test/data/contacts.xml'
            mockWeb.status = 200


            def contacts = contactService.get()
            //test invoice properties
            assert contacts.size() == 2
        } finally {
            mockWeb.stop()
        }

    }

}
