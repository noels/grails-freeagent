<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>List of invoices</title>
    </head>
    <body>
        <p>${flash.message}</p>
        <table>
            <thead>
            <tr>
                <td>id</td>
                <td>contactId</td>
                <td>projectId</td>
                <td>datedOn</td>
                <td>reference</td>
                <td>currency</td>
                <td>exchangeRate</td>
                <td>netValue</td>
                <td>salesTaxValue</td>
                <td>secondSalesTaxValue</td>
                <td>status</td>
                <td>comments</td>
                <td>discountPercent</td>
                <td>poReference</td>
                <td>omitHeader</td>
                <td>paymentTermsInDays</td>
                <td>writtenOffDate</td>
                <td>invoiceItems</td>
            </tr>
            </thead>
        <g:each in="${invoices}" var="invoice">
            <tr>
                <td>${invoice.id}</td>
                <td>${invoice.contactId}</td>
                <td>${invoice.projectId}</td>
                <td>${invoice.datedOn}</td>
                <td>${invoice.reference}</td>
                <td>${invoice.currency}</td>
                <td>${invoice.exchangeRate}</td>
                <td>${invoice.netValue}</td>
                <td>${invoice.salesTaxValue}</td>
                <td>${invoice.secondSalesTaxValue}</td>
                <td>${invoice.status}</td>
                <td>${invoice.comments}</td>
                <td>${invoice.discountPercent}</td>
                <td>${invoice.poReference}</td>
                <td>${invoice.omitHeader}</td>
                <td>${invoice.paymentTermsInDays}</td>
                <td>${invoice.writtenOffDate}</td>
            </tr><tr>
                <td colspan="17">
                    <table style="float:right">
                        <thead>
                            <tr>
                                <td>id</td>
                                <td>invoiceId</td>
                                <td>projectId</td>
                                <td>itemType</td>
                                <td>description</td>
                                <td>price</td>
                                <td>quantity</td>
                                <td>salesTaxRate</td>
                                <td>secondSalesTaxRate</td>
                                <td>nominalCode</td>
                            </tr>

                        </thead>
                        <g:each in="${invoice.invoiceItems}" var="item">
                        <tr>
                            <td>${item.id}</td>
                            <td>${item.invoiceId}</td>
                            <td>${item.projectId}</td>
                            <td>${item.itemType}</td>
                            <td>${item.description}</td>
                            <td>${item.price}</td>
                            <td>${item.quantity}</td>
                            <td>${item.salesTaxRate}</td>
                            <td>${item.secondSalesTaxRate}</td>
                            <td>${item.nominalCode}</td>
                        </tr>
                        </g:each>
                    </table>
                    <g:form action="addItem">
                        <g:hiddenField name="invoiceId" value="${invoice.id}" />
                        <g:hiddenField name="projectId" value="$invoice.projectId" />
                        <g:select name="itemType" from="${itemTypes}" />
                        <g:textField name="description" value="Item description"/>
                        <g:textField name="price" value="200"/>
                        <g:textField name="quantity" value="1"/>
                        <g:textField name="salesTaxRate" value="20.0"/>
                        <g:textField name="secondSalesTaxRate" value="0.0"/>
                        <g:select name="nominalCode" from="${invoiceTypes}" optionKey="key" optionValue="value"/>
                        <g:submitButton name="addItem" value="Add item" />
                    </g:form>
                    <g:form action="markAsSent">
                        <g:hiddenField name="invoiceId" value="${invoice.id}" />
                        <g:submitButton name="markAsSent" value="Mark as sent" />
                    </g:form>

                </td>
            </tr>
        </g:each>
        </table>
        <p>Add an invoice using the form below</p>
        <g:form action="addInvoice">
            <g:select name="contactId" from="${contacts}" optionKey="key" optionValue="value" />
            <g:datePicker name="datedOn" value="${new Date()}" precision="day" years="${2011..2012}"/>
            <g:textField name="paymentTermsInDays" value="30"/>
            <g:textField name="reference" value="INA10012" />
            <g:submitButton name="addInvoice" value="Add invoice" />
        </g:form>

        <g:form action="addContact">
            <g:textField name="organisationName" value="Evil MegaCorp PLC"/>
            <g:textField name="firstName" value="Wesley" />
            <g:textField name="lastName" value="Tweedy" />
            <g:textField name="email" value="wes@example.com" />
            <g:submitButton name="addContact" value="Add contact" />
        </g:form>

    </body>
</html>
