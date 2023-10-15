package org.keepcode.aggregator;

import org.keepcode.aggregator.http.HttpClient;

public class Requester {
    private final static String API_LINK = "https://onlinesim.io/api/getFreeList";
    private final HttpClient httpClient;
    private final Parser parser;

    public Requester() {
        this.httpClient = new HttpClient();
        this.parser = new Parser();
    }
}
