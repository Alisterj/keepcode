package org.keepcode.task1.aggregator.requester;

import org.keepcode.task1.aggregator.http.HttpClient;
import org.keepcode.task1.aggregator.http.Response;
import org.keepcode.task1.aggregator.parser.Country;
import org.keepcode.task1.aggregator.parser.Number;
import org.keepcode.task1.aggregator.parser.Parser;
import org.keepcode.task1.logger.CustomLogger;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Requester {
    private static final String API_LINK = "https://onlinesim.io/api/getFreeList";
    private static final String API_LINK_COUNTRY = API_LINK + "?country=%s";

    private final HttpClient httpClient;
    private final Parser parser;

    public Requester() {
        this.httpClient = new HttpClient();
        this.parser = new Parser();
    }

    public List<Country> getCountries() throws RequesterException {
        final String data = getData(API_LINK);
        CustomLogger.getInstance().info("Получены страны");

        return parser.getCountries(data);
    }
    public List<Number> getNumbers(Country country) throws RequesterException {
        final String apiLink = API_LINK_COUNTRY.formatted(country.getId());
        final String data = getData(apiLink);
        CustomLogger.getInstance().info("Получен список номеров по стране - " + country.getName());

        return parser.getNumbers(data);
    }

    public String getData(String apiLink) throws RequesterException {
        final Response response;

        try {
            response = httpClient.get(new URL(apiLink));

            if (response.getCode() != HttpURLConnection.HTTP_OK) {
                CustomLogger.getInstance().error("Ошибка " + response.getCode());
                throw new RequesterException("Error, you have don't current data");
            }
        } catch (IOException e) {
            CustomLogger.getInstance().error("Ошибка при выполнении HTTP-запроса");
            throw new RequesterException("Data error", e);
        }

        return response.getData();
    }
}
