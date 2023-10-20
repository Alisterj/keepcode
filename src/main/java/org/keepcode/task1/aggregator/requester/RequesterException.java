package org.keepcode.task1.aggregator.requester;

public class RequesterException extends java.lang.Exception {

    /**
     * Конструктор для создания объекта RequesterException с указанным сообщением.
     * @param message Сообщение об ошибке, описывающее причину исключения.
     */
    public RequesterException(String message) {
        super(message);
    }

    /**
     * Конструктор для создания объекта RequesterException с указанным сообщением и причиной
     * @param message Сообщение об ошибке, описывающее причину исключения.
     * @param e Причина исключения, представленная в виде другого исключения.
     */
    public RequesterException(String message, Throwable e) {
        super(message, e);
    }
}
