package ru.job4j.functionalinterface;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class Diapason {
    /**
     * Calculate digits row with any functions
     *
     * @param start start
     * @param end end
     * @param func your lambda function
     * @return result
     */
    List<Double> diapason(int start, int end, Function<Double, Double> func) {
        List<Double> range = new ArrayList<>();
        for (int i = start; i != end; i++) {
            range.add(func.apply((double) i));
        }
        return range;
    }
}
