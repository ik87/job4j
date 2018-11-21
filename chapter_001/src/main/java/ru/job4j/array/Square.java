package ru.job4j.array;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class Square {
    /**
     * Create squaring array for each element from 1 to "bound"
     * @param bound size array
     * @return squaring array
     */
    public int[] calculate(int bound) {
        int[] rst = new int[bound];
        for (int i = 0; i < bound; i++) {
            rst[i] = (int) Math.pow((i + 1), 2);
        }
        return rst;
    }
}