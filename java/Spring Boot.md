## Introduction

If you do not include webflux or web dependency, then it does not start a webserver. So you can use its features like logging, config, DI...

You can get its maven dependency from [Spring Initializr](https://start.spring.io/)

To add dependency, firstly, use spring pom parent:

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.1.1</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>
```

Then add dependency:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
</dependency>
```

If web server is not included, then spring would shut down soon after starting up

## logging

### slf4j & common-logging

They are both interfaces used to do logging. i.e. similar to JDBC, they only provide interface, you may use any implementation that supports them, e.g. log4j2

I would use slf4j since its API is easier to use

### How to configure log4j2

#### Why log4j2?

Spring boot's default logging framework is logback and provides some configurations for the internal logger. However, the configuration items are very limited, you even cannot change the log format!

So have to use underlying implementation's config files. Since I'm more familiar with log4j2 confg and log4j2 is the fastest, finally chose log4j2

#### Maven Dependency

[official doc](https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto.logging.log4j)

First exclude default logback dependency and then add log4j2 dependency:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>
```

And if you would like to use log4j2.yaml, following dependencies need to be added:

```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.dataformat</groupId>
    <artifactId>jackson-dataformat-yaml</artifactId>
</dependency>
```

#### Basic Log4j2 config

```yaml
Configuration:
  status: info

  appenders:
    Console:
      name: LogToConsole
      PatternLayout:
        Pattern: "[%level] %d{yyyy-MM-dd HH:mm:ss.SSS Z} [%t] %c - %msg%n"

    RollingFile:
      - name: LogToRollingFile
        fileName: logs/app.log
        filePattern: "logs/$${date:yyyy-MM}/app-%d{yyyy-MM-dd}.log.zip"
        PatternLayout:
          pattern: "[%level] %d{yyyy-MM-dd HH:mm:ss.SSS Z} [%t] %c - %msg%n"
        Policies:
          TimeBasedTriggeringPolicy: { }

  Loggers:
    Root:
      level: info
      AppenderRef:
        - ref: LogToConsole
        - ref: LogToRollingFile
```

Sample output (check [this doc](https://logging.apache.org/log4j/2.x/manual/layouts.html#PatternLayout) for pattern format):

```
[INFO] 2023-07-17 16:53:15.493 +0900 [main] com.sgq.tool.log.TLogger - Test abc
[ERROR] 2023-07-17 16:53:15.493 +0900 [main] com.sgq.tool.log.TLogger - Test exception
java.lang.RuntimeException: def
	at com.sgq.tool.sync.App.main(App.java:15) [classes/:?]
```

Log file rollover would be done daily.

#### Java Code

Setup

```java
package com.sgq.tool.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface TLogger
{
	Logger TLog = LoggerFactory.getLogger(TLogger.class);
}
```

Usage:

```java
TLog.info("Test {}", "abc");
TLog.error("Test exception", new RuntimeException("def"));
```



## Tips

### Do task on startup

Use annotation `@EventListener(ApplicationReadyEvent.class)` or `@PostConstruct`

NOTE: both of them would run code in the main thread. So do not do long-blocking stuff there

### Clean up on shutdown

Just annotate with `@PreDestroy`

NOTE: This would not work if main thread is blocked in other places