package ru.job4j.bomberman;

import ru.job4j.bomberman.figures.Cell;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This class responsible for steps on the board
 */
public class Board {
    private final ReentrantLock[][] board;

    public Board(ReentrantLock[][] board) {
        this.board = board;
    }

    public boolean move(Cell source, Cell dest) throws InterruptedException {
        boolean result = true;
        try {
            if (lock(board, dest).tryLock(500, TimeUnit.MILLISECONDS)) {
                Thread.sleep(1000);
            } else {
                result = false;
            }
        } catch (InterruptedException e) {
            throw e;
        } finally {
            //if it was first step, check on the lock and release it;
            if (lock(board, source).isLocked()) {
                lock(board, source).unlock();
            }
        }
        return result;
    }

    public int size() {
        return board.length;
    }

    /**
     * Helper method. Get lock object
     */
    private ReentrantLock lock(ReentrantLock[][] board, Cell cell) {
        return board[cell.getY()][cell.getX()];
    }

}
