package org.keepcode.aggregator.parser;

public class Country {
    private final Long id;

    private String name;

    public Country(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Country: %s".formatted(name);
    }
}
