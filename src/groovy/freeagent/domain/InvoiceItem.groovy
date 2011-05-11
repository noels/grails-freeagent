package freeagent.domain

import groovy.util.slurpersupport.Node
import groovy.xml.MarkupBuilder

class InvoiceItem {
    Integer id
    Integer invoiceId
    Integer projectId
    String itemType
    String description
    BigDecimal price
    BigDecimal quantity
    BigDecimal salesTaxRate
    BigDecimal secondSalesTaxRate
    String nominalCode

    public InvoiceItem(){

    }

    public InvoiceItem(Map params){
        id = params.id? new Integer(params.id) :0
        invoiceId = new Integer(params.invoiceId)
        projectId = new Integer(params.projectId)
        itemType = params.itemType
        description = params.description
        price = new BigDecimal(params.price)
        quantity = new BigDecimal(params.quantity)
        salesTaxRate = new BigDecimal(params.salesTaxRate)
        secondSalesTaxRate = new BigDecimal(params.secondSalesTaxRate)
        nominalCode = params.nominalCode
    }

    public void bind(node){
        id = node."id".text().toInteger()
        invoiceId = node."invoice-id".text()? node."invoice-id".text().toInteger() : 0
        projectId = node."project-id".text()? node."project-id".text().toInteger() : 0
        itemType = node."item-type".text()
        description = node.description.text()
        price = node.price.text()? node.price.text().toBigDecimal() : 0
        quantity = node.quantity.text()? node.quantity.text().toBigDecimal() : 0
        salesTaxRate = node."sales-tax-rate".text()? node."sales-tax-rate".text().toBigDecimal() : 0
        secondSalesTaxRate = node."second-sales-tax-rate".text()? node."second-sales-tax-rate".text().toBigDecimal() : 0
        nominalCode = node."nominal-code".text()
    }

    public String toXML(){
        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)
        xml."invoice-item"() {
            //mandatory fields
            "item-type"(itemType)
            "price"(price)
            "quantity"(quantity)
            "description"(description)
            "nominal-code"(nominalCode)
            //optional fields
            if (id) "id"(id)
            if (projectId) "project-id"(projectId)
            if (invoiceId) "invoice-id" (invoiceId)
            if (salesTaxRate) "sales-tax-rate"(salesTaxRate)
            if (secondSalesTaxRate) "second-sales-tax-rate"(secondSalesTaxRate)
        }
        writer.toString()
    }

}