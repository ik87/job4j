public interface Figure {
    Cell getPosition();
    void setPosition(Cell cell);
    Cell[] calcWay(Cell source, Cell dest);
}
