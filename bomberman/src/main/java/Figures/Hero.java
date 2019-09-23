package Figures;

public class Hero implements Figure {
    private final Cell position;

    public Hero(final Cell position) {
        this.position = position;
    }

    @Override
    public Cell position() {
        return this.position;
    }

    @Override
    public Cell[] way(Cell source, Cell dest) {
        Cell[] steps = new Cell[0];
        if ((Math.abs(source.x - dest.x) + Math.abs(source.y - dest.y)) == 1) {
            steps = new Cell[]{dest};
        }
        return steps;
    }

    @Override
    public Figure copy(Cell dest) {
        return new Hero(dest);
    }
}
