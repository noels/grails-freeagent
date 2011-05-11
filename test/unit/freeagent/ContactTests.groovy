package freeagent

import grails.test.*
import freeagent.domain.Contact
import org.apache.commons.io.FileUtils
import org.custommonkey.xmlunit.*

class ContactTests extends GrailsUnitTestCase {

    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    public testBind(){
        def contact = new Contact()
        def xml = new XmlParser().parse(new File('test/data/contact.xml'))
        contact.bind(xml)
        assert contact.id == 436396
        assert contact.organisationName == 'Skynet PLC'
        assert contact.firstName == 'Someone'
        assert contact.lastName == 'Whoisnotme'
        assert contact.email == 'someone@example.com'
        assert contact.billingEmail == 'bills@example.com'
        assert contact.phoneNumber == '01234567890'
        assert contact.address1 == '2nd floor'
        assert contact.address2 == 'SomeBuilding'
        assert contact.address3 == '1 West Street'
        assert contact.town == 'London'
        assert contact.region == 'Berkshire'
        assert contact.postcode == 'W1 1EC'
        assert contact.contactNameOnInvoices
        assert contact.country == 'United Kingdom'
        assert contact.salesTaxRegistrationNumber == '1234132'
        assert contact.locale == 'en'
        assert contact.mobile == '0798765432'
        assert contact.accountBalance == 1200.00
        assert ! contact.usesContactInvoiceSequence
    }

    public testToXml(){
        def contact = new Contact()
        def filename = 'test/data/contact.xml'
        def xml = new XmlParser().parse(new File(filename))
        contact.bind(xml)

        def inXml = FileUtils.readFileToString(new File(filename), "UTF-8")
        def outXml = contact.toXML()
        log.println inXml
        log.println outXml
        XMLUnit.setIgnoreWhitespace(true)
        XMLUnit.setIgnoreAttributeOrder(true)
        def xmlDiff = new Diff(inXml, outXml)
        assert xmlDiff.similar()
    }
}
