package org.keepcode.task1.logger;

import java.io.IOException;
import java.util.logging.*;

/**
 * Класс для логирования информации и ошибок в различные файлы.
 */
public class CustomLogger {
    private static final CustomLogger INSTANCE = new CustomLogger();
    private static final String INFO_LOG_FILE_PATH = "logs/info.log";
    private static final String ERROR_LOG_FILE_PATH = "logs/error.log";

    private final Logger infoLogger;
    private final Logger eroorLogger;

    /**
     * Приватный конструктор для создания единственного экземпляра класса CustomLogger.
     */
    private CustomLogger() {
        this.infoLogger = createLogger(INFO_LOG_FILE_PATH, "INFO");
        this.eroorLogger = createLogger(ERROR_LOG_FILE_PATH, "ERROR");
    }

    /**
     * Создает и настраивает объект Logger для записи логов в файл.
     * @param path Путь к файлу логов.
     * @param name Имя логгера.
     * @return Объект {@link Logger} для записи логов.
     * @throws RuntimeException Если произошла ошибка при инициализации логгера.
     */
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

    /**
     * Возвращает единственный экземпляр CustomLogger.
     * @return Экземпляр {@link CustomLogger}
     */
    public static CustomLogger getInstance() {
        return INSTANCE;
    }

    /**
     * Записывает информационное сообщение в лог.
     * @param message Сообщение для записи в лог.
     */
    public void info(String message) {
        infoLogger.log(new LogRecord(Level.INFO, message));
    }

    /**
     * Записывает сообщение об ошибке в лог.
     * @param message Сообщение об ошибке для записи в лог.
     */
    public void error(String message) {
        eroorLogger.log(new LogRecord(Level.SEVERE, message));
    }

    /**
     * Записывает сообщение об ошибке в лог.
     * @param message Сообщение об ошибке для записи в лог.
     * @param url Ссылка связанная с ошибкой
     */
    public void error(String message, String url) {
        eroorLogger.log(new LogRecord(Level.SEVERE, message + " " + url));
    }
}