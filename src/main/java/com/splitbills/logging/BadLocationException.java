package com.splitbills.logging;

public class BadLocationException extends RuntimeException {

    public BadLocationException() {
        super();
    }

    public BadLocationException(String message) {
        super(message);
    }

    public BadLocationException(Throwable cause) {
        super(cause);
    }

    public BadLocationException(String message, Throwable cause) {
        super(message, cause);
    }

}
