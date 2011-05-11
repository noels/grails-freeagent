// configuration for plugin testing - will not be included in the plugin zip
 
log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
           'org.codehaus.groovy.grails.web.pages', //  GSP
           'org.codehaus.groovy.grails.web.sitemesh', //  layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping', // URL mapping
           'org.codehaus.groovy.grails.commons', // core / classloading
           'org.codehaus.groovy.grails.plugins', // plugins
           'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'

    warn   'org.mortbay.log'
    debug  'groovyx.net.http.RESTClient',
           'org.apache.http.headers',
           'org.apache.http.wire'
    //*/
}

environments {
    development {
        freeagent{
            userId = 'grailsplugintest@grails.org'
            password = 'gr41l5'
            uri = 'https://grailsplugintest.freeagentcentral.com'
        }
    }
    test {
        freeagent{
            userId = 'testUser'
            password = 'testPassword'
            uri = 'http://localhost:8099'
        }
    }
}


springcache {
    defaults {
        // set default cache properties that will apply to all caches that do not override them
        eternal = false
        diskPersistent = false
    }
    caches {
        HttpGetCache {
            // set any properties unique to this cache
            memoryStoreEvictionPolicy = "LRU"
        }
    }
}
grails.views.default.codec="none" // none, html, base64
grails.views.gsp.encoding="UTF-8"
