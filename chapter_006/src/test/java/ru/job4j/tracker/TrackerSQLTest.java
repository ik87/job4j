package ru.job4j.tracker;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class TrackerSQLTest {

    public Connection init() {
        try (InputStream in = TrackerSQL.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            return DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")


            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }


    @Test
    public void whenAddThenAdded() throws Exception {
        try (TrackerSQL trackerSQL = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            long time = 1555505563550L; // 17.04.19 12:00
            Item item = trackerSQL.add(new Item("Jack", "todo1", time));
            Item result = trackerSQL.findById(item.getId());
            assertThat(result.getId(), is(item.getId()));
            assertThat(result.getName(), is(item.getName()));
            assertThat(result.getDesc(), is(item.getDesc()));
            assertThat(result.getCreate(), is(item.getCreate()));
        }
    }

    @Test
    public void whenFindByNameThenFound() throws Exception {
        try (TrackerSQL trackerSQL = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            long time = 1555505563550L; // 17.04.19 12:00
            Item item = trackerSQL.add(new Item("Jack", "todo1", time));
            Item[] result = trackerSQL.findByName("Jack");
            assertThat(result[0].getId(), is(item.getId()));
            assertThat(result[0].getName(), is(item.getName()));
            assertThat(result[0].getDesc(), is(item.getDesc()));
            assertThat(result[0].getCreate(), is(item.getCreate()));
        }
    }

    @Test
    public void whenFindAllThenFound() throws Exception {
        try (TrackerSQL trackerSQL = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            long time = 1555505563550L; // 17.04.19 12:00
            Item[] items = new Item[4];
            items[0] = trackerSQL.add(new Item("Jack", "todo1", time));
            items[1] = trackerSQL.add(new Item("Max", "todo2", time));
            items[2] = trackerSQL.add(new Item("Sam", "todo3", time));
            items[3] = trackerSQL.add(new Item("Jack", "todo3", time));

            Item[] result = trackerSQL.findAll();
            for (int i = 0; i < items.length; i++) {
                assertThat(result[i].getId(), is(items[i].getId()));
                assertThat(result[i].getName(), is(items[i].getName()));
                assertThat(result[i].getDesc(), is(items[i].getDesc()));
                assertThat(result[i].getCreate(), is(items[i].getCreate()));
            }
        }
    }

    @Test
    public void whenReplaceThenReplaced() throws Exception {
        try (TrackerSQL trackerSQL = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            // trackerSQL.createTables();
            long time = 1555505563550L; // 17.04.19 12:00
            Item[] items = new Item[4];
            items[0] = trackerSQL.add(new Item("Jack", "todo1", time));
            items[1] = trackerSQL.add(new Item("Max", "todo2", time));
            items[2] = trackerSQL.add(new Item("Sam", "todo3", time));
            items[3] = trackerSQL.add(new Item("Jack", "todo3", time));

            Item expected = new Item("Dan", "todo4", time);
            expected.setId(items[2].getId());
            trackerSQL.replace(items[2].getId(), expected);
            Item result = trackerSQL.findById(items[2].getId());

            assertThat(result.getId(), is(expected.getId()));
            assertThat(result.getName(), is(expected.getName()));
            assertThat(result.getDesc(), is(expected.getDesc()));
            assertThat(result.getCreate(), is(expected.getCreate()));
        }
    }

    @Test
    public void whenDeleteThenDeleted() throws Exception {
        try (TrackerSQL trackerSQL = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            long time = 1555505563550L; // 17.04.19 12:00
            Item item = trackerSQL.add(new Item("Dan", "todo4", time));
            assertNotNull(trackerSQL.findById(item.getId()));
            trackerSQL.delete(item.getId());
            assertNull(trackerSQL.findById(item.getId()));
        }
    }
}