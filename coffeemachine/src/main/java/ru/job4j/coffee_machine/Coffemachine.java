package ru.job4j.coffee_machine;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version 1.0
 * @since 19.02.2019
 */
public class Coffemachine {

    /**
     * This method give coins  array in descending order.
     * Trying provide the greatest coins value
     * @param value value
     * @param price price
     * @return exchange
     */
    public int[] changes(int value, int price) {
        int[] changes = new int[0];
        int tail = value - price;
        int i = 0;
        int coin;
        while (tail != 0) {
            i++;
            if (tail >= 10) {
                coin = 10;
            } else if (tail >= 5) {
                coin = 5;
            } else if (tail >= 2) {
                coin = 2;
            } else if (tail >= 1) {
                coin = 1;
            } else {
                break;
            }
            tail -= coin;
            int[] tmp = changes;
            changes = new int[i];
            System.arraycopy(tmp, 0, changes, 0, tmp.length);
            changes[i - 1] = coin;
        }
        return changes;
    }
}
