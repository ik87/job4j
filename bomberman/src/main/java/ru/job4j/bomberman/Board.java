package ru.job4j.bomberman;

import ru.job4j.bomberman.figures.Cell;
import ru.job4j.bomberman.figures.Hero;

import java.util.concurrent.ExecutorService;
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
    private final Lock[][] board;
    private final Hero hero;
    private ExecutorService pool;

    public Board(Lock[][] board, Hero hero, ExecutorService pool) {
        this.board = board;
        this.hero = hero;
        this.pool = pool;
    }

    public void move(Cell source, Cell dest) {
        pool.submit(()->hero.move(dest, board));
    }
}
