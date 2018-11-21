package ru.job4j.array;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class FindLoop {
    /**
     * Get index found element.
     * @param data array.
     * @param el element.
     * @return index.
     */
    public int indexOf(int[] data, int el) {
        int rst = -1;
        for (int index = 0; index < data.length; index++) {
            if (el == data[index]) {
                rst = index;
                break;
            }
        }
        return rst;
    }
}
