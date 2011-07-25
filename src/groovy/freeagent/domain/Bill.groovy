package freeagent.domain

import groovy.util.slurpersupport.NodeChild
import groovy.xml.MarkupBuilder

class Bill {
    Integer id
    Integer contactId
    Integer rebillToProjectId
    Integer rebilledOnInvoiceItemId
    Date datedOn
    Date dueDate
    Integer nominalCode
    BigDecimal cachedPaidValue
    String comments
    String depreciationSchedule
    BigDecimal manualSalesTaxAmount
    Boolean recurring
    Date recurringEndDate
    String reference
    BigDecimal salesTaxRate
    BigDecimal secondSalesTaxRate
    BigDecimal totalValue

    static final String FREEAGENT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    /**
     * Default constructor
     */
    public Bill(){

    }

    /**
     * Constructor to create a Bill object and set the properties from a map of string values passed in
     *
     * @param params
     * @return a new instance of Bill with properties set
     */
    public Bill(params){
        id = params.id?.toInteger()?:0
        contactId = params.contractId?.toInteger()?:0
        rebillToProjectId = params.rebillToProjectId?.toInteger()?:0
        rebilledOnInvoiceItemId = params.ed?.toInteger()?:0
        datedOn = params.datedOn
        dueDate = params.dueDate
        nominalCode = params.nominalCode
        cachedPaidValue = params.cachedPaidValue? new BigDecimal(params.cachedPaidValue):0
        comments = params.comments
        depreciationSchedule = params.depreciationSchedule
        manualSalesTaxAmount = params.manualSalesTaxAmount? new BigDecimal(params.manualSalesTaxAmount):0
        recurring = params.recurring
        recurringEndDate = params.recurringEndDate
        reference = params.reference
        salesTaxRate = params.salesTaxRate? new BigDecimal(params.salesTaxRate):0
        secondSalesTaxRate = params.secondSalesTaxRate? new BigDecimal(params.secondSalesTaxRate):0
        totalValue = params.totalValue? new BigDecimal(params.totalValue):0

    }

    /**
     * Read data from an XML doc to set properties of the bill object
     *
     * @param node
     */
    public void bind(node){
        id = new Integer(node.id.text())
        contactId = node."contact-id".text().toInteger()
        rebillToProjectId = node."rebill-to-project-id".text()? node."rebill-to-project-id".text().toInteger:0
        rebilledOnInvoiceItemId  = node."rebill-on-invoice-item-id".text()?node."rebill-on-invoice-item-id".text():0
        datedOn = Date.parse(FREEAGENT_DATE_FORMAT, node."dated-on".text())
        dueDate = node."due-date".text()? Date.parse(FREEAGENT_DATE_FORMAT, node."due-date".text()): null
        nominalCode = node."nominal-code".text().toInteger()
        if (node."cached-paid-value") cachedPaidValue = new BigDecimal(node."cached-paid-value".text())
        comments  = node.comments.text()
        depreciationSchedule = node."depreciation-schedule".text()
        manualSalesTaxAmount = new BigDecimal(node."manual-sales-tax-amount".text())
        recurring = node.recurring.toBoolean
        recurringEndDate = node."recurring-end-date".text()?Date.parse(FREEAGENT_DATE_FORMAT, node."recurring-end-date".text()):null
        reference = node.reference.text()
        salesTaxRate = new BigDecimal(node."sales-tax-rate".text())
        secondSalesTaxRate = new BigDecimal(node."second-sales-tax-rate".text())
        totalValue = new BigDecimal(node."total-value".text())

    }

    public String toXML(){
        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)
        xml."bill"() {
            //mandatory fields are: <nominal-code>, <contact-id>, <reference>, <dated-on>,
            //<due-date>, and <total-value>.
            "id"("type":"integer", id)
            "nominal-code"(nominalCode)
            "contact-id" ("type":"integer",contactId)
            "reference"(reference)
            "dated-on"("type":"datetime", datedOn.format(FREEAGENT_DATE_FORMAT))
            "due-date"("type":"datetime", dueDate.format(FREEAGENT_DATE_FORMAT))
            if(!rebillToProjectId)
                "rebill-to-project-id" ("type":"integer", "nil":"true")
            else
                "rebill-to-project-id" ("type":"integer",rebillToProjectId)
            if (!rebilledOnInvoiceItemId)
                "rebilled-on-invoice-item-id"("type":"integer","nil":"true")
            else
                "rebilled-on-invoice-item-id"("type":"integer",rebilledOnInvoiceItemId)
            "cached-paid-value"("type":"decimal",cachedPaidValue)
            "comments"(comments)
            "depreciation-schedule"(depreciationSchedule)
            "manual-sales-tax-amount"("type":"decimal",manualSalesTaxAmount)
            if (!recurring)
                "recurring"("nil":"true")
            else
                "recurring"(recurring.toString())
            if (!recurringEndDate)
                "recurring-end-date"("nil":"true","type":"date")
            else
                "recurring-end-date"("type":"date", recurringEndDate)
            "sales-tax-rate"("type":"decimal",salesTaxRate)
            "second-sales-tax-rate"("type":"decimal",secondSalesTaxRate)
            "total-value"("type":"decimal",totalValue)
        }
        writer.toString()
    }


}