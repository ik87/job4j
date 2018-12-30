package ru.job4j;

import org.junit.Test;
import ru.job4j.chess.Logic;
import ru.job4j.chess.firuges.*;
import ru.job4j.chess.firuges.white.*;
import ru.job4j.chess.firuges.black.*;

import java.util.Objects;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Testing all steps and intersection figures
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class LogicTest {

    /**
     * Testing all steps and intersection figures with use matrix mask
     *
     * @param figure current figure
     * @param intersect figure
     * @return matrix steps
     */
    private int[][] testAllSteps(Figure figure, Figure intersect) {
        int[][] map = new int[5][5];
        int i = 0;
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map.length; x++) {
                // System.out.println(((map.length * y) + x) + " " +Cell.values()[((map.length * y) + x + i)]);
                if (testStepFigure(figure, Cell.values()[((map.length * y) + x + i)], intersect)) {
                    //System.out.println("^");
                    map[y][x] = 1;
                } else {
                    map[y][x] = 0;
                }
            }
            i += 3;
        }
        return map;
    }

    /**
     * General method for testing steps and intersection
     *
     * @param figure    current figure
     * @param dest      destination position
     * @param intersect figure for test intersection
     * @return state
     */
    private boolean testStepFigure(Figure figure, Cell dest, Figure intersect) {
        Logic logic = new Logic();
        logic.add(figure);
        //test intersect
        if (!Objects.isNull(intersect)) {
            logic.add(intersect);
        }

        return logic.move(figure.position(), dest);
    }

    @Test
    public void testPawnBlack() {
        int[][] expected = {
                //1  2  3  4  5
                {0, 0, 0, 0, 0}, //A
                {0, 0, 0, 0, 0}, //B
                {0, 1, 0, 0, 0}, //C
                {0, 0, 0, 0, 0}, //D
                {0, 0, 0, 0, 0}  //E
        };

        int[][] result = testAllSteps(new PawnBlack(Cell.C3), null);
        assertThat(result, is(expected));

    }

    @Test
    public void testPawnWhite() {
        int[][] expected = {
                //1  2  3  4  5
                {0, 0, 0, 0, 0}, //A
                {0, 0, 0, 0, 0}, //B
                {0, 0, 0, 1, 0}, //C
                {0, 0, 0, 0, 0}, //D
                {0, 0, 0, 0, 0}  //E
        };

        int[][] result = testAllSteps(new PawnWhite(Cell.C3), null);
        assertThat(result, is(expected));

    }

    @Test
    public void testPawnBlackIntersect() {
        int[][] expected = {
                //1  2  3  4  5
                {0, 0, 0, 0, 0}, //A
                {0, 0, 0, 0, 0}, //B
                {0, 0, 0, 0, 0}, //C
                {0, 0, 0, 0, 0}, //D
                {0, 0, 0, 0, 0}  //E
        };

        int[][] result = testAllSteps(new PawnBlack(Cell.C3), new PawnBlack(Cell.C2));
        assertThat(result, is(expected));

    }

    @Test
    public void testPawnWhiteIntersect() {
        int[][] expected = {
                //1  2  3  4  5
                {0, 0, 0, 0, 0}, //A
                {0, 0, 0, 0, 0}, //B
                {0, 0, 0, 0, 0}, //C
                {0, 0, 0, 0, 0}, //D
                {0, 0, 0, 0, 0}  //E
        };

        int[][] result = testAllSteps(new PawnWhite(Cell.C3), new PawnBlack(Cell.C4));
        assertThat(result, is(expected));

    }

    @Test
    public void testBishopWhite() {
        int[][] expected = {
                //1  2  3  4  5
                {1, 0, 0, 0, 1}, //A
                {0, 1, 0, 1, 0}, //B
                {0, 0, 0, 0, 0}, //C
                {0, 1, 0, 0, 0}, //D
                {1, 0, 0, 0, 0}  //E
        };

        int[][] result = testAllSteps(new BishopWhite(Cell.C3), new PawnBlack(Cell.D4));
        assertThat(result, is(expected));

    }

    @Test
    public void testBishopBlack() {
        int[][] expected = {
                //1  2  3  4  5
                {0, 0, 0, 0, 1}, //A
                {0, 0, 0, 1, 0}, //B
                {0, 0, 0, 0, 0}, //C
                {0, 1, 0, 1, 0}, //D
                {1, 0, 0, 0, 1}  //E
        };

        int[][] result = testAllSteps(new BishopBlack(Cell.C3), new PawnBlack(Cell.B2));
        assertThat(result, is(expected));

    }

    @Test
    public void testKingBlack() {
        int[][] expected = {
                //1  2  3  4  5
                {0, 0, 0, 0, 0}, //A
                {0, 1, 1, 0, 0}, //B
                {0, 1, 0, 1, 0}, //C
                {0, 1, 1, 1, 0}, //D
                {0, 0, 0, 0, 0}  //E
        };

        int[][] result = testAllSteps(new KingBlack(Cell.C3), new PawnBlack(Cell.B4));
        assertThat(result, is(expected));

    }

    @Test
    public void testKingWhite() {
        int[][] expected = {
                //1  2  3  4  5
                {0, 0, 0, 0, 0}, //A
                {0, 1, 1, 1, 0}, //B
                {0, 1, 0, 1, 0}, //C
                {0, 0, 1, 1, 0}, //D
                {0, 0, 0, 0, 0}  //E
        };

        int[][] result = testAllSteps(new KingWhite(Cell.C3), new PawnBlack(Cell.D2));
        assertThat(result, is(expected));

    }

    @Test
    public void testKnightWhite() {
        int[][] expected = {
                //1  2  3  4  5
                {0, 1, 0, 1, 0}, //A
                {1, 0, 0, 0, 1}, //B
                {0, 0, 0, 0, 0}, //C
                {1, 0, 0, 0, 1}, //D
                {0, 1, 0, 1, 0}  //E
        };

        int[][] result = testAllSteps(new KnightWhite(Cell.C3), new PawnBlack(Cell.B4));
        assertThat(result, is(expected));

    }

    @Test
    public void testKnightBlack() {
        int[][] expected = {
                //1  2  3  4  5
                {0, 1, 0, 1, 0}, //A
                {1, 0, 0, 0, 1}, //B
                {0, 0, 0, 0, 0}, //C
                {1, 0, 0, 0, 1}, //D
                {0, 1, 0, 0, 0}  //E
        };

        int[][] result = testAllSteps(new KnightBlack(Cell.C3), new PawnBlack(Cell.E4));
        assertThat(result, is(expected));

    }

    @Test
    public void testQeenBlack() {
        int[][] expected = {
                //1  2  3  4  5
                {0, 0, 1, 0, 1}, //A
                {0, 0, 1, 1, 0}, //B
                {1, 1, 0, 1, 1}, //C
                {0, 1, 1, 1, 0}, //D
                {1, 0, 1, 0, 1}  //E
        };

        int[][] result = testAllSteps(new QeenBlack(Cell.C3), new PawnBlack(Cell.B2));
        assertThat(result, is(expected));

    }

    @Test
    public void testQeenWhite() {
        int[][] expected = {
                //1  2  3  4  5
                {1, 0, 1, 0, 1}, //A
                {0, 1, 1, 1, 0}, //B
                {1, 1, 0, 1, 1}, //C
                {0, 1, 1, 0, 0}, //D
                {1, 0, 1, 0, 0}  //E
        };

        int[][] result = testAllSteps(new QeenWhite(Cell.C3), new PawnBlack(Cell.D4));
        assertThat(result, is(expected));

    }

    @Test
    public void testRookWhite() {
        int[][] expected = {
                //1  2  3  4  5
                {0, 0, 1, 0, 0}, //A
                {0, 0, 1, 0, 0}, //B
                {1, 1, 0, 0, 0}, //C
                {0, 0, 1, 0, 0}, //D
                {0, 0, 1, 0, 0}  //E
        };

        int[][] result = testAllSteps(new RookWhite(Cell.C3), new PawnBlack(Cell.C4));
        assertThat(result, is(expected));

    }

    @Test
    public void testRookBlack() {
        int[][] expected = {
                //1  2  3  4  5
                {0, 0, 0, 0, 0}, //A
                {0, 0, 0, 0, 0}, //B
                {1, 1, 0, 1, 1}, //C
                {0, 0, 1, 0, 0}, //D
                {0, 0, 1, 0, 0}  //E
        };

        int[][] result = testAllSteps(new RookBlack(Cell.C3), new PawnBlack(Cell.B3));
        assertThat(result, is(expected));

    }
}
