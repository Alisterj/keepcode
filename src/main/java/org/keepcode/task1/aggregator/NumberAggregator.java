package org.keepcode.task1.aggregator;

import org.keepcode.task1.aggregator.parser.Country;
import org.keepcode.task1.aggregator.parser.Number;
import org.keepcode.task1.aggregator.requester.Requester;
import org.keepcode.task1.aggregator.requester.RequesterException;
import org.keepcode.task1.logger.CustomLogger;

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
    /**
     * Директория для сохраненных файлов с данными
     */
    private static final String COUNTRY_NUMBERS_DIR = "data";

    /**
     * Map для хранения данных о номерах телефонов для каждой страны
     * Ключ - название страны, значение - список объектов {@link Number} для данной страны.
     */
    private final Map<String, List<Number>> numbersCountry = new ConcurrentHashMap<>();
    /**
     * Объект {@link Requester} для выполнения запросов к внешнему API.
     */
    private final Requester requester;


    /**
     * Конструктор класса NumberAggregator
     */
    public NumberAggregator() {
        this.requester = new Requester();
    }

    /**
     * Получает данные о номерах телефонов для каждой страны.
     * @return {@link #numbersCountry} HashMap в котором ключ - название страны, значение - лист объектов {@link Number}
     * @throws RequesterException Если произошла ошибка при запросе данных о странах или номерах телефонов.
     * @throws RuntimeException Если возникла ошибка при обработке результатов запросов или сохранении данных в файл.
     */
    public Map<String, List<Number>> getNumbersCountry() throws RequesterException {
        final List<Country> countries = requester.getCountries();

        if (countries == null) {
            return null;
        }

        ExecutorService executorService = Executors.newFixedThreadPool(countries.size());

        for (Country country: countries) {
            executorService.submit(() -> {
                try {
                    final String countryName = country.getName();
                    final List<Number> numbers;
                    numbers = requester.getNumbers(country);
                    numbersCountry.put(countryName, numbers);

                    Thread.sleep(1000);
                } catch (InterruptedException | RequesterException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        executorService.shutdown();

        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }

            saveData();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            CustomLogger.getInstance().error("Ошибка данные не сохранились в файл");
            throw new RuntimeException("files were not saved, folder creation error", e);
        }

        return numbersCountry;
    }

    /**
     * Сохранение данных({@link #numbersCountry}) в csv файл
     * @throws IOException Если произошла ошибка сохранения в файл csv
     */
    private void saveData() throws IOException {
        final File dataDir = new File(COUNTRY_NUMBERS_DIR);

        if (!dataDir.exists() && !dataDir.mkdir()){
            throw new IOException("Problem with the csv folder");
        }

        try {
            final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy_MM_dd_hh_mm");
            final String fileDir = "%s/numbersCountry_%s.csv".formatted(COUNTRY_NUMBERS_DIR, LocalDateTime.now().format(df));
            Writer writer = writer(fileDir);
            writer.flush();

            CustomLogger.getInstance().info("HashMap успешно сохранена в CSV");
        } catch (IOException e) {
            CustomLogger.getInstance().error("Ошибка сохранения в файл csv");
            throw new RuntimeException("Create file for writer exception", e);
        }
    }

    /**
     * Создает объект {@link Writer} для записи данных в файл.
     * @param fileDir Директория файла, в который будут записаны данные.
     * @return Объект {@link Writer} для записи данных в файл.
     * @throws IOException Если произошла ошибка при создании файла или записи данных.
     */
    private Writer writer(String fileDir) throws IOException {
        Writer writer = new FileWriter(fileDir);

        int counterRow = 1;
        writer.append("Index,Country,Number\n");

        for (Map.Entry<String, List<Number>> entry : numbersCountry.entrySet()) {
            for (Number number: entry.getValue()) {
                final String strCounter = Integer.toString(counterRow++);
                final String strCountry = entry.getKey();
                final String strNumber = number.getNumber();

                writer.append("%s,%s,%s\n".formatted(strCounter, strCountry, strNumber));
            }
        }
        return writer;
    }
}
