package freeagent

import grails.test.*

class ConfigurationServiceTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testUserId() {
        def config = new ConfigurationService()
        assertEquals 'testUser', config.freeagentUserId
    }

    void testUserPassword() {
        def config = new ConfigurationService()
        assertEquals 'testPassword', config.freeagentPassword
    }

    void testURI() {
        def config = new ConfigurationService()
        assertEquals 'http://localhost:8099', config.freeagentURI
    }

}
