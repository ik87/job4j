package ru.job4j.bomberman;

import org.junit.Test;
import ru.job4j.bomberman.figures.Cell;
import ru.job4j.bomberman.figures.Hero;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class BoardTest {


    /**
     * Fill array zeros
     * @param lockBoard
     */
    private void init2DArrayLock(Lock[][] lockBoard) {
        //init all Locks
        for (int y = 0; y < lockBoard.length; y++) {
            for (int x = 0; x < lockBoard[0].length; x++) {
                lockBoard[y][x] = new ReentrantLock();
            }
        }
    }


    @Test
    public void whenHeroBypassBlocks() throws InterruptedException {
        /**
         *  S - start
         *  F - finish
         *  * - steps
         *  b - blocks
         *
         * [. F b *]
         * [. * . *]
         * [. b . *]
         * [. * * S]
         */
        final Lock[][] lockBoard = new Lock[4][4];

        init2DArrayLock(lockBoard);

        //blocks
        lockBoard[0][2].lock();
        lockBoard[2][1].lock();

        Cell source = new Cell(3, 3);
        Cell dest = new Cell(1, 0);

        Hero hero = new Hero(source);
        Board board = new Board(lockBoard, hero);

        Thread moveHero = new Thread(() -> board.move(null, dest));

        moveHero.start();
        moveHero.join();

        assertThat(dest.getX(), is(hero.getPosition().getX()));
        assertThat(dest.getY(), is(hero.getPosition().getY()));
    }

}