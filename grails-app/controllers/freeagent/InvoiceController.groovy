package freeagent

import groovyx.net.http.RESTClient
import groovyx.net.http.ContentType
import grails.plugin.springcache.annotations.CacheFlush
import grails.plugin.springcache.annotations.Cacheable
import freeagent.domain.Invoice
import freeagent.domain.InvoiceItem
import freeagent.domain.Contact

class InvoiceController {
    InvoiceService invoiceService
    ConfigurationService configurationService
    ContactService contactService

    def list = {
        def inv = invoiceService.get()
        def invoiceTypes = invoiceService.getInvoiceTypes()
        def contacts = contactService.get()
        [invoices:inv, itemTypes: Invoice.ITEM_TYPES, invoiceTypes: invoiceTypes, contacts:contacts.collect{[key:it.id,value:it.name]}]

    }

    @CacheFlush(["HttpGetCache"])
    def flushCache = {
        redirect(action: "list")
    }

    def addItem = {
        def invItem = new InvoiceItem(params)
        def invItemId = invoiceService.putInvoiceItem(invItem)
        flash.message = "Created invoice item id: ${invItemId}"
        redirect(action:"list")
    }

    def addInvoice = {
        def inv = new Invoice(params)
        log.println inv.toXML()
        def invoiceId = invoiceService.putInvoice(inv)
        flash.message = "Created invoice id: ${invoiceId}"
        redirect(action:"list")
    }

    def addContact = {
        def contact = new Contact(params)
        contactService.put(contact)
        redirect (action:'list')
    }

    def markAsSent = {
        def invoice = new Invoice(id:params.invoiceId)
        invoiceService.markInvoiceAsSent(invoice)
        redirect (action:'list')
    }
}