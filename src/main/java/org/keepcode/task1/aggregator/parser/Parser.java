package org.keepcode.task1.aggregator.parser;

import org.json.JSONArray;
import org.json.JSONObject;
import org.keepcode.task1.logger.CustomLogger;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    public List<Country> getCountries(String data) {
        final JSONObject jsonObject = new JSONObject(data);
        final JSONArray countriesObject = jsonObject.getJSONArray("countries");

        List<Country> countries = new ArrayList<>();

        for (int i = 0; i < countriesObject.length(); i++) {
            final JSONObject countryObject = countriesObject.getJSONObject(i);
            final Long id = countryObject.getLong("country");
            final String name = countryObject.getString("country_original");

            countries.add(new Country(id, name));
        }

        CustomLogger.getInstance().info("Получен лист со странами из json");

        return countries;
    }

    public List<Number> getNumbers(String data) {
        final JSONObject jsonObject = new JSONObject(data).getJSONObject("numbers");

        List<Number> numbers = new ArrayList<>();

        for (String key: jsonObject.keySet()) {
            final JSONObject jsonObject1 = jsonObject.getJSONObject(key);
            final String number = jsonObject1.getString("full_number");

            numbers.add(new Number(number));
        }

        CustomLogger.getInstance().info("Получен лист с номерами из json");

        return numbers;
    }
}
