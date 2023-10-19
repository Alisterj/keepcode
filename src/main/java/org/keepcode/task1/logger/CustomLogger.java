package org.keepcode.task1.logger;

import java.io.IOException;
import java.util.logging.*;

public class CustomLogger {
    private static final CustomLogger INSTANCE = new CustomLogger();
    private static final String INFO_LOG_FILE_PATH = "logs/info.log";
    private static final String ERROR_LOG_FILE_PATH = "logs/error.log";

    private final Logger infoLogger;
    private final Logger eroorLogger;

    private CustomLogger() {
        this.infoLogger = createLogger(INFO_LOG_FILE_PATH, "INFO");
        this.eroorLogger = createLogger(ERROR_LOG_FILE_PATH, "ERROR");
    }

    private Logger createLogger(String path, String name) {
        Logger logger = Logger.getLogger(name);

        try {
            FileHandler handler = new FileHandler(path, true);
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);

        } catch (IOException e) {
            throw new RuntimeException("Logger initialization exception", e);
        }

        return logger;
    }

    public static CustomLogger getInstance() {
        return INSTANCE;
    }

    public void info(String message) {
        infoLogger.log(new LogRecord(Level.INFO, message));
    }

    public void error(String message) {
        eroorLogger.log(new LogRecord(Level.SEVERE, message));
    }
}