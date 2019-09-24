package Figures;

import java.util.ArrayList;
import java.util.List;

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
        List<Cell> steps = new ArrayList<>();

        int vecX = position.x < dest.x ? 1 : -1;
        int vecY = position.y < dest.y ? 1 : -1;

        for (int y = position.y; y <= dest.y;  y += vecY) {
            steps.add(new Cell(position.x, y));
        }

        for (int x = position.x; x <= dest.x; x += vecX) {
            steps.add(new Cell(x, dest.y));
        }

        return (Cell[]) steps.toArray();
    }

}
