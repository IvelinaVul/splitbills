package com.splitbills.logging;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public abstract class Logger {

    private final static Map<String, Logger> LOGGERS = new HashMap<>();
    private final static String PATH_TO_LOGS = "." + File.separator + "logs";
    private final static String LOG_NAME = "splitbills.log";

    public abstract void log(Level level, String message);

    public abstract void log(Level level, String message, Throwable throwable);

    /**
     * Setting a level causes a Logger to ignore messages with a greater level.
     */
    public abstract void setLevel(Level level);

    public abstract Level getLevel();

    public static synchronized Logger getLogger(String name) {
        if (!LOGGERS.containsKey(name)) {
            Appender fileAppender = new FileAppender(Path.of(PATH_TO_LOGS), LOG_NAME);
            Appender consoleAppender = new ConsoleAppender();
            LOGGERS.put(name, new SimpleLogger(fileAppender, consoleAppender));
        }
        return LOGGERS.get(name);
    }

}
