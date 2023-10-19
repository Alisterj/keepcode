package org.keepcode.task1.aggregator.http;

import org.keepcode.task1.logger.CustomLogger;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {

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
            CustomLogger.getInstance().error("Error connecting ");
            throw new ConnectionException("Error connecting ", e);
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }
    }

    public Response get(URL url) throws ConnectionException {
        CustomLogger.getInstance().info("Запуск метода GET");
        return connect(url, HttpMethod.GET);
    }

    public Response head(URL url) throws ConnectionException {
        CustomLogger.getInstance().info("Запуск метода HEAD");
        return connect(url, HttpMethod.HEAD);
    }

}
