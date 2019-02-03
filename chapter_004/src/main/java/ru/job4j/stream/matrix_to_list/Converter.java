package ru.job4j.stream.matrix_to_list;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class Converter {
    /**
     * Convert Integer matrix to list
     *
     * @param data matrix
     * @return list
     */
    public List<Integer> matrixIntegerToList(Integer[][] data) {
        return Stream.of(data).flatMap(Stream::of).collect(Collectors.toList());
    }

}
