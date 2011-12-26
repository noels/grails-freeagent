package freeagent.domain

import groovy.util.slurpersupport.NodeChild
import groovy.xml.MarkupBuilder


class Invoice {
    Integer id
    Integer contactId
    Integer projectId
    Date datedOn
    String reference
    String currency
    BigDecimal exchangeRate
    BigDecimal netValue
    BigDecimal salesTaxValue
    BigDecimal secondSalesTaxValue
    String status
    String comments
    BigDecimal discountPercent
    String poReference
    Boolean omitHeader
    Integer paymentTermsInDays
    Date writtenOffDate
    ArrayList<InvoiceItem> invoiceItems


    static final String FREEAGENT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    static final String[] ITEM_TYPES = ['Hours','Days','Months','Years','Products','Services','Expenses','Discount','Credit','Comment']
    public static final String FREEAGENT_STATUS_DRAFT = 'Draft'
    public Invoice(){
        invoiceItems = new ArrayList<InvoiceItem>()
    }

    /**
     * Constructor to create an invoice object and set the properties from a map of string values
     * passed in.
     *
     * @param params map of string representations of the values for an invoice's properties
     * @return instance of Invoice with properties set
     */
    public Invoice(params){
        id = params.id?.toInteger()?:0
        contactId = params.contactId?.toInteger()?:0
        projectId = params.projectId?.toInteger()?:0
        datedOn = params.datedOn
        reference = params.reference
        currency = params.currency
        exchangeRate = params.exchangeRate?new BigDecimal(params.exchangeRate):0
        netValue = params.netValue?new BigDecimal(params.netValue):0
        salesTaxValue = params.salesTaxValue?new BigDecimal(params.salesTaxValue):0
        secondSalesTaxValue = params.secondSalesTaxValue? new BigDecimal(params.secondSalesTaxValue):0
        status = params.status
        comments =params.comments
        discountPercent = params.discountPercent?new BigDecimal(params.discountPercent):0
        poReference = params.poReference?new BigDecimal(params.poReference):0
        omitHeader = params.omitHeader
        paymentTermsInDays = params.paymentTermsInDays?new Integer(params.paymentTermsInDays):0
        writtenOffDate = params.writtenOffDate
    }

    /**
     * Read data from an XML doc to set properties of the invoice object.
     *
     * @param node XML node from which to get the data
     */
    public void bind(node){
        id = node.id.text().toInteger()
        contactId = node."contact-id".text().toInteger()
        projectId = node."project-id".text()? node."project-id".text().toInteger(): 0
        datedOn = Date.parse(FREEAGENT_DATE_FORMAT, node."dated-on".text())
        reference = node.reference.text()
        currency = node.currency.text()
        exchangeRate = node."exchange-rate".text().toBigDecimal()
        netValue = node."net-value".text().toBigDecimal()
        salesTaxValue = node."sales-tax-value".text().toBigDecimal()
        secondSalesTaxValue = node."second-sales-tax-value".text().toBigDecimal()
        status = node.status.text()
        comments = node.comments.text()
        discountPercent = node."discount-percent".text()? node."discount-percent".text().toBigDecimal():0
        poReference = node."po-reference".text()
        omitHeader = node."omit-header".toBoolean()
        paymentTermsInDays = node."payment-terms-in-days".text().toInteger()
        writtenOffDate =  node."written-off-date".text()? Date.parse(FREEAGENT_DATE_FORMAT, node."written-off-date".text()): null

        node."invoice-items".children().each{ invoiceItem ->
            def invItem = new InvoiceItem()
            invItem.bind(invoiceItem)
            invoiceItems << invItem
        }
    }

    public String toXML(){
        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)
        xml."invoice"() {
            //mandatory fields
            if (id) "id"(id)
            "contact-id" (contactId)
            if (projectId) "project-id"(projectId)
            "dated-on"(datedOn.format(FREEAGENT_DATE_FORMAT))
            "reference"(reference)
            if(currency)"currency"(currency)
            if(exchangeRate)"exchange-rate"(exchangeRate)
            if(netValue)"net-value"(netValue)
            if(salesTaxValue)"sales-tax-value"(salesTaxValue)
            if(secondSalesTaxValue)"second-sales-tax-value"(secondSalesTaxValue)
            if(status)"status"(status)
            if(comments)"comments"(comments)
            if(discountPercent)"discount-percent"(discountPercent)
            if(poReference)"po-reference"(poReference)
            "omit-header"(omitHeader.toString())
            "payment-terms-in-days"(paymentTermsInDays)
            if(writtenOffDate)"written-off-date"(writtenOffDate)
        }
        writer.toString()
    }

}