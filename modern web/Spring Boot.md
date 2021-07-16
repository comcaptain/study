## What is spring boot?

> Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can "just run".

- It's not a web server. It's just a basic framework of other spring frameworks
- For example, if you want to turn it into a web server, you can add either of following 2 spring frameworks:
  - Spring Web. Or you can call it Spring MVC
  - Spring Reactive Web. Or you can call it Spring WebFlux
- You can get a clear picture from [Spring Initializr](https://start.spring.io/)

## [Load Conf Files](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#features.external-config)

### Conf file priorities

Check [this doc](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#features.external-config) for detail

### YAML conf file

- It's chosen because it supports far more features than plain properties file
- You can master it in 5 mintues by reading [Learn yaml in Y Minutes (learnxinyminutes.com)](https://learnxinyminutes.com/docs/yaml/)

### Pass conf from command line

```bash
# Any parameter whose name starts with -- would be treated as conf
# Following option would inject conf name=Spring
# This conf has highest priority in non-testing environment
java -jar app.jar --name="Spring"
```

### Conf file location

- Default conf file name is `application.conf` / `application.yaml`

- You can change conf file location in 2 ways:

  - Set `spring.config.location`
    - You should set this before system starts up
  - Set `spring.config.import`
    - You can set it in conf files

- `spring.config.location` and `spring.config.import` share the same value format:

  - Multiple paths should be seprated by comma
    - For yaml, you can use sequence instead
  - Each path can be directory path or file path. Direactory path must end with `/`
  - Wildcard `*` is supported but can only be used on folder names, not on filename

  ### Environment Specific Conf Design

  File strucutre:

  ```
  conf/
    mickey/
      application.yaml
      extra.yaml
    tony/
      application.yaml
      extra.yaml
      extra2.yaml
    application.yaml
  ```

  Following argurments are added when system starts up:

  ```
  --spring.config.location=conf/ --environment=tony
  ```

  `application.yaml` loads environment-specific conf files like below:

  ```yaml
  spring:
    config:
      # Load environment-specific config
      import: optional:./${environment}/
  ```

  `tony/application.yaml` imports `extra.yaml` and `extra2.yaml` like below:

  ```yaml
  spring:
    config:
      import:
        # These 2 files are relevant to current directory
        # So they would only load extra.yaml in current directory
        - optional:extra.yaml
        - optional:extra2.yaml
  ```

  Since imported conf files have higher priority, so environment-specific conf would override shared conf

## [Build & Deploy](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#deployment.containers)

- **Build** 

  - Run gradle task `bootJar`, then you can find the runnable jar in `build/libs` directory
  - This task comes from gradle plugin `id 'org.springframework.boot' version '2.5.2'`

- **Deploy**

  - Just copy the built jar file to anywhere  you like and then use `java -jar` to run it

  - Or, you can choose to unzip the jar file and then run. This would speed up the startup time a bit, but has no influence on runtime performance:

    ```
    jar -xf myapp.jar
    java org.springframework.boot.loader.JarLauncher
    ```

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