package ru.job4j.patterns.singleton;

import ru.job4j.tracker.Item;
import ru.job4j.tracker.Tracker;

public class TrackerSingleEagerLoad2 {
    private static final TrackerSingleEagerLoad2 INSTANCE = new TrackerSingleEagerLoad2();
    private Tracker tracker = new Tracker();

    private TrackerSingleEagerLoad2() {

    }

    public static TrackerSingleEagerLoad2 getInstance() {
        return INSTANCE;
    }


    public Item add(Item item) {
        return tracker.add(item);
    }

    public boolean replace(String id, Item item) {
        return tracker.replace(id, item);
    }

    public boolean delete(String id) {
        return tracker.delete(id);
    }

    public Item findById(String id) {
        return tracker.findById(id);
    }

    public Item[] findByName(String name) {
        return tracker.findByName(name);
    }

    public Item[] findAll() {
        return tracker.findAll();
    }

    /*
    public static void main(String[] args) {
        TrackerSingleEagerLoad2 tracker = TrackerSingleEagerLoad2.getInstance();
    }
    */

}
