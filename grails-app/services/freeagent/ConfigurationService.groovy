package freeagent

class ConfigurationService {
    def config = org.codehaus.groovy.grails.commons.ConfigurationHolder.config


    def getFreeagentUserId() {
        config.freeagent?.userId
    }

    def getFreeagentPassword() {
        config.freeagent?.password
    }

    def getFreeagentURI(){
        config.freeagent?.uri
    }

}
