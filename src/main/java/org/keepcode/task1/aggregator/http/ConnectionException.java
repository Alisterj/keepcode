package org.keepcode.task1.aggregator.http;

import java.io.IOException;

public class ConnectionException extends IOException {

    /**
     * Конструктор для создания объекта ConnectionException с указанным сообщением.
     * @param message Сообщение об ошибке, описывающее причину исключения.
     */
    public ConnectionException(String message) {
        super(message);
    }

    /**
     * Конструктор для создания объекта ConnectionException с указанным сообщением и причиной
     * @param message Сообщение об ошибке, описывающее причину исключения.
     * @param cause Причина исключения, представленная в виде другого исключения.
     */
    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
