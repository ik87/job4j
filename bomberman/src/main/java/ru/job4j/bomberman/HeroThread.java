package ru.job4j.bomberman;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.bomberman.figures.Cell;
import ru.job4j.bomberman.figures.ThreadFigure;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;
import java.util.function.Consumer;

/**
 * Some game mechanic where Hero is making calculate steps,
 * if on the way he collide with block then he go on 1 step bellow, higher or to the left,
 * to the right from his current position then recalculate way to destination again.
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class HeroThread extends ThreadFigure {
    private static final Logger LOG = LogManager.getLogger(HeroThread.class.getName());

    private final Cell source;
    private final Cell dest;
    private final SplittableRandom random = new SplittableRandom();

    public HeroThread(Board board, Cell source, Cell dest) {
        super(board, source);
        this.source = source;
        this.dest = dest;

    }


    @Override
    public void run() {

        //Generate way
        Cell[] way = way(source, dest, random.nextBoolean());

        LOG.debug("generated way {}", List.of(way));

        for (int i = 1; i < way.length && !Thread.currentThread().isInterrupted(); i++) {
            Cell currentStep = way[i - 1];
            Cell nextStep = way[i];
        try {
            if (!board.move(currentStep, nextStep)) {
                //Check where collision happened on X or Y line
                //If it was vertical collision then move hero on 1 step left or right
                //If it was horizontal collision then move hero on 1 step Up or Down
                boolean vertical = false;
                Cell newSource;
                if (currentStep.getY() != nextStep.getY()) {
                    newSource = goLeftOrRight(currentStep, board.size(), random.nextBoolean());
                    vertical = true;
                } else {
                    newSource = goUpOrDown(currentStep, board.size(), random.nextBoolean());
                }
                //recalculate way from current position
                way = way(newSource, dest, vertical);

                //concat current position with way
                way = concat(currentStep, way);

                i = 0;
                LOG.debug("Generate new way: {}", List.of(way));
            }

            LOG.debug("hero move to {}", nextStep);
            position.set(nextStep);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        }
    }


    /**
     * Add current step at begin array
     */
    private Cell[] concat(Cell cell, Cell[] tmp) {
        Cell[] way = new Cell[tmp.length + 1];
        way[0] = cell;
        System.arraycopy(tmp, 0, way, 1, tmp.length);
        return way;
    }



    /**
     * Calculate way from source to dest position.
     * Example:
     * vertical == true                        vertical == false
     * <p>
     * [start]                               [finish]
     * *                                      *
     * *                                      *
     * *                      [start] * * * * *
     * [finish] * * * * *[finish]                          *
     * *
     * [finish]
     *
     * @param dest     destination position
     * @param vertical see example above
     * @return array calculated way
     */
    private Cell[] way(Cell source, Cell dest, boolean vertical) {
        List<Cell> steps = new ArrayList<>();
        //set motion vector
        int vecX = source.getX() <= dest.getX() ? 1 : -1;
        int vecY = source.getY() <= dest.getY() ? 1 : -1;

        if (vertical) {
            //vertical
            wayFill(source.getY(), dest.getY(), vecY, (y) -> steps.add(new Cell(source.getX(), y)));
            int posY = steps.remove(steps.size() - 1).getY();
            //horizontal
            wayFill(source.getX(), dest.getX(), vecX, (x) -> steps.add(new Cell(x, posY)));
        } else {
            //horizontal
            wayFill(source.getX(), dest.getX(), vecX, (x) -> steps.add(new Cell(x, source.getY())));
            int posX = steps.remove(steps.size() - 1).getX();
            //vertical
            wayFill(source.getY(), dest.getY(), vecY, (y) -> steps.add(new Cell(posX, y)));
        }

        return steps.toArray(Cell[]::new);
    }

    /**
     * Iterator for fill way
     */
    private void wayFill(int source, int dest, int vec, Consumer<Integer> consumer) {
        for (; source != dest; source += vec) {
            consumer.accept(source);
        }
        consumer.accept(dest);
    }
}
