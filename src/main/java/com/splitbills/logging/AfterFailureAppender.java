package com.splitbills.logging;

public class AfterFailureAppender extends ConsoleAppender {

    private final Formatter formatter;

    AfterFailureAppender() {
        formatter = new PlainTextFormatter();
    }

    public void append(LogRecord logRecord, Throwable failureCause) {
        final String errorMessage = "Faulty logging at record:";
        final String causeMessage = "Cause: ";

        String formattedRecord = formatter.format(logRecord);

        System.err.println(errorMessage);
        System.err.println(formattedRecord);

        if (failureCause != null) {
            System.err.println(causeMessage + failureCause);
        }
    }

    @Override
    public void append(LogRecord logRecord) {
        append(logRecord, null);
    }
}
