package freeagent

import freeagent.domain.Invoice
import freeagent.domain.InvoiceItem


class InvoiceService {
    HttpService httpService

    public getList(String view = 'all'){
        def invoices = []
        def resp = httpService.get('/invoices', [view:view])
        resp.children().each{ invoice ->
            def inv = new Invoice()
            inv.bind(invoice)
            invoices << inv
        }
        invoices
    }

    public get(Integer id) {
        def resp = httpService.get("/invoices/${id}")
        def inv = new Invoice()
        inv.bind(resp)
        inv
    }


    public getInvoiceTypes(){
        def invoiceTypes = [:]
        //must specify all params otherwise we won't cache it
        def res = httpService.get('/invoices/types', [:])
        res.income.children().each{type ->
            invoiceTypes[type."@nominal-code".toString()] = type.text()
        }
        invoiceTypes
    }

    /**
     * To create an invoice item you should POST to /invoices/invoice_id/invoice_items. You only need to specify
     * the required elements which for invoice items are <item-type>, <description>, <quantity>, <price> and
     * <nominal-code> e.g.:
     *     <?xml version="1.0" encoding="UTF-8"?>
     *     <invoice-item>
     *         <item-type>Hours</item-type>
     *         <price>50</price>
     *         <quantity>12</quantity>
     *         <sales-tax-rate>17.5</sales-tax-rate>
     *         <description>Creating wireframe templates</description>
     *         <nominal-code>001</nominal-code>
     *     </invoice-item>
     *
     */
    public putInvoiceItem(invoiceItem){
        if (!invoiceItem.invoiceId){
            throw new RuntimeException('You must specify an invoice number')
        }
        def invItemXml = invoiceItem.toXML()
        //POST to /invoices/invoice_id/invoice_items
        def location = httpService.post("/invoices/${invoiceItem.invoiceId}/invoice_items", invItemXml)
        //the last part of the url is the new invoice item id
        location.value.split('/')[-1]
    }

    /**
     * To create an invoice you should POST to /invoices. You only need to specify the required
     * elements which for an invoice are:  <contact-id>, <dated-on>, <payment-terms-in-days>
     * and <reference>
     *
     * <invoice>
     *     <contact-id>42</contact-id>
     *     <dated-on>2008-02-17T00:00:00Z</dated-on>
     *     <reference>INVOICE 2001</reference>
     *     <currency>GBP</currency>
     *     <payment-terms-in-days>30</payment-terms-in-days>
     *     <comments>A comment</comments>
     * </invoice>
     *
     */
    public putInvoice(invoice){
        def invXml = invoice.toXML()
        def location = httpService.post("/invoices", invXml)
        //the last part of the url is the new invoice item id
        location.value.split('/')[-1]
    }

    public markInvoiceAsSent(invoice){
        if (!invoice.id){
            throw new RuntimeException('You must specify an invoice number')
        }
        // we need to put to: /invoices/invoice_id/mark_as_sent
        httpService.put("/invoices/${invoice.id}/mark_as_sent", '')
    }
}