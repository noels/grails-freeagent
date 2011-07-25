package freeagent

class BillController{
    def billService

    def index = {
        def bills = billService.get()
        return [bills:bills]

    }
}