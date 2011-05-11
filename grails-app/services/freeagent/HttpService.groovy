package freeagent

import grails.plugin.springcache.annotations.Cacheable
import grails.plugin.springcache.annotations.CacheFlush
import groovyx.net.http.RESTClient
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.ContentType

class HttpService {
    ConfigurationService configurationService
    private RESTClient client = new RESTClient(configurationService?.freeagentURI?:'')
    private uri = configurationService?.freeagentURI?:''

    public HttpService() {
    }

    private init(){
        withRest(uri: uri) {
            auth.basic configurationService?.freeagentUserId?:'', configurationService?.freeagentPassword?:''
        }
    }

    private def getAuth(){
        def user = configurationService?.freeagentUserId
        def passwd = configurationService?.freeagentPassword
        "$user:$passwd".getBytes('iso-8859-1').encodeBase64()
    }

    private def getResponse(String path, query = [:]) {
        HttpResponseDecorator response = null
        withRest(uri: configurationService?.freeagentURI) {
            auth.basic configurationService?.freeagentUserId, configurationService?.freeagentPassword?:''
            response = get(path: path,
                    contentType: ContentType.XML,
                    headers:[
                            //Authorization:"Basic ${getAuth()}",
                            "Content-Type": ContentType.XML,
                            "Accept":"application/xml"],
                    query: query)
            response.data
        }
    }

    @Cacheable("HttpGetCache")
    def get(String path, query = [:]) throws IOException {
        getResponse(path, query)
    }

    @CacheFlush("HttpGetCache")
    def post(String path, String payload) {
        withRest(uri: configurationService?.freeagentURI){
            auth.basic configurationService?.freeagentUserId, configurationService?.freeagentPassword?:''
            handler."201" = {resp ->
                //when we get a 201 response, the Location parameter will contain the entity id that was created
                //we will want to use that probably.
                return resp.getHeaders()['Location']
            }
            handler."400" = {resp, reader ->
                def errors = reader.error.collect{it.text()}.join("\n")
                throw new RuntimeException(errors)
            }
            handler."500" = { resp ->
                throw new RuntimeException('something bad happened - freeagent returned 500, but I have no further details')
            }
            post(   path: path,
                    body: payload,
                    requestContentType: ContentType.XML,
                    headers:[
                            //Authorization:"Basic ${getAuth()}",
                            "Content-Type": ContentType.XML,
                            "Accept":"application/xml"],
            )
        }
    }

    def delete(String path) {
    }

    @CacheFlush("HttpGetCache")
    def put(String path, String payload = '') {
        withRest(uri:configurationService?.freeagentURI){
            auth.basic configurationService?.freeagentUserId, configurationService?.freeagentPassword?:''
            handler."403" = {resp ->
                throw new RuntimeException('Forbidden')
            }
            //When we do a put, the freeagent server doesn't send any response body that can be parsed.
            handler.success = {resp ->
                //this is here to stop the sax parser chocking on the empty response from the freeagent server
            }
            put(
                    path: path,
                    body: payload,
                    requestContentType: ContentType.XML,
                    headers:[
                            //Authorization:"Basic ${getAuth()}",
                            "Content-Type": ContentType.XML,
                            "Accept":"application/xml",
                    ]
            )
        }
    }

    def put(String path, File file) {
    }
}


