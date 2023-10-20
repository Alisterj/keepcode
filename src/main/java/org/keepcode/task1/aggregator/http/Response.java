package org.keepcode.task1.aggregator.http;

public class Response {
    /**
     * Код ответа от сервера
     */
    private final int code;

    /**
     * Данные полученные от сервера
     */
    private final String data;

    /**
     * Конструктор класса Response.
     * @param code Код выполнения запроса в виде int
     * @param data Данные полученные от сервера в виде String
     */
    public Response(int code, String data) {
        this.code = code;
        this.data = data;
    }

    /**
     * Метод получения кода ответа от сервера
     * @return {@link #code}
     */
    public int getCode() {
        return code;
    }

    /**
     * Метод получения данных от сервера
     * @return {@link #data}
     */
    public String getData() {
        return data;
    }

    /**
     * Возвращает строковое представление объекта Response.
     * @return Строковое представление объекта, включающее код и данные.
     */
    @Override
    public String toString() {
        return """
            Code: %s
            Data: %s
            """.formatted(code, data);
    }
}
