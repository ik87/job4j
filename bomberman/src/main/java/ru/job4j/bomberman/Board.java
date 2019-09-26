package ru.job4j.bomberman;

import ru.job4j.bomberman.Figures.Cell;
import ru.job4j.bomberman.Figures.Figure;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
    private final Lock board[][];
    private final Figure hero;
    private ExecutorService pool;

    public Board(int size, Figure hero, ExecutorService pool) {
        this.board = new Lock[size][size];

        //init all Locks
        for(int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++) {
                this.board[y][x] = new ReentrantLock();
            }
        }

        //lock position Hero on the board
        board[hero.position().getY()][hero.position().getX()].lock();

        this.hero = hero;
        //this.board[1][3].lock();
        this.pool = pool;
    }

    public void move(Cell source, Cell dest) {
     //   pool.submit(() -> {
            /**
             *  Get hero's way with random curve. See Example {@link Figure#way}
             */
            Cell[] way = hero.way(dest, true);

            for (int i = 1; i < way.length; i++) {
                Cell currentStep = way[i - 1];
                Cell nextStep = way[i];
                Lock currentLock = board[currentStep.getY()][currentStep.getX()];
                Lock nextLock = board[nextStep.getY()][nextStep.getX()];
                System.out.println(nextStep);
                try {
                    if (nextLock.tryLock(500, TimeUnit.MILLISECONDS)) {
                        hero.setPosition(nextStep);
                        currentLock.unlock();
                        Thread.sleep(1000);
                    } else {
                        boolean vertical = false;
                        //Check where collision happened on X or Y line
                        if (currentStep.getY() != nextStep.getY()) {
                            //If it was vertical collision then move hero on 1 step left or right
                            if (currentStep.getX() + 1 < board[0].length) {
                                hero.setPosition(new Cell(currentStep.getX() + 1, currentStep.getY()));
                            } else {
                                hero.setPosition(new Cell(currentStep.getX() - 1, currentStep.getY()));
                            }
                            vertical = true;
                        } else {
                            //If it was horizontal collision then move hero on 1 step up or down
                            if (currentStep.getY() + 1 < board.length) {
                                hero.setPosition(new Cell(currentStep.getX(), currentStep.getY() + 1));
                            } else {
                                hero.setPosition(new Cell(currentStep.getX(), currentStep.getY() - 1));
                            }
                        }
                        //recalculate way from current position
                        way = hero.way(dest, vertical);
                        i = 1;
                    }
                } catch (InterruptedException e) {
                    break;
                }
            }
     //  });
    }

}
