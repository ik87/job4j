package ru.job4j.chess.firuges;


/**
 * Wrap figure identity type and use specify method for calc steps
 *
 * @author Kosolapob Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class FigureDecorate implements Figure {
    Figure figure;

    public FigureDecorate(Figure figure) {
        this.figure = figure;
    }

    private int deltaX;
    private int deltaY;

    /**
     * General counter steps for "King", "Bishop", "Rook", "Queen"
     *
     * @param source source
     * @param dest   dest
     * @return steps
     */
    private Cell[] multiSteps(Cell source, Cell dest) {

        Cell[] steps = new Cell[0];
        Cell[] tmp = new Cell[8];

        int j = 0;
        boolean correct = false;
        for (int i = source.ordinal(); i >= 0 && i < Cell.values().length && j < tmp.length; j++) {
            tmp[j] = Cell.values()[i];
            //System.out.printf("[%d %d] -> [%d %d] steps:[%d %d] \n", source.x, source.y, dest.x, dest.y, Cell.values()[i].x, Cell.values()[i].y);
            i = i + deltaY + (deltaX * 8);
            if (tmp[j].equals(dest)) {
                correct = true;
                break;
            }

        }
        if (correct) {
            steps = new Cell[j];
            System.arraycopy(tmp, 1, steps, 0, j);
        }
        return steps;
    }

    private Cell[] kingStep(Cell source, Cell dest) {
        Cell[] steps = new Cell[0];
        if (source.y == dest.y - 1 && source.x == dest.x) {
            steps = new Cell[]{dest};
        } else if (source.y == dest.y + 1 && source.x == dest.x) {
            steps = new Cell[]{dest};
        } else if (source.y == dest.y && source.x == dest.x - 1) {
            steps = new Cell[]{dest};
        } else if (source.y == dest.y && source.x == dest.x + 1) {
            steps = new Cell[]{dest};
        } else if (source.y == dest.y + 1 && source.x == dest.x - 1) {
            steps = new Cell[]{dest};
        } else if (source.y == dest.y - 1 && source.x == dest.x + 1) {
            steps = new Cell[]{dest};
        } else if (source.y == dest.y + 1 && source.x == dest.x + 1) {
            steps = new Cell[]{dest};
        } else if (source.y == dest.y - 1 && source.x == dest.x - 1) {
            steps = new Cell[]{dest};
        }
        return steps;
    }

    private Cell[] qeenSteps(Cell source, Cell dest) {
        deltaX = Integer.compare(dest.x, source.x);
        deltaY = Integer.compare(dest.y, source.y);
        return multiSteps(source, dest);
    }

    private Cell[] bishopSteps(Cell source, Cell dest) {
        deltaX = source.x < dest.x ? 1 : -1;
        deltaY = source.y < dest.y ? 1 : -1;
        return multiSteps(source, dest);
    }

    private Cell[] rookSteps(Cell source, Cell dest) {
        deltaX = 0;
        deltaY = 0;
        Cell[] steps = new Cell[0];
        boolean start = false;
        if (source.x < dest.x && source.y == dest.y) {
            deltaX = 1;
            start = true;
        } else if (source.x > dest.x && source.y == dest.y) {
            deltaX = -1;
            start = true;
        } else if (source.x == dest.x && source.y < dest.y) {
            deltaY = 1;
            start = true;
        } else if (source.x == dest.x && source.y > dest.y) {
            deltaY = -1;
            start = true;
        }
        if (start) {
            steps = multiSteps(source, dest);
        }
        return steps;
    }

    private Cell[] knightSteps(Cell source, Cell dest) {
        Cell[] steps = new Cell[0];
        if ((source.x == dest.x + 1) || (source.x == dest.x - 1)) {
            if ((source.y == dest.y + 2) || (source.y == dest.y - 2)) {
                steps = new Cell[]{dest};
            }
        } else if ((source.x == dest.x + 2) || (source.x == dest.x - 2)) {
            if ((source.y == dest.y + 1) || (source.y == dest.y - 1)) {
                steps = new Cell[]{dest};
            }
        }
        return steps;
    }

    @Override
    public Cell position() {
        return figure.position();
    }

    @Override
    public Cell[] way(Cell source, Cell dest) {
        Cell[] steps = new Cell[0];
        String name = figure.getClass().getSimpleName();
        if (name.contains("Bishop")) {
            steps = bishopSteps(source, dest);
        } else if (name.contains("King")) {
            steps = kingStep(source, dest);
        } else if (name.contains("Knight")) {
            steps = knightSteps(source, dest);
        } else if (name.contains("Qeen")) {
            steps = qeenSteps(source, dest);
        } else if (name.contains("Rook")) {
            steps = rookSteps(source, dest);
        } else if (name.equals("PawnBlack")) {
            if (source.y == dest.y + 1 && source.x == dest.x) {
                steps = new Cell[]{dest};
            }
        } else if (name.equals("PawnWhite")) {
            if (source.y == dest.y - 1 && source.x == dest.x) {
                steps = new Cell[]{dest};
            }
        }
        //System.out.println(Arrays.toString(steps) + " " + name);
        return steps;
    }

    @Override
    public Figure copy(Cell dest) {
        return new FigureDecorate(figure.copy(dest));
    }
}
