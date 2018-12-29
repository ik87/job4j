package ru.job4j.chess.firuges;

public class Counter {
    private static Cell[] multiSteps(Cell source, Cell dest, int deltaX, int deltaY) {

        Cell[] steps = new Cell[0];

        int x = source.x;
        int y = source.y;
        int i = 0;
        Cell[] tmp = new Cell[7];

        while (x >= 0 && x <= 7 && y >= 0 && y <= 7) {
            if (dest.x == x && dest.y == y) {
                tmp[i++] = dest;
                steps = new Cell[i - 1];
                System.arraycopy(tmp, 1, steps, 0, i - 1 );
                break;
            }
            for(Cell cell : Cell.values()) {
                if (cell.x == x && cell.y == y) {
                    tmp[i++] = cell;
                }
            }
            System.out.printf("[%d %d] -> [%d %d] steps:[%d %d] \n",source.x, source.y, dest.x, dest.y, x, y);
            x = x + deltaX;
            y = y + deltaY;
        }



        return steps;
    }

    public static Cell[] kingStep(Cell source, Cell dest) {
        Cell[] steps = new Cell[0];
        if (source.y == dest.y - 1 && source.x == dest.x) {
            steps = new Cell[] { dest };
        } else if (source.y == dest.y + 1 && source.x == dest.x) {
            steps = new Cell[]{dest};
        } else if (source.y == dest.y && source.x == dest.x - 1) {
            steps = new Cell[]{dest};
        } else if (source.y == dest.y && source.x == dest.x + 1) {
            steps = new Cell[]{dest};
        }else if (source.y == dest.y + 1 && source.x == dest.x - 1) {
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

    public static Cell[] qeenSteps(Cell source, Cell dest) {
        int deltaX = source.x == dest.x ? 0 : source.x > dest.x ? -1 : 1;
        int deltaY = source.y == dest.y ? 0 : source.y > dest.y ? -1 : 1;
        return Counter.multiSteps(source, dest, deltaX, deltaY);
    }

    public static Cell[] bishopSteps(Cell source, Cell dest) {
        int deltaX = source.x > dest.x ? -1 : 1;
        int deltaY = source.y > dest.y ? -1 : 1;
        return Counter.multiSteps(source, dest, deltaX, deltaY);
    }

    public static Cell[] rookSteps(Cell source, Cell dest) {
        Cell[] steps = new Cell[0];
        int deltaX = 0;
        int deltaY = 0;
        boolean start = false;
        if (source.x < dest.x && source.y == dest.y) {
            deltaX = 1;
            deltaY = 0;
            start = true;
        } else if (source.x > dest.x && source.y == dest.y) {
            deltaX = -1;
            deltaY = 0;
            start = true;
        } else if (source.x == dest.x && source.y < dest.y) {
            deltaX = 0;
            deltaY = 1;
            start = true;
        } else if (source.x == dest.x && source.y > dest.y) {
            deltaX = 0;
            deltaY = -1;
            start = true;
        }
        if (start) {
            steps = Counter.multiSteps(source, dest, deltaX, deltaY);
        }
        return steps;
    }

    public static Cell[] knightSteps(Cell source, Cell dest) {
        Cell[] steps = new Cell[0];
        if (source.x == dest.x + 1) {
            if( source.y == dest.y + 2) {
                steps = new Cell[]{dest};
            } else if ( source.y == dest.y - 2) {
                steps = new Cell[]{dest};
            }
        } else if (source.x == dest.x - 1 ) {
            if( source.y == dest.y + 2) {
                steps = new Cell[]{dest};
            } else if ( source.y == dest.y - 2) {
                steps = new Cell[]{dest};
            }
        } else if (source.x == dest.x + 2) {
            if( source.y == dest.y + 1) {
                steps = new Cell[]{dest};
            } else if ( source.y == dest.y - 1) {
                steps = new Cell[]{dest};
            }
        } else if (source.x == dest.x - 2) {
            if( source.y == dest.y + 1) {
                steps = new Cell[]{dest};
            } else if ( source.y == dest.y - 1) {
                steps = new Cell[]{dest};
            }
        }
        return  steps;
    }
}
