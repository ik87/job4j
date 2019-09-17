import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Board {
    private final Lock[][] board;
    private final Hero hero;
    //private final Monsters[];

    public Board(int x, int y, Hero hero) {
        this.board = new ReentrantLock[x][y];
        this.hero = hero;
        init();
    }

    public boolean move(Cell source, Cell dist) {
        int vecX = source.x > dist.x ? -1 : 1;
        int vecY = source.y > dist.y ? -1 : 1;
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
