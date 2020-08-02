package com.rasklaad

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.http.RequestMethod
import org.junit.jupiter.api.Test

class BasicTest {

    @Test
    void mock() {
        WireMockServer server = new WireMockServer()
        server.start()

        def wm = new WireMockGroovy('localhost', 8080)
        wm.mock {
            request {
                method RequestMethod.GET
            }
            response {
                status 200
                body 'Done!'
            }
        }
        println server.getStubMappings()
    }
}
