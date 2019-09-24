import Figures.Cell;
import Figures.Figure;
import Figures.Hero;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Logic {
    private final Lock board[][];
    private final Figure hero;
    private ExecutorService service = Executors.newFixedThreadPool(1);

    public Logic(int size, Figure hero) {
        this.board = new Lock[size][size];
        //init all Locks
        for (var cell : board) {
            for (var row : cell) {
                row = new ReentrantLock();
            }
        }
        //lock position Hero on board
        board[hero.position().getY()][hero.position().getX()].lock();
        this.hero = hero;
    }

    public boolean move(Cell source, Cell dest) {
        boolean rst = false;

        /*
        int index = this.findBy(source);
        if (index != -1) {
            Cell[] steps = this.figures[index].way(source, dest);
            if (this.isFree(steps)) {
                rst = true;
                this.figures[index] = this.figures[index].copy(dest);
            }
        }
        return rst;

         */
        return rst;
    }

}
