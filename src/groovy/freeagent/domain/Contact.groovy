package freeagent.domain

import groovy.util.slurpersupport.NodeChild
import groovy.xml.MarkupBuilder


class Contact {
    Integer id
    String organisationName
    String firstName
    String lastName
    String email
    String billingEmail
    String phoneNumber
    String address1
    String town
    String region
    String postcode
    String address2
    String address3
    Boolean contactNameOnInvoices
    String country
    String salesTaxRegistrationNumber
    String locale
    String mobile
    BigDecimal accountBalance
    Boolean usesContactInvoiceSequence

    public Contact(){

    }

    public Contact(params){
        id = params.id?.toInteger()?:0
        organisationName = params.organisationName
        firstName = params.firstName
        lastName = params.lastName
        email = params.email
        billingEmail = params.billingEmail
        phoneNumber = params.phoneNumber
        address1 = params.address1
        town = params.town
        region = params.region
        postcode = params.postcode
        address2 = params.address2
        address3 = params.address3
        contactNameOnInvoices = params.contactNameOnInvoices
        country = params.country
        salesTaxRegistrationNumber = params.salesTaxRegistrationNumber
        locale = params.locale
        mobile = params.mobile
        accountBalance = params.accountBalance
        usesContactInvoiceSequence = params.usesContactInvoiceSequence


    }

    public bind(node){
        id = node."id".text().toInteger()
        organisationName = node."organisation-name".text()
        firstName = node."first-name".text()
        lastName = node."last-name".text()
        email = node."email".text()
        billingEmail = node."billing-email".text()
        phoneNumber = node."phone-number".text()
        address1 = node."address1".text()
        town = node."town".text()
        region = node."region".text()
        postcode = node."postcode".text()
        address2 = node."address2".text()
        address3 = node."address3".text()
        contactNameOnInvoices = node."contact-name-on-invoices"
        country = node."country".text()
        salesTaxRegistrationNumber = node."sales-tax-registration-number".text()
        locale = node."locale".text()
        mobile = node."mobile".text()
        accountBalance = node."account-balance".text().toBigDecimal()
        usesContactInvoiceSequence = node."uses-contract-invoice-sequence"

    }

    public toXML(){
        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)
        /**
         * To create an contact you should POST to /contacts. You only need to specify the required
         * elements which for a contact are: <first-name> and <last-name>
         *     <?xml version="1.0" encoding="UTF-8"?>
         *     <contact>
         *         <first-name>Mark</first-name>
         *         <last-name>Rothko</last-name>
         *     </contact>
         *
         * To update a contact, you only need to put the changed fields into an update doc; something
         * to look at later on. Changes should be posted to:
         * /contacts/contact_id
         *
         */
        xml."contact"() {
            if (id) "id"("type":"integer", id)
            if (organisationName) "organisation-name"(organisationName)
            "first-name"(firstName)
            "last-name" (lastName)
            if (email) "email"(email)
            if (billingEmail) "billing-email"(billingEmail)
            if (phoneNumber) "phone-number"(phoneNumber)
            if (address1) "address1"(address1)
            if (town) "town"(town)
            if (region) "region"(region)
            if (postcode) "postcode"(postcode)
            if (address2) "address2"(address2)
            if (address3) "address3"(address3)
            "contact-name-on-invoices"("type":"boolean", contactNameOnInvoices.toString())
            if(country)"country"(country)
            if (salesTaxRegistrationNumber) "sales-tax-registration-number"(salesTaxRegistrationNumber)
            if (locale) "locale"(locale)
            if (mobile) "mobile"(mobile)
            if (accountBalance) "account-balance"(accountBalance)
            "uses-contact-invoice-sequence"("type":"boolean", usesContactInvoiceSequence.toString())
        }
        writer.toString()
    }

    public String getName(){
        if (organisationName) return organisationName
        return "${firstName} ${lastName}"
    }
}
