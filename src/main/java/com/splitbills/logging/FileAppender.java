package com.splitbills.logging;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class FileAppender implements Appender {

    private final Path location;
    private final Formatter formatter;
    private final String lock;

    FileAppender(Path logLocation, String logName) {
        if (logLocation == null) {
            throw new IllegalArgumentException("Parameter logLocation cannot be null");
        }
        if (logName == null) {
            throw new IllegalArgumentException("Parameter logName cannot be null");
        }
        this.formatter = new PlainTextFormatter();
        location = createLocation(logLocation, logName);
        lock = createLock();
    }

    private Path createLocation(Path logLocation, String logName) {
        try {
            Files.createDirectories(logLocation);
        } catch (IOException ioException) {
            throw new BadLocationException("Cannot create subdirectories at: "
                    + logLocation, ioException);
        }
        return logLocation.resolve(logName);
    }

    private String createLock() {
        File file = location.toFile();
        String lock;
        try {
            lock = file.getCanonicalPath();
        } catch (IOException ioException) {
            throw new BadLocationException(ioException);
        }
        return lock.intern();
    }

    @Override
    public void append(LogRecord logRecord) throws AppenderLoggingException {
        if (logRecord == null) {
            return;
        }
        synchronized (lock) {
            String formattedRecord = formatter.format(logRecord);
            try (OutputStream os = Files.newOutputStream(location, CREATE, APPEND)) {
                os.write(formattedRecord.getBytes());
            } catch (IOException ioException) {
                throw new AppenderLoggingException(ioException);
            }
        }
    }
}
