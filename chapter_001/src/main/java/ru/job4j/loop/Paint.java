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
        StringBuilder screen = new StringBuilder();
        int weight = 2 * height - 1;
        for (int row = 0; row != height; row++) {
            for (int column = 0; column != weight; column++) {
                if (row >= height - column - 1 && row >= column - height + 1) {
                    screen.append("^");
                } else {
                    screen.append(" ");
                }

            }
            screen.append(System.lineSeparator());
        }
        return screen.toString();
    }

    /**
     * Build right side of piramide.
     * @param height height piramid.
     * @return right side.
     */
    public String rightTrl(int height) {
        StringBuilder screen = new StringBuilder();
        for (int row = 0; row != height; row++) {
            for (int column = 0; column != height; column++) {
                if (row >= column) {
                    screen.append("^");
                } else {
                    screen.append(" ");
                }
            }
            screen.append(System.lineSeparator());
        }
        return screen.toString();
    }

    /**
     * Build left side of piramide.
     * @param height  height piramid.
     * @return left side.
     */
    public String leftTrl(int height) {
        StringBuilder screen = new StringBuilder();
        for (int row = 0; row != height; row++) {
            for (int column = 0; column != height; column++) {
                if (row >= height - column - 1) {
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