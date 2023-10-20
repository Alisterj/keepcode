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
    /**
     * Ссылка на API для получения данных о странах.
     */
    private static final String API_LINK = "https://onlinesim.io/api/getFreeList";
    /**
     * Шаблон ссылки на API для получения номеров по определенной стране
     */
    private static final String API_LINK_COUNTRY = API_LINK + "?country=%s";

    /**
     * Объект {@link HttpClient} для выполнения HTTP-запросов.
     */
    private final HttpClient httpClient;
    /**
     * Объект {@link Parser} для обработки данных, полученных от сервера.
     */
    private final Parser parser;

    /**
     * Конструктор класса Requester
     */
    public Requester() {
        this.httpClient = new HttpClient();
        this.parser = new Parser();
    }

    /**
     * Метод для получения списка стран.
     * @return Список стран {@link Parser#getCountries(String)}
     * @throws RequesterException
     */
    public List<Country> getCountries() throws RequesterException {
        final String data = getData(API_LINK);
        CustomLogger.getInstance().info("Получены страны");

        return parser.getCountries(data);
    }

    /**
     * Метод получения номеров
     * @param country объект {@link Country}
     * @return Возвращает список объектов {@link Number} по стране
     * @throws RequesterException
     */
    public List<Number> getNumbers(Country country) throws RequesterException {
        final String apiLink = API_LINK_COUNTRY.formatted(country.getId());
        final String data = getData(apiLink);
        CustomLogger.getInstance().info("Получен список номеров по стране - " + country.getName());

        return parser.getNumbers(data);
    }

    /**
     * Получает данные с сервера по заданной ссылке API.
     * @param apiLink Ссылка на API для получения данных.
     * @return {@link Response#getData()} Данные, полученные с сервера.
     * @throws RequesterException Если произошла ошибка при выполнении HTTP-запроса или получении данных
     */
    public String getData(String apiLink) throws RequesterException {
        final Response response;

        try {
            response = httpClient.get(new URL(apiLink));

            if (response.getCode() != HttpURLConnection.HTTP_OK) {
                CustomLogger.getInstance().error("Ошибка " + response.getCode(), apiLink);
                throw new RequesterException("Error, you have don't current data");
            }
        } catch (IOException e) {
            CustomLogger.getInstance().error("Ошибка при выполнении HTTP-запроса");
            throw new RequesterException("Data error", e);
        }

        return response.getData();
    }
}
