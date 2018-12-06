package ru.job4j.tracker;

import org.junit.Test;
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

    @Test
    public void whenUserAddItemThenTrackerHasNewItemWithSameName() {
        Tracker tracker = new Tracker();
        new StartUI(new StubInput(new String[]{"0", "test name", "desc", "6"}), tracker).init();
        assertThat(tracker.findAll()[0].getName(), is("test name"));
    }

    @Test
    public void whenUpdateThenTrackerHasUpdatedValue() {
        //final String[] input = {"0", "test name", "desc", "6"};
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("test name", "desc"));
        new StartUI(new StubInput(new String[]{"2", item.getId(), "changed name", "changed desc", "6"}), tracker).init();
        assertThat(tracker.findAll()[0].getName(), is("changed name"));
    }

    @Test
    public void whenDeleteThenTrackerEmpty() {
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("test name", "desc"));
        new StartUI(new StubInput(new String[]{"3", item.getId(), "6"}), tracker).init();
        assertThat(tracker.findAll().length, is(0));
    }

}
