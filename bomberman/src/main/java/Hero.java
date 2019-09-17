public class Hero implements Figure {
    private final Cell position;
    public Hero(Cell cell) {
        this.position = cell;
    }

    @Override
    public Cell getPosition() {
        return position;
    }

    @Override
    public void setPosition(Cell cell) {
        this.position.x = cell.x;
        this.position.y = cell.y;
    }
}
