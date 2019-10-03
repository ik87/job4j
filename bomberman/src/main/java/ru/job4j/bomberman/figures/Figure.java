package ru.job4j.bomberman.figures;

public interface Figure {
    Cell getPosition();

    void setPosition(Cell position);

    Cell[] way(Cell source, Cell dest, boolean vertical);
}
