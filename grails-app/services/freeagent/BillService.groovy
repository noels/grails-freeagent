package freeagent

import freeagent.domain.Bill

class BillService{
    HttpService httpService

    static final String BILL_DATE_FORMAT = 'yyyy-mm-dd'
    /**
     * Return a list of contacts from freeagent
     *
     * @return List of contacts
     */
    public get(fromDate = Date.parse(BILL_DATE_FORMAT, '2010-01-01'), toDate = Date.parse(BILL_DATE_FORMAT, '2011-12-31')){
        def from = fromDate.format(BILL_DATE_FORMAT)
        def to = toDate.format(BILL_DATE_FORMAT)
        def bills = []
        def resp = httpService.get('/bills', ["view":"${from}_${to}"])
        resp.children().each{ billNode ->
            def bill = new Bill()
            bill.bind(billNode)
            bills << bill
        }
        bills
    }

    public put(Bill bill){
        def billXml = bill.toXML()
        def location = httpService.post("/bills", billXml)
        location.value.split('/')[-1]
    }
}