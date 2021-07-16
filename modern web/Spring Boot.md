## What is spring boot?

> Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can "just run".

- It's not a web server. It's just a basic framework of other spring frameworks
- For example, if you want to turn it into a web server, you can add either of following 2 spring frameworks:
  - Spring Web. Or you can call it Spring MVC
  - Spring Reactive Web. Or you can call it Spring WebFlux
- You can get a clear picture from [Spring Initializr](https://start.spring.io/)

## [Developer Tools](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.devtools)

```groovy
https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.devtools
```

### [Override default properties](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.devtools.property-defaults)

- It overrides a few properties, e.g. it disables HTTP caching
- You can add `spring.devtools.add-properties` to application.properties to turn off this feature

### ["Hot" Auto-Restart](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.devtools.restart)

- In Intellij, do a build would trigger it
- It's much faster than restart

### [Run server remotely with IntelliJ](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.devtools.remote-applications)

- With it, you can run remote servers with its own config in your IntelliJ
- Hot auto-restart would still work. And once changed, the jar would be automatically uploaded to remote