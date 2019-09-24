package Figures;

public interface Figure {

    Cell position();

    Cell[] way(Cell source, Cell dest);
}
