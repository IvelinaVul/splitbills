package com.splitbills.logging;

import java.time.LocalDateTime;
import java.util.Comparator;

public class SimpleLogger extends Logger {

    private Level appendLevel;
    private final Appender[] appenders;
    private final AfterFailureAppender afterFailureAppender;

    SimpleLogger(Appender... appenders) {
        this.appenders = appenders;
        this.appendLevel = Level.INFO;
        afterFailureAppender = new AfterFailureAppender();
    }

    public void log(Level level, String message) {
        log(level, message, null);
    }

    public void log(Level level, String message, Throwable throwable) {
        Comparator<Level> comparator = Comparator.comparingInt(Level::getValue);
        if (comparator.compare(level, appendLevel) <= 0) {
            LocalDateTime now = LocalDateTime.now();
            LogRecord logRecord = new LogRecord(now, level, message, throwable);
            append(logRecord);
        }
    }

    private void append(LogRecord logRecord) {
        for (Appender appender : appenders) {
            try {
                appender.append(logRecord);
            } catch (Exception appendingException) {
                afterFailureAppender.append(logRecord, appendingException);
            }
        }
    }

    public void setLevel(Level level) {
        appendLevel = level;
    }

    public Level getLevel() {
        return appendLevel;
    }
}
