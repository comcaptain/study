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