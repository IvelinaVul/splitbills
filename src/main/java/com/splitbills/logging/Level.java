package com.splitbills.logging;

public enum Level {
    OFF(1),
    FATAL(2),
    ERROR(3),
    WARN(4),
    INFO(5),
    DEBUG(6);
    private int value;

    Level(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
