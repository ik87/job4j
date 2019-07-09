package ru.job4j.check_task;

import java.util.Arrays;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $id$
 * @since 0.1
 */
public class StreamAPI {
    /**
     * Get sum of even squared stream
     *
     * @param array int array
     * @return sum squared stream
     */
    public static int evenSquaredSum(int[] array) {
        return Arrays.stream(array)
                .filter(x -> x % 2 == 0)
                .map(x -> x * x)
                .sum();
    }
}
