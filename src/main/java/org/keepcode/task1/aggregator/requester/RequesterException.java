package org.keepcode.task1.aggregator.requester;

public class RequesterException extends java.lang.Exception {

    public RequesterException(String message) {
        super(message);
    }
    public RequesterException(String message, Throwable e) {
        super(message, e);
    }
}
