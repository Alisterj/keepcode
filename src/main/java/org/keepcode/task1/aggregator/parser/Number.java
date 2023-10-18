package org.keepcode.task1.aggregator.parser;

public class Number {
    private String number;

    public Number( String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Toll-free phone number: %s".formatted(number);
    }
}
