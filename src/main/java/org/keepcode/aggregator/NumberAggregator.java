package org.keepcode.aggregator;

import org.keepcode.aggregator.http.ConnectionException;
import org.keepcode.aggregator.parser.Country;
import org.keepcode.aggregator.parser.Number;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;

public class NumberAggregator {
    private final Requester requester;

    public NumberAggregator() {
        this.requester = new Requester();
    }

    public void getNumbersCountry() throws MalformedURLException, ConnectionException {
        HashMap<String, List<Number>> numbersCountry = new HashMap<>();
        final List<Country> countries = requester.getCountries();

        for (Country country: countries) {
            final String countryName = country.getName();
            final List<Number> numbers = requester.getNumbers(country.getId());

            numbersCountry.put(countryName, numbers);
        }
    }
}
