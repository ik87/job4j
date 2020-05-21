package ru.job4j.tracker;

import org.junit.Test;


import javax.transaction.Transactional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class TrackerHibernateTest {
    @Test
    public void whenAddThenAdded() throws Exception {

        try (TrackerHibernate trackerHibernate = new TrackerHibernate().init()) {
            long time = 1555505563550L; // 17.04.19 12:00
            Item item = trackerHibernate.add(new Item("Jack", "todo1", time));
            Item result = trackerHibernate.findById(item.getId());
            assertThat(result.getId(), is(item.getId()));
            assertThat(result.getName(), is(item.getName()));
            assertThat(result.getDesc(), is(item.getDesc()));
            assertThat(result.getCreate(), is(item.getCreate()));
            trackerHibernate.delete(item.getId());
        }
    }

    @Test
    public void whenFindByNameThenFound() throws Exception {
        try (TrackerHibernate trackerHibernate = new TrackerHibernate().init()) {
            long time = 1555505563550L; // 17.04.19 12:00
            Item item = trackerHibernate.add(new Item("Jack", "todo1", time));
            Item[] result = trackerHibernate.findByName("Jack");
            assertThat(result[0].getId(), is(item.getId()));
            assertThat(result[0].getName(), is(item.getName()));
            assertThat(result[0].getDesc(), is(item.getDesc()));
            assertThat(result[0].getCreate(), is(item.getCreate()));
            trackerHibernate.delete(item.getId());
        }
    }

    @Test
    public void whenFindAllThenFound() throws Exception {
        try (TrackerHibernate trackerHibernate = new TrackerHibernate().init()) {
            long time = 1555505563550L; // 17.04.19 12:00
            Item[] items = new Item[4];
            items[0] = trackerHibernate.add(new Item("Jack", "todo1", time));
            items[1] = trackerHibernate.add(new Item("Max", "todo2", time));
            items[2] = trackerHibernate.add(new Item("Sam", "todo3", time));
            items[3] = trackerHibernate.add(new Item("Jack", "todo3", time));

            Item[] result = trackerHibernate.findAll();
            for (int i = 0; i < items.length; i++) {
                assertThat(result[i].getId(), is(items[i].getId()));
                assertThat(result[i].getName(), is(items[i].getName()));
                assertThat(result[i].getDesc(), is(items[i].getDesc()));
                assertThat(result[i].getCreate(), is(items[i].getCreate()));
                trackerHibernate.delete(result[i].getId());
            }

        }
    }

    @Test
    public void whenReplaceThenReplaced() throws Exception {
        try (TrackerHibernate trackerHibernate = new TrackerHibernate().init()) {
            long time = 1555505563550L; // 17.04.19 12:00
            Item item = trackerHibernate.add(new Item("Jack", "todo1", time));

            Item expected = new Item("Dan", "todo4", time);
            expected.setId(item.getId());
            trackerHibernate.replace(item.getId(), expected);
            Item result = trackerHibernate.findById(item.getId());

            assertThat(result.getId(), is(expected.getId()));
            assertThat(result.getName(), is(expected.getName()));
            assertThat(result.getDesc(), is(expected.getDesc()));
            assertThat(result.getCreate(), is(expected.getCreate()));
            trackerHibernate.delete(item.getId());
        }
    }


}