package com.rasklaad

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import com.github.tomakehurst.wiremock.http.DelayDistribution
import com.github.tomakehurst.wiremock.http.HttpHeaders
import groovy.transform.PackageScope
import groovy.transform.TypeChecked

@TypeChecked
@PackageScope
class ResponseDefinition {

    private final ResponseDefinitionBuilder delegate = new ResponseDefinitionBuilder()


    void status(int statusCode) {
        delegate.withStatus(statusCode)
    }

    void header(String key, String ... values) {
        delegate.withHeader(key, values)
    }

    void bodyFile(String fileName) {
        delegate.withBodyFile(fileName)
    }

    void body(String body) {
        delegate.withBody(body)
    }

    void body(byte [] body) {
        delegate.withBody(body)
    }

    void delay(Integer milliseconds) {
        delegate.withFixedDelay(milliseconds)
    }

    void randomDelay(DelayDistribution delayDistribution) {
        delegate.withRandomDelay(delayDistribution)
    }

    void transformers(String ... transformers) {
        delegate.withTransformers(transformers)
    }

    void transformerParameter(String name, String value) {
        delegate.withTransformerParameter(name, value)
    }

    void transformerParameters(Map<String, Object> params) {
        delegate.withTransformerParameters(params)
    }

    void transformer(String transformerName, String parameterKey, Object parameterValue) {
        delegate.withTransformer(transformerName, parameterKey, parameterValue)
    }

    void base64Body(String body) {
        delegate.withBase64Body(body)
    }

    void statusMessage(String message) {
        delegate.withStatusMessage(message)
    }

    void oroxy(String proxyUrl) {
        delegate.proxiedFrom(proxyUrl)
    }

    void headers(HttpHeaders headers) {
        delegate.withHeaders(headers)
    }

    com.github.tomakehurst.wiremock.http.ResponseDefinition build() {
        return delegate.build()
    }
}
