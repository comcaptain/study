## [Architecture](https://logging.apache.org/log4j/2.x/manual/architecture.html)

![Log4j 2 Class Relationships](C:\git\study\modern web\images\Log4jClasses.jpg)

- **LoggerContext** is the central singleton for the entire log4j2 system
- **Configuration** Every `LoggerContext` has a `Configuration` that contains log4j2's all configurations
- **Logger**
  - The class that user uses to do logging. e.g. `Logger.info`
  - It's created by `LoggerManager.getLogger("<logger name>")`
- **LoggerConfig**
  - Each `Logger` has one corresponding `LoggerConfig`
  - `LoggerConfig` has parent attribute
- **Appender**
  - The instance that actually outputs log
  - We have appender that outputs to console, file...
- **Layout**
  - Decides format of log
  - Each `Appender` would have a `Layout`

### Hierarchy

- Hierarchy is done via logger name
  - Parent of `a.b.c` is `a.b`
  - Parent of `a` is `root` (you can get root logger by invoking `LogManager.getRootLogger()`)
- Although conceptually, `Logger` has hierarchy. But behind the scene, the hierarchy is actually maintained by `LoggerConfig`
  - Each `LoggerConfig` has a parent attribute
- When getting a logger `a.b.c`, if there is no conf for `a.b` or `a.b.c`, the conf for `a` would be used
- Child config would inherite parent's conf values, e.g. log level
- Log would be output to current & parent config's appenders

## How to use

```java
private static final Logger logger = LogManager.getLogger(MyApp.class);
public void test() {
    // ...
    // By default, it uses {} as placeholder
    // If you want to use `String.format` style, then you should use LogManager.getFormatterLogger(xxx) to get logger
    logger.info("Test {} {}", a, b);
    // You can use lambda to improve performance
    logger.trace("Some long-running operation returned {}", () -> expensiveOperation());
}
```

## [Config](https://logging.apache.org/log4j/2.x/manual/configuration.html)

### Conf file loation

- Default location is log4j2.yaml/json/xml/properties on the classpath
- You can change it by setting system property `log4j2.configurationFile`

### Additivity

- By default, logger would also output to parent's appenders

- You can turn this off by setting `additivity` to false like below:

  ```xml
  <Logger name="com.foo.Bar" level="trace" additivity="false">
      <AppenderRef ref="Console"/>
  </Logger>
  ```

  ### Configuration Element

  - Root tag of the whole conf xml file
  - You can get full available attribute list from [Log4j – Configuring Log4j 2 (apache.org)](https://logging.apache.org/log4j/2.x/manual/configuration.html), here are a few interesting ones:
    - **monitorInterval** Time unit is second. e.g. If set to 60, then log4j2 would automatically reload config in every 60 seconds
    - **status** Log level of log4j2 itself

  ### [Add yaml conf file support](https://logging.apache.org/log4j/2.x/runtime-dependencies.html)

  ```groovy
  implementation "com.fasterxml.jackson.core:jackson-databind:version"
  implementation "com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:version"
  ```

  ### [Enable Async Logging via Disruptor](https://logging.apache.org/log4j/2.x/manual/async.html)

  Add disruptor dependency:

  ```groovy
  implementation 'com.lmax:disruptor:3.4.4'
  ```

  Add following option to make all loggers async:

  ```bash
  -Dlog4j2.contextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector

### Sample Config

```yaml
Configuration:
  status: warn
  name: Pygmalion
  properties:
    property:
      - name: fileName
        value: logs/pygmalion.log
      - name: archiveFilePattern
        value: logs/pygmalion.%d{yyyy-MM-dd}.log.gz
      - name: patternLayout
        # %d{ABSOLUTE} -> 14:34:02,781
        # %N -> Outputs the result of System.nanoTime()
        # %level -> Level of log
        # %m -> logged message
        # %exception{full} -> full stack trace, same as Throwable.printStackTrace
        # %n -> platform dependent line separator
        value: "%d{ABSOLUTE} %N %level [%threadName] %m %exception{full}%n"
  appenders:
    Console:
      name: STDOUT
      target: SYSTEM_OUT
      PatternLayout:
        Pattern: ${patternLayout}
    RollingFile:
      name: File
      fileName: ${fileName}
      # The .gz extension would make log4j2 compress old log files
      filePattern: ${archiveFilePattern}
      # Create a new file when value of the date in filePattern is changed. i.e. %d{yyyy-MM-dd}
      TimeBasedTriggeringPolicy:
        interval: 1
      PatternLayout:
        Pattern: ${patternLayout}

  Loggers:
    logger:
      - name: dev
        level: debug
      - name: prod
        level: info
    Root:
      level: info
      AppenderRef:
        - ref: STDOUT
        - ref: File
```

### Sample Static Log class

```java
public final class Log
{
	private static final Logger _logger = LogManager.getLogger(System.getProperty("mode", "prod"));

	public static void debug(final String message, final Object... params)
	{
		_logger.debug(message, params);
	}

	public static void info(final String message, final Object... params)
	{
		_logger.info(message, params);
	}

	public static void warn(final String message, final Object... params)
	{
		_logger.warn(message, params);
	}

	public static void error(final String message, final Object... params)
	{
		_logger.error(message, params);
	}

	public static void error(final Throwable e, final String message, final Object... params)
	{
		_logger.error(message, params, e);
	}
}
```

