package freeagent

class ConfigurationService {
    def config
    def grailsApplication

    def ConfigurationService(grailsApplication) {
        this.grailsApplication = grailsApplication;
        config = grailsApplication.config
    }

    def ConfigurationService(){
        /*
        def CONFIG_LOCATION_APP_HOME = 'app.home.directory'
        def configPath = ''
        if (System.properties[CONFIG_LOCATION_APP_HOME]) {
            configPath = System.properties[CONFIG_LOCATION_APP_HOME]
        }

        def filePath = new File("${configPath}grails-app/conf/Config.groovy").toURL()
        config = new ConfigSlurper(System.properties.get('grails.env')).parse(filePath)
        */
        //config = application.config
    }

    def getFreeagentUserId() {
        application.config?.freeagent?.userId
    }

    def getFreeagentPassword() {
        application.config?.freeagent?.password
    }

    def getFreeagentURI(){
        application.config?.freeagent?.uri
    }

}
