package ru.job4j.tracker;

import org.junit.Test;

import java.io.InputStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class TrackerSQLTest {
    private InputStream config = TrackerSQL.class.getClassLoader().getResourceAsStream("app.properties");
    @Test
    public void checkConnection() throws Exception {
        try (TrackerSQL trackerSQL = new TrackerSQL(config)) {
            assertTrue(trackerSQL.init());
        }
    }

    @Test
    public void whenAddThenAdded() throws Exception {
        try (TrackerSQL trackerSQL = new TrackerSQL(config)) {
            trackerSQL.init();
            trackerSQL.dropTable();
            trackerSQL.createTables();
            long time = 1555505563550L; // 17.04.19 12:00
            Item item = new Item("Jack", "todo1", time);
            item.setId("1");
            trackerSQL.add(item);
            Item result = trackerSQL.findById("1");
            assertThat(result.getId(), is(item.getId()));
            assertThat(result.getName(), is(item.getName()));
            assertThat(result.getDesc(), is(item.getDesc()));
            assertThat(result.getCreate(), is(item.getCreate()));
            trackerSQL.dropTable();
        }
    }

    @Test
    public void whenFindByNameThenFound() throws Exception {
        try (TrackerSQL trackerSQL = new TrackerSQL(config)) {
            trackerSQL.init();
            trackerSQL.dropTable();
            trackerSQL.createTables();
            long time = 1555505563550L; // 17.04.19 12:00

            Item item1 = new Item("Jack", "todo1", time);
            item1.setId("1");
            Item item2 = new Item("Max", "todo2", time);
            item2.setId("2");
            Item item3 = new Item("Sam", "todo3", time);
            item3.setId("3");
            Item item4 = new Item("Jack", "todo3", time);
            item4.setId("4");

            trackerSQL.add(item1);
            trackerSQL.add(item2);
            trackerSQL.add(item3);
            trackerSQL.add(item4);

            Item[] result = trackerSQL.findByName("Jack");

            assertThat(result[0].getId(), is(item1.getId()));
            assertThat(result[0].getName(), is(item1.getName()));
            assertThat(result[0].getDesc(), is(item1.getDesc()));
            assertThat(result[0].getCreate(), is(item1.getCreate()));

            assertThat(result[1].getId(), is(item4.getId()));
            assertThat(result[1].getName(), is(item4.getName()));
            assertThat(result[1].getDesc(), is(item4.getDesc()));
            assertThat(result[1].getCreate(), is(item4.getCreate()));
            trackerSQL.dropTable();
        }
    }
    @Test
    public void whenFindAllThenFound() throws Exception {
        try (TrackerSQL trackerSQL = new TrackerSQL(config)) {
            trackerSQL.init();
            trackerSQL.dropTable();
            trackerSQL.createTables();
            long time = 1555505563550L; // 17.04.19 12:00
            Item[] items = new Item[4];
            items[0] = new Item("Jack", "todo1", time);
            items[0].setId("1");
            items[1] = new Item("Max", "todo2", time);
            items[1].setId("2");
            items[2] = new Item("Sam", "todo3", time);
            items[2].setId("3");
            items[3] = new Item("Jack", "todo3", time);
            items[3].setId("4");

            trackerSQL.add(items[0]);
            trackerSQL.add(items[1]);
            trackerSQL.add(items[2]);
            trackerSQL.add(items[3]);

            Item[] result = trackerSQL.findAll();
            for (int i = 0; i < items.length; i++) {
                assertThat(result[i].getId(), is(items[i].getId()));
                assertThat(result[i].getName(), is(items[i].getName()));
                assertThat(result[i].getDesc(), is(items[i].getDesc()));
                assertThat(result[i].getCreate(), is(items[i].getCreate()));
            }
            trackerSQL.dropTable();
        }
    }
    @Test
    public void whenReplaceThenReplaced() throws Exception {
        try (TrackerSQL trackerSQL = new TrackerSQL(config)) {
            trackerSQL.init();
            trackerSQL.dropTable();
            trackerSQL.createTables();
            long time = 1555505563550L; // 17.04.19 12:00
            Item[] items = new Item[4];
            items[0] = new Item("Jack", "todo1", time);
            items[0].setId("1");
            items[1] = new Item("Max", "todo2", time);
            items[1].setId("2");
            items[2] = new Item("Sam", "todo3", time);
            items[2].setId("3");
            items[3] = new Item("Jack", "todo3", time);
            items[3].setId("4");
            trackerSQL.add(items[0]);
            trackerSQL.add(items[1]);
            trackerSQL.add(items[2]);
            trackerSQL.add(items[3]);

            Item expected = new Item("Dan", "todo4", time);
            expected.setId("2");
            trackerSQL.replace("2", expected);
            Item result = trackerSQL.findById("2");

            assertThat(result.getId(), is(expected.getId()));
            assertThat(result.getName(), is(expected.getName()));
            assertThat(result.getDesc(), is(expected.getDesc()));
            assertThat(result.getCreate(), is(expected.getCreate()));
            trackerSQL.dropTable();
        }
    }
    @Test
    public void whenDeleteThenDeleted() throws Exception {
        try (TrackerSQL trackerSQL = new TrackerSQL(config)) {
            trackerSQL.init();
            trackerSQL.dropTable();
            trackerSQL.createTables();
            long time = 1555505563550L; // 17.04.19 12:00

            Item item = new Item("Dan", "todo4", time);
            item.setId("1");
            trackerSQL.add(item);
            assertNotNull(trackerSQL.findById("1"));
            trackerSQL.delete("1");
            assertNull(trackerSQL.findById("1"));
            trackerSQL.dropTable();
        }
    }
}