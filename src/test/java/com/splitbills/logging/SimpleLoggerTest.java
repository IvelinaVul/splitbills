package com.splitbills.logging;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class SimpleLoggerTest {

    @Mock
    Appender appender;

    @Test
    public void logIgnoreWithLevelLessThanDefault() throws AppenderLoggingException {
        SimpleLogger logger = new SimpleLogger(appender);
        String message = "message";
        logger.log(Level.DEBUG, message);
        verify(appender, never()).append(any());
    }

    @Test
    public void logAfterSettingLevel() throws AppenderLoggingException {
        SimpleLogger logger = new SimpleLogger(appender);
        String message = "message";
        logger.setLevel(Level.WARN);
        logger.log(Level.ERROR, message);
        verify(appender, atLeastOnce()).append(any());
    }

}
