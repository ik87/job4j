package ru.job4j.bomberman.figures;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Hero {


    private final Cell position;

    public Cell getPosition() {
        return position;
    }

    public void setPosition(int x, int y) {
        this.position.x = x;
        this.position.y = y;
    }

    public Hero(final Cell position) {
        this.position = position;
    }


    /**
     * Go at one step UP or Down from current position
     *
     * @param currentStep current position of Hero
     * @param length matrix's width
     * @param direction where is he go? If true then UP if false then DOWN
     * @return new position
     */
    public Cell goUpOrDown(Cell currentStep, int length, boolean direction) {
        Cell newSource;
        if (currentStep.getY() + 1 < length && currentStep.getY() - 1 >= 0) {
            newSource = direction
                    ? new Cell(currentStep.getX(), currentStep.getY() + 1)
                    : new Cell(currentStep.getX(), currentStep.getY() - 1);
            //If it was horizontal collision then move hero on 1 step up or down
        } else if (currentStep.getY() + 1 < length) {
            newSource = new Cell(currentStep.getX(), currentStep.getY() + 1);
        } else {
            newSource = new Cell(currentStep.getX(), currentStep.getY() - 1);
        }
        return newSource;
    }

    /**
     * Go at one step Left or Right from current position
     *
     * @param currentStep current position of Hero
     * @param length matrix's height
     * @param direction where is he go? If true then LEFT if false then RIGHT
     * @return new position
     */
    public Cell goLeftOrRight(Cell currentStep, int length, boolean direction) {
        Cell newSource;
        if (currentStep.getX() + 1 < length && currentStep.getX() - 1 >= 0) {
            newSource = direction
                    ? new Cell(currentStep.getX() + 1, currentStep.getY())
                    : new Cell(currentStep.getX() - 1, currentStep.getY());
        } else if (currentStep.getX() + 1 < length) {
            newSource = new Cell(currentStep.getX() + 1, currentStep.getY());
        } else {
            newSource = new Cell(currentStep.getX() - 1, currentStep.getY());
        }
        return newSource;
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

}
