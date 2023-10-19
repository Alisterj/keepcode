package org.keepcode.task1.aggregator.http;

import java.io.IOException;

public class ConnectionException extends IOException {

    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
