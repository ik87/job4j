package ru.job4j.bomberman;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.bomberman.figures.Cell;
import ru.job4j.bomberman.figures.ThreadFigure;


public class MonsterThread extends ThreadFigure {
    private static final Logger LOG = LogManager.getLogger(MonsterThread.class.getName());
    public MonsterThread(Board board, Cell position) {
        super(board, position);
    }

    @Override
    public void run() {
        Cell nextStep;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                boolean vertical = random.nextBoolean();
                nextStep = nextStep(position, vertical);
                while (!board.move(position, nextStep)) {
                    LOG.debug("monster {} intersect on {}", Thread.currentThread().getName(), nextStep);
                    nextStep = nextStep(position, vertical);

                }
                LOG.debug("monster {} go to {}", Thread.currentThread().getName(), nextStep);
                position.set(nextStep);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private Cell nextStep(Cell source, boolean vertical) {
        Cell newPosition;
        if (vertical) {
            newPosition = goLeftOrRight(source, board.size(), random.nextBoolean());
        } else {
            newPosition = goUpOrDown(source, board.size(), random.nextBoolean());
        }
        return newPosition;
    }
}
