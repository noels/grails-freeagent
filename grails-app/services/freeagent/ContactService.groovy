package freeagent

import freeagent.domain.Contact

class ContactService{
    HttpService httpService

    /**
     * Return a list of contacts from freeagent
     *
     * @return List of contacts
     */
    public get(){
        def contacts = []
        def resp = httpService.get('/contacts', [:])
        resp.children().each{ contactNode ->
            def contact = new Contact()
            contact.bind(contactNode)
            contacts << contact
        }
        contacts
    }

    public put(Contact contact){
        def contactXml = contact.toXML()
        def location = httpService.post("/contacts", contactXml)
        location.value.split('/')[-1]
    }
}