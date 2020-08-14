package com.splitbills.logging;

import java.time.LocalDateTime;

public class LogRecord {

    private LocalDateTime date;
    private Level level;
    private String message;
    private Throwable throwable;

    LogRecord(LocalDateTime date, Level level, String message, Throwable throwable) {
        this.date = date;
        this.level = level;
        this.message = message;
        this.throwable = throwable;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Level getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

}
