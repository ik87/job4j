package ru.job4j.converter;

/**
 * Конвертер валюты
 */
public class Converter {
    private final int dollar = 60;
    private final int euro = 70;

    /**
     * Конвертируем рубли в евро.
     * @param value рубли.
     * @return Евро.
     */
    public int rubleToEuro(int value) {
        return value / euro;
    }

    /**
     * Конвертируем рубли в доллары.
     * @param value рубли.
     * @return Доллары.
     */
    public int rubleToDollar(int value) {
        return value / dollar;
    }

    /**
     * Конвертируем доллары в рубли.
     * @param value доллар.
     * @return Рубли.
     */
    public int dollarToRuble(int value) {
        return value * dollar;
    }

    /**
     * Конвертируем евро в рубли.
     * @param value евро.
     * @return Рубли.
     */
    public int euroToRuble(int value) {
        return value * euro;
    }
}
