package ru.job4j.calculate;
/**
 * Calculate
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $id$
 * @since 0.1
 */
public class Calculate {
    private double result;

    public double getResult() {
        return this.result;
    }

    public void add(double first, double second) {
        this.result = first + second;
    }

    public void sub(double first, double second) {
        this.result = first - second;
    }

    public void mult(double first, double second) {
        this.result = first * second;
    }

    public void div(double first, double second) {
        this.result = first / second;
    }
}