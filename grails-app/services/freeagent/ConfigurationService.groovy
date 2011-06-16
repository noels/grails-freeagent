package freeagent

class ConfigurationService {
    def config
    def grailsApplication


    def getFreeagentUserId() {
        grailsApplication.config?.freeagent?.userId
    }

    def getFreeagentPassword() {
        grailsApplication.config?.freeagent?.password
    }

    def getFreeagentURI(){
        grailsApplication.config?.freeagent?.uri
    }

}
