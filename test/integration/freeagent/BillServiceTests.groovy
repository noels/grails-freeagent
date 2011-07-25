package freeagent

import grails.test.*
import freeagent.test.MockWeb
import org.junit.*

class BillServiceTests extends GrailsUnitTestCase {
    MockWeb mockWeb
    BillService billService
    void testInstantiation() {
        assertNotNull billService

    }

    void testGetBills() {
        //set port for mock web server to 8099
        mockWeb = new MockWeb(8099, '/bills')
        try {
            mockWeb.response = 'test/data/bills.xml'
            mockWeb.status = 200


            def bills = billService.get()
            //test invoice properties
            assert bills.size() == 1
        } finally {
            mockWeb.stop()
        }
    }

    void testGetBillsWithDates(){
        //set port for mock web server to 8099
        mockWeb = new MockWeb(8099, '/bills')
        try {
            mockWeb.response = 'test/data/bills.xml'
            mockWeb.status = 200


            def bills = billService.get(Date.parse('yyyy-mm-dd', '2011-03-01'), Date.parse('yyyy-mm-dd', '2011-04-30'))
            //test invoice properties
            assert bills.size() == 1
        } finally {
            mockWeb.stop()
        }
    }

}
