package org.keepcode.task1.aggregator;

import org.keepcode.task1.aggregator.http.ConnectionException;
import org.keepcode.task1.aggregator.parser.Country;
import org.keepcode.task1.aggregator.parser.Number;
import org.keepcode.task1.aggregator.requester.Requester;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class NumberAggregator {
    private final Map<String, List<Number>> numbersCountry = new ConcurrentHashMap<>();
    private final Requester requester;

    public NumberAggregator() {
        this.requester = new Requester();
    }

    public Map<String, List<Number>> getNumbersCountry() throws MalformedURLException, ConnectionException {
        final List<Country> countries = requester.getCountries();

        ExecutorService executorService = Executors.newFixedThreadPool(countries.size());

        for (int i = 0; i < 2; i++) { //Веменное ограничение
            int finalI = i;
            executorService.submit(() -> {
                try {
                    final String countryName = countries.get(finalI).getName();
                    TimeUnit.SECONDS.sleep(1);
                    final List<Number> numbers = requester.getNumbers(countries.get(finalI).getId());
                    numbersCountry.put(countryName, numbers);
                } catch (MalformedURLException | ConnectionException | InterruptedException e) {
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

        return numbersCountry;
    }
}
