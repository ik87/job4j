package ru.job4j.max;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Max {
    /**
     * get max from 2 values.
     * @param first value1.
     * @param second value2.
     * @return max value.
     */
    public int max(int first, int second) {
        return first > second ? first : second;
    }

    /**
     * get max from 3 values.
     * @param first value1.
     * @param second value2.
     * @param third value3.
     * @return max value.
     */
    public  int max(int first, int second, int third) {
        return max(first, max(second, third));
    }
}
