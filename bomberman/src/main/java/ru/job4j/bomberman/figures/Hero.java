package ru.job4j.bomberman.figures;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.bomberman.Board;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.function.Consumer;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Hero {

    private static final Logger LOG = LogManager.getLogger(Board.class.getName());

    private final Cell position;

    public Cell getPosition() {
        return position;
    }

    public Hero(final Cell position) {
        this.position = position;
    }


    /**
     * Main method that make calculating hero movement
     *
     * @param dest
     * @param board
     */
    public void move(Cell dest, Lock[][] board) {

        //Fast and thread safe random (instead classical Random)
        SplittableRandom random = new SplittableRandom();
        /**
         * Generate wayGenerator
         */
        Cell[] way = wayGenerator(position, dest, random.nextBoolean());

        LOG.debug("Generate way {}", List.of(way));

        //set lock on current position
        board[position.y][position.x].lock();

        for (int i = 1; i < way.length; i++) {
            Cell currentStep = way[i - 1];
            Cell nextStep = way[i];
            try {
                if (lock(board, nextStep).tryLock(500, TimeUnit.MILLISECONDS)) {
                    LOG.debug(currentStep);
                    position.x = nextStep.x;
                    position.y = nextStep.y;
                    lock(board, currentStep).unlock();
                    Thread.sleep(1000);
                } else {
                    boolean vertical = false;
                    Cell newSource;
                    //Check where collision happened on X or Y line
                    if (currentStep.getY() != nextStep.getY()) {
                        //If it was vertical collision then move hero on 1 step left or right
                        newSource = goLeftOrRight(currentStep, board.length, random.nextBoolean());
                        vertical = true;
                    } else {
                        newSource = goUpOrDown(currentStep, board[0].length, random.nextBoolean());
                    }
                    //recalculate way from current position
                    way = wayGenerator(newSource, dest, vertical);

                    //Concat current position with way
                    way = concat(currentStep, way);

                    i = 0;
                    LOG.debug("Generate new way: {}", List.of(way));
                }
            } catch (InterruptedException e) {
                LOG.error(e.getMessage());
            }

        }
        LOG.debug(way[way.length - 1]);

    }

    /**
     * Go at one step UP or Down from current position
     *
     * @param currentStep current position of Hero
     * @param length matrix's width
     * @param direction where is he go? If true then UP if false then DOWN
     * @return new position
     */
    private Cell goUpOrDown(Cell currentStep, int length, boolean direction) {
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
    private Cell goLeftOrRight(Cell currentStep, int length, boolean direction) {
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
     * Helper method. Get lock object
     *
     * @param board
     * @param cell
     * @return
     */
    private Lock lock(Lock[][] board, Cell cell) {
        return board[cell.getY()][cell.getX()];
    }

    /**
     * Add current step at begin array
     *
     * @param cell
     * @param tmp
     * @return
     */
    private Cell[] concat(Cell cell, Cell[] tmp) {
        Cell[] way = new Cell[tmp.length + 1];
        way[0] = cell;
        System.arraycopy(tmp, 0, way, 1, tmp.length);
        return way;
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
    private Cell[] wayGenerator(Cell source, Cell dest, boolean vertical) {
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
