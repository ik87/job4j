package ru.job4j.tictactoe;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Kosolapov Iliya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class Logic3T {

    private final Figure3T[][] table;
    /**
     * all win states
     */
    private final int[][] win = {
            {0, 4, 8}, {2, 4, 6}, {0, 3, 6}, {1, 4, 7},
            {2, 5, 8}, {0, 1, 2}, {3, 4, 5}, {6, 7, 8}
    };

    /**
     * Calculate win state. Compare all win state with current state on the table
     *
     * @param predicate checker Figure3T which has mark
     * @return result
     */
    private boolean calculate(Predicate<Figure3T> predicate) {

        boolean flag = false;
        List<Figure3T> board = Arrays.stream(table).flatMap(Arrays::stream).
                collect(Collectors.toList());
        for (var condition : win) {
            int j = 0;
            while (j < condition.length) {
                if (!predicate.test(board.get(condition[j]))) {
                    break;
                }
                j++;
            }
            if (j == 3) {
                flag = true;
                break;
            }
        }


        return flag;
    }

    public Logic3T(Figure3T[][] table) {
        this.table = table;
    }

    public boolean isWinnerX() {
        return calculate(Figure3T::hasMarkX);
    }


    public boolean isWinnerO() {
        return calculate(Figure3T::hasMarkO);
    }

    public boolean hasGap() {
        List<Figure3T> board = Arrays.stream(table).flatMap(Arrays::stream).
                collect(Collectors.toList());

        return !board.stream().allMatch(x -> x.hasMarkO() || x.hasMarkX());

    }
}