import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class Hero implements Figure {
    private final Cell position;
    private final Lock board[][];
    private Degree degree = Degree.DOWN;
    // private Cell degree = new Cell(0, 1); // 0,1 ↓ | -1,0 ← | 0,-1 ↑ | 1,0 →

    public Hero(Cell position, Lock board[][]) {
        this.position = position;
        board[position.y][position.x].lock();
        this.board = board;

    }

    @Override
    public void leftTurn() {
        if (degree.ordinal()  < Degree.values().length - 1) {
            degree = Degree.values()[degree.ordinal() + 1];
        } else {
            degree = Degree.values()[0];
        }
    }

    @Override
    public void rightTurn() {
        if (0 < degree.ordinal()) {
            degree = Degree.values()[degree.ordinal() - 1];
        } else {
            degree = Degree.values()[3];
        }
    }

    @Override
    public void mirrorTurn() {
        if(degree == Degree.DOWN) {
            degree = Degree.UP;
        } else if (degree == Degree.LEFT) {
            degree = Degree.RIGHT;
        } else if (degree == Degree.UP) {
            degree = Degree.DOWN;
        } else if (degree == Degree.RIGHT) {
            degree = Degree.LEFT;
        }
    }

    @Override
    public Condition move() throws InterruptedException {
        Condition condition = Condition.OK;
        Cell oldPosition = this.position;
        this.position.x += degree.cell.x;
        this.position.y += degree.cell.y;
        //check that we in the range of the board
        if( board.length > position.y && 0 <= position.y &&
            board[0].length > position.x && 0 <= position.x) {
            if (board[position.y][position.x].tryLock(500, TimeUnit.MICROSECONDS)) {
                board[oldPosition.y][oldPosition.x].unlock();
                Thread.sleep(1000);
            } else {
                position.y = oldPosition.y;
                position.x = oldPosition.x;
               condition = Condition.OBJECT;
            }
        } else {
            condition = Condition.WALL;
        }
        return condition;
    }

    public Cell[] getDir(Cell dest) {
        List<Cell> cells = new ArrayList<>();
        int x = position.x - dest.x; //1 - 4 = -3
        int y = position.y - dest.y; //1 - 4 = -3
        //выяснить направление движения
        return (Cell[]) cells.toArray();
    }

    private enum Degree {
        DOWN(0, 1), //0
        LEFT(-1, 0), //1
        UP(0, -1),  //2
        RIGHT(1, 0); //3

        Cell cell;
        Degree(int x, int y) {
            this.cell = new Cell(x, y);
        }
    }
}
