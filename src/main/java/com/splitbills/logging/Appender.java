package com.splitbills.logging;

public interface Appender {

    void append(LogRecord record) throws AppenderLoggingException;
}
