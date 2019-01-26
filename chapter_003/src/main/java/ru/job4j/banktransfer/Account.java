package ru.job4j.banktransfer;


/**
 * @author Kosolapov Ilya(d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class Account {
    private double value;
    private String requisites;

    public Account(double value, String requisites) {
        this.value = value;
        this.requisites = requisites;
    }

    public double getValue() {
        return value;
    }

    public void addValue(double value) {
        this.value += value;
    }

    public void debitValue(double value) {
        this.value -= value;
    }

    public String getRequisites() {
        return requisites;
    }

    @Override
    public String toString() {
        return String.format("requisites: %s value: %s", requisites, value);
    }

}
