package com.splitbills.logging;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LogRecord {

    private final LocalDateTime date;
    private final Level level;
    private final String message;
    private final Throwable thrown;

    LogRecord(LocalDateTime date, Level level, String message, Throwable thrown) {
        this.date = date;
        this.level = level;
        this.message = message;
        this.thrown = thrown;
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

    public Throwable getThrown() {
        return thrown;
    }

    public List<Object> getElements() {
        List<Object> elements = new ArrayList<>();
        elements.add(date);
        elements.add(level);
        elements.add(message);
        elements.add(thrown);
        return elements;
    }
}
