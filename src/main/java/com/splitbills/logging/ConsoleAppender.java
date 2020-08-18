package com.splitbills.logging;

public class ConsoleAppender implements Appender {

    private final static String DELIMITER = " ";

    @Override
    public void append(LogRecord record) {
        System.err.println(record.getLevel() + DELIMITER + record.getMessage());
    }

}
