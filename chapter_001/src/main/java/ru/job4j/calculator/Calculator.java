package ru.job4j.calculator;
/**
 * Calculate
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $id$
 * @since 0.1
 */
public class Calculator {
    private double result;

    public double getResult() {
        return this.result;
    }

    /**
     *  Сложение
     * @param first первый аргумент
     * @param second второй аргумент
     */
    public void add(double first, double second) {
        this.result = first + second;
    }

    /**
     * Вычитание
     * @param first первый аргумент
     * @param second второй аргумент
     */
    public void sub(double first, double second) {
        this.result = first - second;
    }

    /**
     * Умножение
     * @param first первый аргумент
     * @param second второй аргумент
     */
    public void mult(double first, double second) {
        this.result = first * second;
    }

    /**
     * Деление
     * @param first первый аргумент
     * @param second второй аргумент
     */
    public void div(double first, double second) {
        this.result = first / second;
    }
}