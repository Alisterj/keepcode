package org.keepcode.aggregator.parser;

import org.json.JSONArray;
import org.json.JSONObject;

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
        return countries;
    }

    public List<Number> getNumbers(Long id) {
        return null;
    }
}