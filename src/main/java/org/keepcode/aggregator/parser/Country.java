package org.keepcode.aggregator.parser;

import java.util.List;

public class Country {
    private final Long id;
    private String name;
    private List<Number> numbers;

    public Country(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /*public Country(Long id, String name, List<Number> numbers) {
        this.id = id;
        this.name = name;
        this.numbers = numbers;
    }*/

    @Override
    public String toString() {
        return """
            Country: %s
            List free numbers: %s
            """.formatted(name, numbers);

    }
}
