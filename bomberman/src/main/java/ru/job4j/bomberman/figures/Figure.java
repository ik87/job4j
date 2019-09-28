package ru.job4j.bomberman.figures;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public interface Figure {

    Cell position();

    void setPosition(Cell cell);

    /**
     * Calculate way from source to dest position.
     *
     * Example:
     *
     *          vertical == false                        vertical == true
     *
     *                [start]                               [finish]
     *                   *                                      *
     *                   *                                      *
     *                   *                      [start] * * * * *
     *      [finish] * * * * *[finish]                          *
     *                                                          *
     *                                                      [finish]
     *
     * @param dest destination position
     * @param vertical see example above
     *
     * @return
     */
    Cell[] way(Cell source, Cell dest, boolean vertical);
}
