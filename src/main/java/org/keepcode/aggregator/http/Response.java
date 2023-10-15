package org.keepcode.aggregator.http;

public class Response {
    private final int code;
    private final String data;

    public Response(int code, String data) {
        this.code = code;
        this.data = data;
    }

    @Override
    public String toString() {
        return """
            Code: %s
            Data: %s
            """.formatted(code, data);
    }
}
