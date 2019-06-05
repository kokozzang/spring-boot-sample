import ch.qos.logback.classic.PatternLayout
import ch.qos.logback.core.*;
import ch.qos.logback.core.encoder.*;
import ch.qos.logback.core.read.*;
import ch.qos.logback.core.rolling.*;
import ch.qos.logback.core.status.*;
import ch.qos.logback.classic.net.*;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import org.springframework.boot.logging.logback.ColorConverter;
import org.springframework.boot.logging.logback.ExtendedWhitespaceThrowableProxyConverter;
import org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter;


conversionRule("clr", ColorConverter)
conversionRule("wex", WhitespaceThrowableProxyConverter)
conversionRule("wEx", ExtendedWhitespaceThrowableProxyConverter)


def DEFAULT_FOLDER = "./logs"

appender('COMMON_CONSOLE_APPENDER', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
//        pattern = "%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
        pattern = "%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} %clr(%5p) %clr([%thread]){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n%wEx"
    }

}

appender('COMMON_CONSOLE_FILE_APPENDER', RollingFileAppender) {
    file = DEFAULT_FOLDER + "/ap-member-console.log"
    append = true
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = DEFAULT_FOLDER + "/ap-member-console-%d{yyyy-MM-dd}.gz"
        maxHistory = 5
    }
    encoder(PatternLayoutEncoder) {
//        pattern = "%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
        pattern = "%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} %clr(%5p) %clr([%thread]){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n%wEx"
    }

}

appender('COMMON_FILE_APPENDER', RollingFileAppender){
    file = DEFAULT_FOLDER + "/ap-member.log"
    append = true
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = DEFAULT_FOLDER + "/ap-member-%d{yyyy-MM-dd}.gz"
        maxHistory = 30
    }
    encoder(PatternLayoutEncoder){
        pattern = "%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} %clr(%5p) %clr([%thread]){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n%wEx"
    }
}

root(INFO, ["COMMON_CONSOLE_APPENDER", "COMMON_CONSOLE_FILE_APPENDER"])

logger("com.amorepacific", INFO, ["COMMON_FILE_APPENDER"])