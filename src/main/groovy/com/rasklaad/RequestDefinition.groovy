package com.rasklaad

import com.github.tomakehurst.wiremock.client.BasicCredentials
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.extension.Parameters
import com.github.tomakehurst.wiremock.http.Request
import com.github.tomakehurst.wiremock.http.RequestMethod
import com.github.tomakehurst.wiremock.matching.*
import groovy.transform.PackageScope
import groovy.transform.TypeChecked

import static com.google.common.collect.Lists.newArrayList
import static com.google.common.collect.Lists.newLinkedList
import static com.google.common.collect.Maps.newLinkedHashMap

@TypeChecked
@PackageScope
class RequestDefinition {

    private String scheme
    private StringValuePattern hostPattern
    private Integer port
    private RequestMethod method = RequestMethod.ANY
    private UrlPattern urlPattern = UrlPattern.ANY
    private Map<String, MultiValuePattern> headers = newLinkedHashMap()
    private Map<String, MultiValuePattern> queryParams = newLinkedHashMap()
    private List<ContentPattern<?>> bodyPatterns = newArrayList()
    private Map<String, StringValuePattern> cookies = newLinkedHashMap()
    private BasicCredentials basicCredentials
    private List<MultipartValuePattern> multiparts = newLinkedList()

    private ValueMatcher<Request> customMatcher

    private CustomMatcherDefinition customMatcherDefinition

    void method(RequestMethod method) {
        this.method = method
    }

    void method(String methodName) {
        this.method = RequestMethod.fromString(methodName)
    }

    void url(String url) {
        urlPattern = WireMock.urlEqualTo(url)
    }

    void urlMatching(String regex) {
        urlPattern = WireMock.urlMatching(regex)
    }

    void anyUrl() {
        urlPattern = UrlPattern.ANY
    }

    void urlPath(String urlPath) {
        urlPattern = WireMock.urlPathEqualTo(urlPath)
    }

    void urlPathMatching(String urlPathRegex) {
        urlPattern = WireMock.urlPathMatching(urlPathRegex)
    }

    void header(String key, StringValuePattern valuePattern) {
        headers.put(key, MultiValuePattern.of(valuePattern))
    }


    void withoutHeader(String key) {
        headers.put(key, MultiValuePattern.absent())
    }

    void queryParam(String key, StringValuePattern valuePattern) {
        queryParams.put(key, MultiValuePattern.of(valuePattern))
    }

    void host(StringValuePattern hostPattern) {
        this.hostPattern = hostPattern
    }

    void port(int port) {
        this.port = port
    }

    void scheme(String scheme) {
        this.scheme = scheme
    }


    void body(ContentPattern valuePattern) {
        this.bodyPatterns.add(valuePattern)
    }

    void bodyPart(MultipartValuePattern multiPattern) {
        if (multiPattern != null) {
            multiparts.add(multiPattern)
        }
    }



    void auth(String username, String password) {
        this.basicCredentials = new BasicCredentials(username, password)
    }

    void cookie(String key, StringValuePattern valuePattern) {
        cookies.put(key, valuePattern)
    }




    void customMatcher(String customMatcherName, Parameters parameters) {
        this.customMatcherDefinition = new CustomMatcherDefinition(customMatcherName, parameters)
    }


    void customMatcher(ValueMatcher<Request> valueMatcher) {
        this.customMatcher = valueMatcher
    }

    RequestPattern build() {
         new RequestPattern(
                scheme,
                hostPattern,
                port,
                urlPattern,
                method,
                headers.isEmpty() ? null : headers,
                queryParams.isEmpty() ? null : queryParams,
                cookies.isEmpty() ? null : cookies,
                basicCredentials,
                bodyPatterns.isEmpty() ? null : bodyPatterns,
                customMatcherDefinition,
                customMatcher,
                multiparts.isEmpty() ? null : multiparts
        )

    }
}
