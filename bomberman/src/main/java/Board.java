import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Board {
    private final Lock[][] board;
    //private final Figure hero = new Hero(new Cell(0, 0));

    public Board(int x, int y) {
        this.board = new ReentrantLock[x][y];
        init();
    }

    public boolean move(Cell source, Cell dest) {

        return true;
    }

    private void init() {
        for(var i : board) {
            for(var j : i) {
                j = new ReentrantLock();
            }
        }
    }
}
