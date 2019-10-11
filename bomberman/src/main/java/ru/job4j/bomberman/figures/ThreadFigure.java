package ru.job4j.bomberman.figures;

import ru.job4j.bomberman.Board;

import java.util.SplittableRandom;

public abstract class ThreadFigure extends Thread  {
    protected final Board board;
    protected final Cell position;
    protected final SplittableRandom random = new SplittableRandom();

    public ThreadFigure(Board board, Cell position) {
        this.board = board;
        this.position = position;
    }

    /**
     * Go at one step UP or Down from current position
     *
     * @param currentStep current position of hero
     * @param length      matrix's width
     * @param direction   where is he go? If true then UP if false then DOWN
     * @return new position
     */
    protected Cell goUpOrDown(Cell currentStep, int length, boolean direction) {
        Cell newSource;
        if (currentStep.getY() + 1 < length && currentStep.getY() - 1 >= 0) {
            newSource = direction
                    ? new Cell(currentStep.getX(), currentStep.getY() + 1)
                    : new Cell(currentStep.getX(), currentStep.getY() - 1);
            //If it was horizontal collision then move hero on 1 step up or down
        } else if (currentStep.getY() + 1 < length) {
            newSource = new Cell(currentStep.getX(), currentStep.getY() + 1);
        } else {
            newSource = new Cell(currentStep.getX(), currentStep.getY() - 1);
        }
        return newSource;
    }

    /**
     * Go at one step Left or Right from current position
     *
     * @param currentStep current position of hero
     * @param length      matrix's height
     * @param direction   where is he go? If true then LEFT if false then RIGHT
     * @return new position
     */
    protected Cell goLeftOrRight(Cell currentStep, int length, boolean direction) {
        Cell newSource;
        if (currentStep.getX() + 1 < length && currentStep.getX() - 1 >= 0) {
            newSource = direction
                    ? new Cell(currentStep.getX() + 1, currentStep.getY())
                    : new Cell(currentStep.getX() - 1, currentStep.getY());
        } else if (currentStep.getX() + 1 < length) {
            newSource = new Cell(currentStep.getX() + 1, currentStep.getY());
        } else {
            newSource = new Cell(currentStep.getX() - 1, currentStep.getY());
        }
        return newSource;
    }

    public Cell getPosition() {
        return position;
    }
}
