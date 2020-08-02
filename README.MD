## WireMock client groovy DSL

This is just a wrapper around `com.github.tomakehurst.wiremock.client.WireMock` for more robust mock creation.

For example, this stub definition:
```java
stubFor(get(urlEqualTo("/some/thing"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "text/plain")
                .withBody("Hello world!")));
```

can be defined with groovy DSL:
```groovy
{
  request {
    method 'GET'
    url '/some/thing'
  }
  response {
    header('Content-Type', 'text/plain')
    body 'Hello world!'
  }
}
```