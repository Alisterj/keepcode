package org.keepcode.task2.process;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class CustomLogger {

    private static final CustomLogger INSTANCE = new CustomLogger();
    private final Logger logger;

    private CustomLogger() {
        this.logger = Logger.getLogger("myName");
    }

    public static CustomLogger getInstance() {
        return INSTANCE;
    }

    public void write(String str, String commandText) {
        logger.log(new LogRecord(Level.INFO, str + commandText));
    }
}
