```java
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import org.slf4j.LoggerFactory;

public class LogbackConfigPrinter {

    public static void printLogbackConfig() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

        for (ch.qos.logback.classic.Logger logger : context.getLoggerList()) {
            for (Appender<ILoggingEvent> appender : logger.iteratorForAppenders()) {
                if (appender instanceof ch.qos.logback.core.OutputStreamAppender) {
                    ch.qos.logback.core.OutputStreamAppender<ILoggingEvent> osAppender = (ch.qos.logback.core.OutputStreamAppender<ILoggingEvent>) appender;
                    if (osAppender.getEncoder() instanceof PatternLayoutEncoder) {
                        PatternLayoutEncoder encoder = (PatternLayoutEncoder) osAppender.getEncoder();
                        System.out.println("Logger: " + logger.getName() + 
                                           ", Appender: " + appender.getName() + 
                                           ", Pattern: " + encoder.getPattern());
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        printLogbackConfig();
    }
}

```
