package freeagent

import grails.test.*
import freeagent.test.MockWeb
import org.junit.*

class InvoiceServiceTests extends GrailsUnitTestCase {
    MockWeb mockWeb
    InvoiceService invoiceService
    @Before
    public void init() {
    }

    @After
    public void cleanup() {
    }

    void testInstantiation() {
        assertNotNull invoiceService

    }

    void testGetInvoices() {
        //set port for mock web server to 8099
        mockWeb = new MockWeb(8099, '/invoices')
        try {
            mockWeb.response = 'test/data/invoices.xml'
            mockWeb.status = 200


            def invoices = invoiceService.get()
            //test invoice properties
            assert invoices.size() == 2
            assert invoices[0].id == 85
            assert invoices[0].contactId == 10
            assert invoices[0].projectId == 16
            assert invoices[0].datedOn == Date.parse("yyyy-MM-dd'T'HH:mm:ss'Z'", "2008-02-17T00:00:00Z")
            assert invoices[0].reference == "Abstract Web Design 001"
            assert invoices[0].currency == "GBP"
            assert invoices[0].exchangeRate == 1.0
            assert invoices[0].netValue == 1000.0
            assert invoices[0].salesTaxValue == 10.0
            assert invoices[0].secondSalesTaxValue == 7.5
            assert invoices[0].status == "Sent"
            assert invoices[0].comments == "This is a comment"
            assert invoices[0].discountPercent == 55
            assert invoices[0].poReference == 'Purchace Order Reference 123'
            assert invoices[0].omitHeader == false
            assert invoices[0].paymentTermsInDays == 30
            assert invoices[0].writtenOffDate == null
            //Test invoice items
            assert invoices[0].invoiceItems.size() == 2
            assert invoices[0].invoiceItems[0].id == 607
            assert invoices[0].invoiceItems[0].invoiceId == 85
            assert invoices[0].invoiceItems[0].projectId == 16
            assert invoices[0].invoiceItems[0].itemType == 'Services'
            assert invoices[0].invoiceItems[0].description == 'Setting up hosting'
            assert invoices[0].invoiceItems[0].price == 160.0
            assert invoices[0].invoiceItems[0].quantity == 1.0
            assert invoices[0].invoiceItems[0].salesTaxRate == 10.0
            assert invoices[0].invoiceItems[0].secondSalesTaxRate == 7.5
            assert invoices[0].invoiceItems[0].nominalCode == '001'


            assert invoices[1].id == 86
        } finally {
            mockWeb.stop()
        }

    }

    void testInvoiceTypes() {
        mockWeb = new MockWeb(8099, '/invoices/types')
        try{
            mockWeb.response = 'test/data/invoiceTypes.xml'
            mockWeb.status = 200

            def types = invoiceService.getInvoiceTypes()
            assert types.size() == 2
            assert types.'001' == "Sales"
            assert types['002'] == "Other Sales"

        } finally {
            mockWeb.stop()
        }

    }
}
