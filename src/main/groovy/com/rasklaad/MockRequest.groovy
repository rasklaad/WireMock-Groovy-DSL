package com.rasklaad

import com.github.tomakehurst.wiremock.common.Metadata
import com.github.tomakehurst.wiremock.matching.RequestPattern
import com.github.tomakehurst.wiremock.stubbing.StubMapping
import groovy.transform.PackageScope

@PackageScope
class MockRequest {

    private RequestPattern requestPattern
    private com.github.tomakehurst.wiremock.http.ResponseDefinition response


    private UUID id = UUID.randomUUID()
    private String name
    private boolean isPersistent = false


    void request(@DelegatesTo(strategy = Closure.DELEGATE_ONLY, value = RequestDefinition) Closure cl) {
        def requestDefinition = new RequestDefinition()
        def code = cl.rehydrate(requestDefinition, this, this)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()

        requestPattern = requestDefinition.build()
    }

    void response(@DelegatesTo(strategy = Closure.DELEGATE_ONLY, value = ResponseDefinition) Closure cl) {
        def responseDefinition = new ResponseDefinition()
        def code = cl.rehydrate(responseDefinition, this, this)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()

        response = responseDefinition.build()
    }


    void id(UUID id) {
        this.id = id
    }

    void name(String name) {
        this.name = name
    }

    void persistent(boolean persistent) {
        this.isPersistent = persistent
    }

    void postServeAction() {
        // TODO
    }


    void metaData(Metadata metadata) {
        // TODO
    }

    StubMapping build() {
        StubMapping stubMapping = new StubMapping(requestPattern, response)
        stubMapping.setId(id)
        stubMapping.setName(name)
        stubMapping.setPersistent(isPersistent)
        return stubMapping
    }
}
