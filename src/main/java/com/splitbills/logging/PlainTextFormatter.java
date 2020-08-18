package com.splitbills.logging;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PlainTextFormatter implements Formatter {

    private final static String DELIMITER = " ";
    private final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Override
    public String format(LogRecord logRecord) {

        List<Object> arguments = logRecord.getElements();
        String result = format(arguments);
        result = result.trim();
        return result + System.lineSeparator();
    }

    private String format(List<Object> arguments) {
        StringBuilder result = new StringBuilder();
        for (Object argument : arguments) {
            if (argument instanceof LocalDateTime) {
                LocalDateTime date = (LocalDateTime) argument;
                argument = date.format(DateTimeFormatter.ofPattern(DATE_FORMAT));

            }
            if (argument != null) {
                result.append(argument);
                result.append(DELIMITER);
            }
        }
        return result.toString();
    }
}
