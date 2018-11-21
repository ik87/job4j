package ru.job4j.loop;
import java.util.function.BiPredicate;
/**
 * @author Kosolapov Ilya (d_dexter@mail.ru).
 * @version $ID$
 * @since 0.1
 */
public class Paint {
    /**
     * Build piramid.
     * @param height height piramid.
     * @return piramid.
     */
    public String piramid(int height) {
        return this.loopBy(
                height,
                height * 2 - 1,
                (row, column) -> (row >= height - column - 1) && (height + row >= column + 1)
        );
    }

    /**
     * Build right side of piramide.
     * @param height height piramid.
     * @return right side.
     */
    public String rightTrl(int height) {
        return this.loopBy(
                height,
                height,
                (row, column) -> row >= column
        );
    }

    /**
     * Build left side of piramide.
     * @param height  height piramid.
     * @return left side.
     */
    public String leftTrl(int height) {
        return this.loopBy(
                height,
                height,
                (row, column) -> row >= height - column - 1
        );
    }

    /**
     *  For refactoring leftTrl, rightTrl, piramid
     * @param height height
     * @param weight weigth
     * @param predict predict
     * @return loop
     */
    private String loopBy(int height, int weight, BiPredicate<Integer, Integer> predict) {
        StringBuilder screen = new StringBuilder();
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < weight; column++) {
                if (predict.test(row, column)) {
                    screen.append("^");
                } else {
                    screen.append(" ");
                }
            }
            screen.append(System.lineSeparator());
        }
        return screen.toString();
    }
}