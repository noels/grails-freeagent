package freeagent

import grails.test.*
import freeagent.domain.Bill
import org.apache.commons.io.FileUtils
import org.custommonkey.xmlunit.*

class BillTests extends GrailsUnitTestCase {

    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    public testBind(){
        def bill = new Bill()
        def xml = new XmlParser().parse(new File('test/data/bill.xml'))

        bill.bind(xml)
        assert bill.nominalCode == 602
        assert bill.cachedPaidValue == 2050.0
        assert bill.comments == "A big pile of stuff"
        assert bill.contactId == 1
        assert bill.datedOn ==  Date.parse("yyyy-MM-dd'T'HH:mm:ss'Z'", "2006-10-01T00:00:00Z")
        assert bill.depreciationSchedule == "3 Years"
        assert bill.dueDate == Date.parse("yyyy-MM-dd'T'HH:mm:ss'Z'","2006-10-01T00:00:00Z")
        assert bill.id == 1
        assert bill.manualSalesTaxAmount == 0.0
        assert !bill.rebillToProjectId
        assert !bill.rebilledOnInvoiceItemId
        assert !bill.recurring
        assert !bill.recurringEndDate
        assert bill.salesTaxRate == 17.5
        assert bill.secondSalesTaxRate == 0.0
        assert bill.totalValue == 2050.0
    }

    public testToXml(){
        def bill = new Bill()
        def filename = 'test/data/bill.xml'
        def xml = new XmlParser().parse(new File(filename))
        bill.bind(xml)

        def inXml = FileUtils.readFileToString(new File(filename), "UTF-8")
        def outXml = bill.toXML()
        log.println inXml
        log.println outXml
        XMLUnit.setIgnoreWhitespace(true)
        XMLUnit.setIgnoreAttributeOrder(true)
        def xmlDiff = new Diff(inXml, outXml)
        assert xmlDiff.similar()
    }
}
