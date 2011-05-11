package freeagent

class ConfigurationService {
    def config

    def ConfigurationService(grailsApplication) {
        this.grailsApplication = grailsApplication;
        config = grailsApplication.config
    }

    def ConfigurationService(){
        def filePath = new File('grails-app/conf/Config.groovy').toURL()
        config = new ConfigSlurper(System.properties.get('grails.env')).parse(filePath)
    }

    def getFreeagentUserId() {
        config?.freeagent?.userId
    }

    def getFreeagentPassword() {
        config?.freeagent?.password
    }

    def getFreeagentURI(){
        config?.freeagent?.uri
    }

}
