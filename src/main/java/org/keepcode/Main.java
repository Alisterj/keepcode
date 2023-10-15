package org.keepcode;

import org.keepcode.aggregator.NumberAggregator;
import org.keepcode.aggregator.http.ConnectionException;

import java.net.MalformedURLException;

public class Main {

    public static void main(String[] args) throws MalformedURLException, ConnectionException {
        NumberAggregator numberAggregator = new NumberAggregator();
        numberAggregator.getNumbersCountry();
    }
}