package org.keepcode.task1.aggregator.requester;

import org.keepcode.task1.aggregator.http.ConnectionException;
import org.keepcode.task1.aggregator.http.HttpClient;
import org.keepcode.task1.aggregator.http.Response;
import org.keepcode.task1.aggregator.parser.Country;
import org.keepcode.task1.aggregator.parser.Number;
import org.keepcode.task1.aggregator.parser.Parser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Requester {
    private final static String API_LINK = "https://onlinesim.io/api/getFreeList";
    private final static String API_LINK_COUNTRY = API_LINK + "?country=%s";

    private final HttpClient httpClient;
    private final Parser parser;

    public Requester() {
        this.httpClient = new HttpClient();
        this.parser = new Parser();
    }

    public List<Country> getCountries() throws MalformedURLException, ConnectionException {
        final String data = getData(API_LINK);

        return parser.getCountries(data);
    }
    public List<Number> getNumbers(Long countryId) throws MalformedURLException, ConnectionException {
        final String apiLink = API_LINK_COUNTRY.formatted(countryId);
        final String data = getData(apiLink);

        return parser.getNumbers(data);
    }

    public String getData(String apiLink) throws MalformedURLException, ConnectionException {
        final Response response = httpClient.get(new URL(apiLink));

        return response.getData();
    }
}
