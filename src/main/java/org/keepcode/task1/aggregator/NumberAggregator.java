package org.keepcode.task1.aggregator;

import org.keepcode.task1.aggregator.parser.Country;
import org.keepcode.task1.aggregator.parser.Number;
import org.keepcode.task1.aggregator.requester.Requester;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class NumberAggregator {
    private static final String COUNTRY_NUMBERS_DIR = "data";

    private final Map<String, List<Number>> numbersCountry = new ConcurrentHashMap<>();
    private final Requester requester;

    public NumberAggregator() {
        this.requester = new Requester();
    }

    public Map<String, List<Number>> getNumbersCountry() {
        final List<Country> countries = requester.getCountries();

        ExecutorService executorService = Executors.newFixedThreadPool(countries.size());

        for (Country country: countries) {
            executorService.submit(() -> {
                try {
                    final String countryName = country.getName();
                    final List<Number> numbers = requester.getNumbers(country);
                    numbersCountry.put(countryName, numbers);

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        executorService.shutdown();

        saveData();

        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return numbersCountry;
    }

    private void saveData() {
        final File dataDir = new File(COUNTRY_NUMBERS_DIR);

        if (!dataDir.exists()){
            dataDir.mkdir();
        }

        try {
            final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy_MM_dd_hh_mm");
            final String fileDir = "%s/numbersCountry_%s.csv".formatted(COUNTRY_NUMBERS_DIR, LocalDateTime.now().format(df));
            Writer writer = new FileWriter(fileDir);
            writer.append("kkkkk");

            /*for (Map.Entry<String, List<Number>> entry : numbersCountry.entrySet()) {
                writer.append(entry.getKey());
                writer.append(",");
                for (Number number: entry.getValue()) {
                    writer.append(number.getNumber());
                }
                writer.append(System.getProperty("line.separator"));
            }*/
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
