package org.keepcode.aggregator;

import org.keepcode.aggregator.http.ConnectionException;
import org.keepcode.aggregator.http.HttpClient;
import org.keepcode.aggregator.http.Response;
import org.keepcode.aggregator.parser.Country;
import org.keepcode.aggregator.parser.Number;
import org.keepcode.aggregator.parser.Parser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Requester {
    private final static String API_LINK = "https://onlinesim.io/api/getFreeList";
    private final HttpClient httpClient;
    private final Parser parser;

    public Requester() {
        this.httpClient = new HttpClient();
        this.parser = new Parser();
    }

    public List<Country> getCountries() throws MalformedURLException, ConnectionException {
        final Response response = httpClient.get(new URL(API_LINK));
        final String data = response.getData();
        return parser.getCountries(data);
    }
    public List<Number> getNumbers() { //This for next type.
        return null;
    }
}
