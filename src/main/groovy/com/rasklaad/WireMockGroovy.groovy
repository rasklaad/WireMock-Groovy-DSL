package com.rasklaad

import com.github.tomakehurst.wiremock.client.WireMock
import groovy.transform.TypeChecked

@TypeChecked
class WireMockGroovy {
    private String url
    private int port
    private final WireMock client

    WireMockGroovy(String url, int port) {
        this.url = url
        this.port = port
        client = new WireMock(url, port)
    }

    def mock(@DelegatesTo(strategy = Closure.DELEGATE_ONLY, value = MockRequest) Closure cl) {
        def mockRequest = new MockRequest()
        def code = cl.rehydrate(mockRequest, this, this)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
        def stubMapping = mockRequest.build()
        client.register(stubMapping)
        return stubMapping
    }

}
