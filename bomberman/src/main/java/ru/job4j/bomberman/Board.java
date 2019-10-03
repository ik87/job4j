package ru.job4j.bomberman;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.bomberman.figures.Cell;
import ru.job4j.bomberman.figures.Figure;

import java.util.List;
import java.util.SplittableRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Some game mechanic where Hero is making calculate steps,
 * if on the way he collide with block then he go on 1 step bellow, higher or to the left,
 * to the right from his current position then recalculate way to destination again.
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Board {
    private static final Logger LOG = LogManager.getLogger(Board.class.getName());

    private final Lock[][] board;
    private final Figure hero;


    public Board(Lock[][] board, Figure hero) {
        this.board = board;
        this.hero = hero;
    }

    public void move(Cell source, Cell dest) {
        SplittableRandom random = new SplittableRandom();
        //Generate way
        Cell[] way = hero.way(hero.getPosition(), dest, random.nextBoolean());
        //set lock on current position
        lock(board, hero.getPosition()).lock();

        LOG.debug("Generate way {}", List.of(way));

        for (int i = 1; i < way.length; i++) {
            Cell currentStep = way[i - 1];
            Cell nextStep = way[i];
            try {
                if (lock(board, nextStep).tryLock(500, TimeUnit.MILLISECONDS)) {
                    hero.setPosition(nextStep);
                    LOG.debug(hero.getPosition());
                    lock(board, currentStep).unlock();
                    Thread.sleep(1000);
                } else {
                    //Check where collision happened on X or Y line
                    //If it was vertical collision then move hero on 1 step left or right
                    //If it was horizontal collision then move hero on 1 step Up or Down

                    boolean vertical = false;
                    Cell newSource;
                    if (currentStep.getY() != nextStep.getY()) {
                        newSource = goLeftOrRight(currentStep, board.length, random.nextBoolean());
                        vertical = true;
                    } else {
                        newSource = goUpOrDown(currentStep, board[0].length, random.nextBoolean());
                    }
                    //recalculate way from current position
                    way = hero.way(newSource, dest, vertical);

                    //concat current position with way
                    way = concat(currentStep, way);
                    i = 0;
                    LOG.debug("Generate new way: {}", List.of(way));
                }
            } catch (InterruptedException e) {
                LOG.error(e.getMessage());
            }
        }
    }

    /**
     * Helper method. Get lock object
     */
    private Lock lock(Lock[][] board, Cell cell) {
        return board[cell.getY()][cell.getX()];
    }


    /**
     * Add current step at begin array
     */
    private Cell[] concat(Cell cell, Cell[] tmp) {
        Cell[] way = new Cell[tmp.length + 1];
        way[0] = cell;
        System.arraycopy(tmp, 0, way, 1, tmp.length);
        return way;
    }


    /**
     * Go at one step UP or Down from current position
     *
     * @param currentStep current position of hero
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
     * @param currentStep current position of hero
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
}
