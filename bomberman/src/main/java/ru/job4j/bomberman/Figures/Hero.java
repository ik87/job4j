package ru.job4j.bomberman.Figures;

import java.util.ArrayList;
import java.util.List;

/**
 *@author Kosolapov Ilya (d_dexter@mail.ru)
 *@version $Id$
 *@since 0.1
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
    public Cell[] way(Cell dest, boolean vertical) {
        List<Cell> steps = new ArrayList<>();
        //set motion vector
        int vecX = position.x < dest.x ? 1 : -1;
        int vecY = position.y < dest.y ? 1 : -1;

        if(vertical) {
            yWay(steps, dest, vecY);
            xWay(steps, dest, vecX);
        } else {
            xWay(steps, dest, vecX);
            yWay(steps, dest, vecY);
        }

        return  steps.toArray(Cell[]::new);
    }

    private void xWay(List<Cell> steps,Cell dest, int vecX) {
        for (int x = position.x; x <= dest.x; x += vecX) {
            steps.add(new Cell(x, position.y));
        }
    }

    private void yWay(List<Cell> steps,Cell dest, int vecY) {
        for (int y = position.y; y <= dest.y;  y += vecY) {
            steps.add(new Cell(position.x, y));
        }
    }

    @Override
    public void setPosition(Cell cell) {
        position.y = cell.y;
        position.x = cell.x;
    }
}
