package org.keepcode.task1.aggregator.parser;

public class Country {
    /**
     * Id страны
     */
    private final Long id;
    /**
     * Наименование страны
     */
    private String name;

    /**
     * Конструктор класса Country.
     * @param id Идентификатор страны, представленный в виде Long.
     * @param name Название страны в виде строки.
     */
    public Country(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Метод получения id страны
     * @return {@link #id}
     */
    public Long getId() {
        return id;
    }

    /**
     * Метод получения наименования страны
     * @return {@link #name}
     */
    public String getName() {
        return name;
    }

    /**
     * Метод для установки нового значения наименования страны
     * @param name Новое значение наименования страны
     */
    public void setName(String name) {
        this.name = name;
    }
}
