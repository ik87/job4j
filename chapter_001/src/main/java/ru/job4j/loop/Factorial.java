package ru.job4j.loop;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru).
 * @version $ID$
 * @since 0.1
 */
public class Factorial {
    /**
     * Calculate factorial.
     * @param n number.
     * @return factorial.
     */
    public int calc(int n) {
        int fact = 1;
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }
}
