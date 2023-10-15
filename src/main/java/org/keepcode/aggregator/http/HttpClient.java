package org.keepcode.aggregator.http;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {

    public static void main(String[] args) throws IOException, ConnectionException {
        final String API_LINK = "https://onlinesim.io/api/getFreeList";
        Response response = get(new URL(API_LINK));
        System.out.println(response);
    }

    private static Response connect(URL url, HttpMethod httpMethod) throws ConnectionException {
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

    public static Response get(URL url) throws ConnectionException {
        return connect(url, HttpMethod.GET);
    }

    public static Response head(URL url) throws ConnectionException {
        return connect(url, HttpMethod.HEAD);
    }
}
