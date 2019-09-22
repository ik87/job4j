public interface Figure {

    void leftTurn();

    void rightTurn();

    void mirrorTurn();

    Condition move() throws InterruptedException;

}
