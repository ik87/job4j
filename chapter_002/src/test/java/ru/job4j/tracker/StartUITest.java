package ru.job4j.tracker;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class StartUITest {

    private ITracker tracker;
    private final PrintStream stdout = System.out;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Before
    public void init() {
        //init tracker and push items
        tracker = new Tracker();
        tracker.add(new Item("test name", "desc"));
        tracker.add(new Item("test name2", "desc2"));
        tracker.add(new Item("test name3", "desc3"));
        tracker.add(new Item("test name4", "desc4"));
        tracker.add(new Item("test name", "desc5"));
        tracker.add(new Item("test name6", "desc6"));
        tracker.add(new Item("test name7", "desc7"));

        //change print output
        System.setOut(new PrintStream(out));
    }

    @After
    public void backOutput() {
        System.setOut(new PrintStream(stdout));
    }


    @Test
    public void whenUserAddItemThenTrackerHasNewItemWithSameName() {
        ITracker tracker = new Tracker();
        String[] action = {"0", "test name", "desc", "6"};
        new StartUI(new StubInput(action), tracker).init();
        assertThat(tracker.findAll()[0].getName(), is("test name"));
    }

    @Test
    public void whenUpdateThenTrackerHasUpdatedValue() {
        String[] action = {"2", tracker.findAll()[0].getId(), "changed name", "changed desc", "6"};
        new StartUI(new StubInput(action), tracker).init();
        assertThat(tracker.findAll()[0].getName(), is("changed name"));
    }

    @Test
    public void whenDeleteThenTrackerEmpty() {
        String[] action = {"3", tracker.findAll()[0].getId(), "6"};
        new StartUI(new StubInput(action), tracker).init();
        assertThat(tracker.findAll().length, is(6));
    }


    @Test
    public void whenShowAll() {
        String[] action = {"1", "6"};
        StringBuilder expected = new StringBuilder();
        for (Item item : tracker.findAll()) {
            expected.append(
                    String.format("%-20s%-11s%-25s%n",
                            item.getId(), item.getName(), item.getDesc()));
        }
        new StartUI(new StubInput(action), tracker).init();
        assertThat(new String(out.toByteArray()).contains(expected), is(true));
    }

    @Test
    public void whenFindByNameThenShowMatches() {
        String[] action = {"5", "test name", "6"};
        StringBuilder expected = new StringBuilder();
        for (Item item : tracker.findByName("test name")) {
            expected.append(
                    String.format("%-20s%-11s%-25s%n",
                            item.getId(), item.getName(), item.getDesc()));
        }
        new StartUI(new StubInput(action), tracker).init();
        assertThat(out.toString().contains(expected), is(true));
    }

    @Test
    public void whenFindByIdThenShow() {
        String id = tracker.findAll()[3].getId();
        String[] action = {"4", id, "6"};
        Item item = tracker.findById(id);
        String expected = String.format("%-20s%-11s%-25s%n",
                item.getId(), item.getName(), item.getDesc());
        new StartUI(new StubInput(action), tracker).init();
        assertThat(out.toString().contains(expected), is(true));
    }

}