package ru.job4j.tracker;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public interface Input {
    String ask(String question);
    int ask(String question, int[] range);
}
