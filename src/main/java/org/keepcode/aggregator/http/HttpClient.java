package org.keepcode.aggregator.http;

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

                return new Response(connection.getResponseCode(), bufferedReader.readLine());
            }

            return new Response(connection.getResponseCode(), null);

        } catch (IOException e) {
            throw new ConnectionException("Error connecting ", e);
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }
    }

    public Response get(URL url) throws ConnectionException {
        return connect(url, HttpMethod.GET);
    }

    public Response head(URL url) throws ConnectionException {
        return connect(url, HttpMethod.HEAD);
    }

}
