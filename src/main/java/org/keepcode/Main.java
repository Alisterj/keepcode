package org.keepcode;

import org.keepcode.aggregator.NumberAggregator;
import org.keepcode.aggregator.http.ConnectionException;
import org.keepcode.aggregator.parser.Country;

import java.net.MalformedURLException;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws MalformedURLException, ConnectionException {
        NumberAggregator numberAggregator = new NumberAggregator();
        List<Country> countries = numberAggregator.getNumbersCountry();

        String stringCountries = countries.stream().map(Country::toString).collect(Collectors.joining("\n"));
        System.out.println(stringCountries);
    }
}