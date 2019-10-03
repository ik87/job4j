package ru.job4j.bomberman.figures;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Hero implements Figure {


    private final Cell position;

    public Cell getPosition() {
        return position;
    }

    public void setPosition(Cell position) {
        this.position.x = position.x;
        this.position.y = position.y;
    }

    public Hero(final Cell position) {
        this.position = position;
    }


    /**
     * Calculate way from source to dest position.
     * Example:
     *          vertical == true                        vertical == false
     *
     *                [start]                               [finish]
     *                   *                                      *
     *                   *                                      *
     *                   *                      [start] * * * * *
     *      [finish] * * * * *[finish]                          *
     *                                                          *
     *                                                      [finish]
     * @param dest destination position
     * @param vertical see example above
     *
     * @return array calculated way
     */
    @Override
    public Cell[] way(Cell source, Cell dest, boolean vertical) {
        List<Cell> steps = new ArrayList<>();
        //set motion vector
        int vecX = source.x <= dest.x ? 1 : -1;
        int vecY = source.y <= dest.y ? 1 : -1;

        if (vertical) {
            //vertical
            wayFill(source.y, dest.y, vecY, (y) -> steps.add(new Cell(source.x, y)));
            int posY = steps.remove(steps.size() - 1).y;
            //horizontal
            wayFill(source.x, dest.x, vecX, (x) -> steps.add(new Cell(x, posY)));
        } else {
            //horizontal
            wayFill(source.x, dest.x, vecX, (x) -> steps.add(new Cell(x, source.y)));
            int posX = steps.remove(steps.size() - 1).x;
            //vertical
            wayFill(source.y, dest.y, vecY, (y) -> steps.add(new Cell(posX, y)));
        }

        return steps.toArray(Cell[]::new);
    }

    /**
     * Iterator for fill way
     */
    private void wayFill(int source, int dest, int vec, Consumer<Integer> consumer) {
        for (; source != dest; source += vec) {
            consumer.accept(source);
        }
        consumer.accept(dest);
    }

    @Override
    public String toString() {
        return "Hero";
    }


}
