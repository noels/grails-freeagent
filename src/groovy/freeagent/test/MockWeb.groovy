package freeagent.test

import com.sun.net.httpserver.*
import org.apache.commons.io.FileUtils

class MockWeb {
    private String response = 'null'
    private HttpServer server = null

    String contentType = 'application/xml'
    Integer status = 200


    public MockWeb(Integer port, String path = '/') {
        server = HttpServer.create(new InetSocketAddress(port),0)
        def ctx = server.createContext(path, {HttpExchange exchange ->
            setResponseHeaders(exchange.responseHeaders)
            exchange.sendResponseHeaders(status, response.bytes.length)
            exchange.responseBody.write(response.bytes)
            exchange.responseBody.close()
        } as HttpHandler)

        /* doesn't work - get and aoob error.
        ctx.setAuthenticator(new  BasicAuthenticator(){
            boolean checkCredentials(String username, String password){
                username == 'testUser' && password == 'testPassword'
            }
        })
        */

        server.start()
    }


    public void stop() {
        if (server) server.stop(0)
    }


    public void setResponse(String filename) {
        response = FileUtils.readFileToString(new File(filename), "UTF-8")
    }


    private void setResponseHeaders(Headers headers) {
        headers["Content-Type"] = [contentType]
    }
}