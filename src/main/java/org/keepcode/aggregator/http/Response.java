package org.keepcode.aggregator.http;

public class Response {
    private final int code;
    private final String data;

    public Response(int code, String data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return """
            Code: %s
            Data: %s
            """.formatted(code, data);
    }
}
