package ru.job4j.bomberman;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.bomberman.figures.Cell;
import ru.job4j.bomberman.figures.Hero;

import java.util.List;
import java.util.SplittableRandom;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

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
    private final Hero hero;
    private ExecutorService pool;


    public Board(Lock[][] board, Hero hero, ExecutorService pool) {
        this.board = board;
        this.hero = hero;
        this.pool = pool;
    }

    public void move(Cell source, Cell dest) {
        pool.submit(() -> {
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
                        //LOG.debug(currentStep);
                        hero.setPosition(nextStep.getX(), nextStep.getY());
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
                            newSource = hero.goLeftOrRight(currentStep, board.length, random.nextBoolean());
                            vertical = true;
                        } else {
                            newSource = hero.goUpOrDown(currentStep, board[0].length, random.nextBoolean());
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
        });
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
}
