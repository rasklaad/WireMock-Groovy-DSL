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

## Installation

```
<dependency>
    <groupId>com.rasklaad</groupId>
    <artifactId>wiremock-groovy-dsl</artifactId>
    <version>0.0.1</version>
</dependency>
```

NOTE:
You must define you own WireMock dependency:
```
<groupId>com.github.tomakehurst</groupId>
<artifactId>wiremock-jre8</artifactId>
<version>your.wiremock.version</version>
```

