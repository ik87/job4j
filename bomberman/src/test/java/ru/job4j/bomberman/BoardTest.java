package ru.job4j.bomberman;

import org.junit.Test;
import ru.job4j.bomberman.Figures.Cell;
import ru.job4j.bomberman.Figures.Figure;
import ru.job4j.bomberman.Figures.Hero;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class BoardTest {
    @Test
    public void whenHeroGoThenHeCome() {
        final int sizeBoard = 4; //4x4
        Cell source = new Cell(1,0);
        Cell dest = new Cell(3,2);
        ExecutorService pool = Executors.newFixedThreadPool(1);
        Figure hero = new Hero(source);
        Board board = new Board(sizeBoard, hero, pool);
        board.move(null, dest);
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertThat(dest.getX(), is(hero.position().getX()));
        assertThat(dest.getY(), is(hero.position().getY()));

    }

}