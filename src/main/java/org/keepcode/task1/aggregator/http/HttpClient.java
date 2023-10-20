package org.keepcode.task1.aggregator.http;

import org.keepcode.task1.logger.CustomLogger;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {

    /**
     * Метод устанавливает соединение с указанным URL, используя указанный HTTP-метод
     * @param url {@link URL}, с которым устанавливается соединение.
     * @param httpMethod {@link HttpMethod} для указания типа запроса (GET, POST, и т.д.)
     * @return {@link Response} представляющий результат соединения, если код ответа не 200, то второй параметр - поле data, будет null
     * @throws ConnectionException ошибка соединения
     */
    private Response connect(URL url, HttpMethod httpMethod) throws ConnectionException {
        HttpsURLConnection connection = null;

        try {
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod(httpMethod.name());
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                final InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
                final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                CustomLogger.getInstance().info(connection.getResponseCode() + " данные прочитаны");
                return new Response(connection.getResponseCode(), bufferedReader.readLine());
            }

            CustomLogger.getInstance().info("Результат соединения: " + connection.getResponseCode() + " данные пустые");
            return new Response(connection.getResponseCode(), null);

        } catch (IOException e) {
            CustomLogger.getInstance().error("Ошибка соединения");
            throw new ConnectionException("Error connecting ", e);
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }
    }

    /**
     * Метод GET выполняет HTTP-запрос по указанному URL.
     * @param url {@link URL}, к которому будет выполнен запрос.
     * @return Объект {@link Response}, представляющий результат выполнения запроса.
     * @throws ConnectionException Ошибка соединения
     */
    public Response get(URL url) throws ConnectionException {
        CustomLogger.getInstance().info("Запуск метода GET");
        return connect(url, HttpMethod.GET);
    }

    /**
     * Метод HEAD выполняет HTTP-запрос по указанному URL.
     * @param url {@link URL}, к которому будет выполнен запрос.
     * @return Объект {@link Response}, представляющий результат выполнения запроса.
     * @throws ConnectionException Ошибка соединения
     */
    public Response head(URL url) throws ConnectionException {
        CustomLogger.getInstance().info("Запуск метода HEAD");
        return connect(url, HttpMethod.HEAD);
    }

}
