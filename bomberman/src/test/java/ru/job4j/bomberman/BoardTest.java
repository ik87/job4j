package ru.job4j.bomberman;

import org.junit.Test;
import ru.job4j.bomberman.figures.Cell;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class BoardTest {


    /**
     * Fill array zeros
     *
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
        final ReentrantLock[][] lockBoard = new ReentrantLock[4][4];

        init2DArrayLock(lockBoard);

        //blocks
        lockBoard[0][2].lock();
        lockBoard[2][1].lock();

        Cell source = new Cell(3, 3);
        Cell dest = new Cell(1, 0);

        Board board = new Board(lockBoard);
        HeroThread hero = new HeroThread(board, source, dest);
        hero.start();
        hero.join();

        assertThat(dest, is(hero.getPosition()));
    }

    @Test
    public void whenMonstersRun() throws InterruptedException {

        final ReentrantLock[][] lockBoard = new ReentrantLock[4][4];

        init2DArrayLock(lockBoard);

        Board board = new Board(lockBoard);
        Cell monster1 = new Cell(1, 3);
        Cell monster2 = new Cell(3, 0);

        MonsterThread monsterThread1 = new MonsterThread(board, monster1);
        MonsterThread monsterThread2 = new MonsterThread(board, monster2);

        monsterThread1.start();
        monsterThread2.start();

        Thread.sleep(3000);

        monsterThread1.interrupt();
        monsterThread2.interrupt();
    }
}