package com.splitbills.logging;

public enum Level {
    OFF(1),
    FATAL(2),
    ERROR(3),
    WARN(4),
    INFO(5),
    DEBUG(6);
    private int level;

    Level(int level) {
        this.level = level;
    }

}
