package org.keepcode.aggregator;

import org.keepcode.aggregator.http.ConnectionException;
import org.keepcode.aggregator.parser.Country;

import java.net.MalformedURLException;
import java.util.List;

public class NumberAggregator {
    private final Requester requester;

    public NumberAggregator() {
        this.requester = new Requester();
    }

    public List<Country> getNumbersCountry() throws MalformedURLException, ConnectionException {
        return requester.getCountries();
    }
}
