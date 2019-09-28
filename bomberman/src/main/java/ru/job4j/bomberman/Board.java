package ru.job4j.bomberman;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.bomberman.figures.Cell;
import ru.job4j.bomberman.figures.Figure;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * Some game mechanic where Hero is making calculate steps,
 * if on the way he collide with block then he go on 1 step bellow, higher or to the left,
 * to the right from his current position then calculate way to destination again.
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Board {
    private final Lock[][] board;
    private final Figure hero;
    private ExecutorService pool;
    private static final Logger LOG = LogManager.getLogger(Board.class.getName());

    public Board(Lock[][] board, Figure hero, ExecutorService pool) {
        this.board = board;
        this.hero = hero;
        this.pool = pool;
    }

    public void move(Cell source, Cell dest) {
        pool.submit(() -> {
            /**
             *  Get hero's way with random curve. See Example {@link Figure#way}
             */
            Random random = new Random();
            Cell[] way = hero.way(hero.position(), dest, random.nextBoolean());
            LOG.debug("Generate way {}", List.of(way));
            board[hero.position().getY()][hero.position().getX()].lock();
            for (int i = 1; i < way.length; i++) {
                Cell currentStep = way[i - 1];
                Cell nextStep = way[i];
                try {
                    if (lock(nextStep).tryLock(500, TimeUnit.MILLISECONDS)) {
                        LOG.debug(currentStep);
                        hero.setPosition(nextStep);
                        lock(currentStep).unlock();
                        Thread.sleep(1000);
                    } else {
                        boolean vertical = false;
                        Cell newSource;

                        //Check where collision happened on X or Y line
                        if (currentStep.getY() != nextStep.getY()) {
                            //If it was vertical collision then move hero on 1 step left or right
                            if (currentStep.getX() + 1 < board[0].length && currentStep.getX() - 1 >= 0) {
                                newSource = random.nextBoolean()
                                        ? new Cell(currentStep.getX() + 1, currentStep.getY())
                                        : new Cell(currentStep.getX() - 1, currentStep.getY());
                            } else if (currentStep.getX() + 1 < board[0].length) {
                                newSource = new Cell(currentStep.getX() + 1, currentStep.getY());
                            } else {
                                newSource = new Cell(currentStep.getX() - 1, currentStep.getY());
                            }
                            vertical = true;
                        } else {
                            if (currentStep.getY() + 1 < board.length && currentStep.getY() - 1 >= 0) {
                                newSource = random.nextBoolean()
                                        ? new Cell(currentStep.getX(), currentStep.getY() + 1)
                                        : new Cell(currentStep.getX(), currentStep.getY() - 1);
                                //If it was horizontal collision then move hero on 1 step up or down
                            } else if (currentStep.getY() + 1 < board.length) {
                                newSource = new Cell(currentStep.getX(), currentStep.getY() + 1);
                            } else {
                                newSource = new Cell(currentStep.getX(), currentStep.getY() - 1);
                            }

                        }
                        //recalculate way from current position
                        way = hero.way(newSource, dest, vertical);
                        way = addAtBegin(currentStep, way);
                        i = 0;

                        //System.out.println(List.of(way));
                        LOG.debug("Generate new way: {}", List.of(way));
                    }
                } catch (InterruptedException e) {
                    LOG.error(e.getMessage());
                }

            }
           LOG.debug(way[way.length - 1]);
        });
    }

    private Lock lock(Cell cell) {
        return board[cell.getY()][cell.getX()];
    }

    private Cell[] addAtBegin(Cell cell, Cell[] tmp) {
        Cell[] way = new Cell[tmp.length + 1];
        way[0] = cell;
        System.arraycopy(tmp, 0, way, 1, tmp.length);
        return way;
    }

}
