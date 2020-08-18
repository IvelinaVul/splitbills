package com.splitbills.logging;

public class AppenderLoggingException extends Exception {

    public AppenderLoggingException(Throwable cause) {
        super(cause);
    }

    public AppenderLoggingException(String message, Throwable cause) {
        super(message, cause);
    }
}
