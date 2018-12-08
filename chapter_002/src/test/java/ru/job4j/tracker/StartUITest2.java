package ru.job4j.tracker;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.StringJoiner;


import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class StartUITest2 {

    private Tracker tracker;
    private final PrintStream stdout = System.out;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final String menu =
            new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                    .add("Menu:")
                    .add("0.Add new item")
                    .add("1.Show all items")
                    .add("2.Edit item")
                    .add("3.Delete item")
                    .add("4.Find item by id")
                    .add("5.Find item by name")
                    .add("6.Exit program")
                    .toString();

    private StringBuilder body;

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

        //init StringBuilder body
        body = new StringBuilder();
        body.append(menu);
    }

    @After
    public void backOutput() {
        System.setOut(new PrintStream(stdout));
    }


    @Test
    public void whenUserAddItemThenTrackerHasNewItemWithSameName() {
        Tracker tracker = new Tracker();
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
        body.append(String.format("------------------------------ Show all requests -----------------------------%n"));
        body.append(String.format("%-20s%-11s%-25s%n", "Id", "Name", "Description"));
        body.append(String.format("------------------------------------------------------------------------------%n"));
        for (Item item : tracker.findAll()) {
            body.append(
                    String.format("%-20s%-11s%-25s%n",
                            item.getId(), item.getName(), item.getDesc()));
        }
        body.append(String.format("------------------------------------------------------------------------------%n"));
        body.append(menu);
        new StartUI(new StubInput(action), tracker).init();
        // assertThat(new String(out.toByteArray()).contains(expected), is(true));
        assertThat(out.toString(), is(body.toString()));

    }

    @Test
    public void whenFindByNameThenShowMatches() {
        String[] action = {"5", "test name", "6"};
        body.append(System.lineSeparator());
        body.append(String.format("-------------------------------- Find by name --------------------------------%n"));
        body.append(String.format("%-20s%-11s%-25s%n", "Id", "Name", "Description"));
        body.append(String.format("------------------------------------------------------------------------------%n"));
        for (Item item : tracker.findByName("test name")) {
            body.append(
                    String.format("%-20s%-11s%-25s%n",
                            item.getId(), item.getName(), item.getDesc()));
        }
        body.append(String.format("------------------------------------------------------------------------------%n"));
        body.append(menu);
        new StartUI(new StubInput(action), tracker).init();
        assertThat(out.toString(), is(body.toString()));
    }

    @Test
    public void whenFindByIdThenShow() {
        String id = tracker.findAll()[3].getId();
        String[] action = {"4", id, "6"};
        body.append(System.lineSeparator());
        body.append(String.format("----------------------------- Find by Id -------------------------------------%n"));
        body.append(String.format("%-20s%-11s%-25s%n", "Id", "Name", "Description"));
        body.append(String.format("------------------------------------------------------------------------------%n"));
        Item item = tracker.findById(id);
        body.append(String.format("%-20s%-11s%-25s%n",
                item.getId(), item.getName(), item.getDesc()));
        body.append(String.format("------------------------------------------------------------------------------%n"));
        body.append(menu);
        new StartUI(new StubInput(action), tracker).init();
        assertThat(out.toString(), is(body.toString()));
    }

}