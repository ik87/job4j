package ru.job4j.patterns.singleton;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.tracker.Item;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test Single pattern trackers
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class SingletonTest {
    private Item[] items = new Item[7];

    @Before
    public void init() {
        items[0] = new Item("test name", "desc", 123L);
        items[1] = new Item("test name2", "desc2", 123L);
        items[2] = new Item("test name3", "desc3", 123L);
        items[3] = new Item("test name4", "desc4", 123L);
        items[4] = new Item("test name", "desc5", 123L);
        items[5] = new Item("test name6", "desc6", 123L);
        items[6] = new Item("test name7", "desc7", 123L);
    }

    @Test
    public void whenTrackerSingleEagerLoadingTwiceThenSame() {
        TrackerSingleEagerLoad instance = TrackerSingleEagerLoad.INSTANCE;
        for (Item item : items) {
            instance.add(item);
        }
        TrackerSingleEagerLoad expected = TrackerSingleEagerLoad.INSTANCE;

        assertThat(expected.findAll(), is(items));
    }

    @Test
    public void whenTrackerSingleEagerLoading2TwiceThenSame() {
        TrackerSingleEagerLoad2 instance = TrackerSingleEagerLoad2.getInstance();
        for (Item item : items) {
            instance.add(item);
        }
        TrackerSingleEagerLoad2 expected = TrackerSingleEagerLoad2.getInstance();

        assertThat(expected.findAll(), is(items));
    }

    @Test
    public void whenTrackerSingleLazyLoadingTwiceThenSame() {
        TrackerSingleLazyLoad instance = TrackerSingleLazyLoad.getInstance();
        for (Item item : items) {
            instance.add(item);
        }
        TrackerSingleLazyLoad expected = TrackerSingleLazyLoad.getInstance();

        assertThat(expected.findAll(), is(items));
    }

    @Test
    public void whenTrackerSingleLazyLoading2TwiceThenSame() {
        TrackerSingleLazyLoad2 instance = TrackerSingleLazyLoad2.getInstance();
        for (Item item : items) {
            instance.add(item);
        }
        TrackerSingleLazyLoad2 expected = TrackerSingleLazyLoad2.getInstance();

        assertThat(expected.findAll(), is(items));
    }
}
