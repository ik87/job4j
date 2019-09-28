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

    public Hero(final Cell position) {
        this.position = position;
    }

    @Override
    public Cell position() {
        return this.position;
    }

    /**
     * See logic {@link Figure#way}
     **/
    @Override
    public Cell[] way(Cell source, Cell dest, boolean vertical) {
        List<Cell> steps = new ArrayList<>();
        //set motion vector
        int vecX = source.x <= dest.x ? 1 : -1;
        int vecY = source.y <= dest.y ? 1 : -1;

        if (vertical) {
            //vertical
            way(source.y, dest.y, vecY, (y) -> steps.add(new Cell(source.x, y)));
            int posY = steps.remove(steps.size() - 1).y;
            //horizontal
            way(source.x, dest.x, vecX, (x) -> steps.add(new Cell(x, posY)));
        } else {
            //horizontal
            way(source.x, dest.x, vecX, (x) -> steps.add(new Cell(x, source.y)));
            int posX = steps.remove(steps.size() - 1).x;
            //vertical
            way(source.y, dest.y, vecY, (y) -> steps.add(new Cell(posX, y)));
        }

        return steps.toArray(Cell[]::new);
    }

    /**
     * Calc steps
     */
    private void way(int source, int dest, int vec, Consumer<Integer> consumer) {
        for (; source != dest; source += vec) {
            consumer.accept(source);
        }
        consumer.accept(dest);
    }

    @Override
    public void setPosition(Cell cell) {
        position.y = cell.y;
        position.x = cell.x;
    }
}
