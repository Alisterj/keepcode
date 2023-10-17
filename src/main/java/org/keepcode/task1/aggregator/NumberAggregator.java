package org.keepcode.task1.aggregator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.keepcode.task1.aggregator.http.ConnectionException;
import org.keepcode.task1.aggregator.parser.Country;
import org.keepcode.task1.aggregator.parser.Number;
import org.keepcode.task1.aggregator.requester.Requester;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class NumberAggregator extends Application {
    private final Map<String, List<Number>> numbersCountry = new ConcurrentHashMap<>();
    private final Requester requester;

    public NumberAggregator() {
        this.requester = new Requester();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/formCountryNumbers.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Country with numbers");
        stage.show();
    }

    public static void launchNumberAggregator(String[] args) {
        launch(args);
    }

    public Map<String, List<Number>> getNumbersCountry() throws MalformedURLException, ConnectionException {
        final List<Country> countries = requester.getCountries();

        ExecutorService executorService = Executors.newFixedThreadPool(countries.size());

        for (int i = 0; i < 1; i++) {
            executorService.submit(() -> {
                try {
                    final String countryName = countries.get(0).getName();
                    final List<Number> numbers = requester.getNumbers(countries.get(0).getId());
                    numbersCountry.put(countryName, numbers);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (ConnectionException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        executorService.shutdown();

        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (Map.Entry<String, List<Number>> entry : numbersCountry.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }

        return numbersCountry;
    }
}
