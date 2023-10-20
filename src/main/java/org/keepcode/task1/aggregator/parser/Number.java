package org.keepcode.task1.aggregator.parser;

public class Number {
    /**
     * Номер телефона
     */
    private String number;

    /**
     * Конструктор класса Number.
     * @param number Номер телефона в виде строки.
     */
    public Number( String number) {
        this.number = number;
    }

    /**
     * Метод получения номера
     * @return {@link #number}
     */
    public String getNumber() {
        return number;
    }

    /**
     * Метод устанавливает новый номер телефона.
     * @param number Новый номер телефона
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Возвращает строковое представление объекта Number.
     * @return Строковое представление объекта, включающее текст и номер телефона
     */
    @Override
    public String toString() {
        return "Toll-free phone number: %s".formatted(number);
    }
}
