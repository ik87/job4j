package ru.job4j.patterns.singleton;

import ru.job4j.tracker.Item;
import ru.job4j.tracker.Tracker;

public class TrackerSingleLazyLoad2 {

    private Tracker tracker = new Tracker();

    private TrackerSingleLazyLoad2() {

    }

    public static TrackerSingleLazyLoad2 getInstance() {
        return Holder.INSTANCE;
    }


    private static final class Holder {
        private static final TrackerSingleLazyLoad2 INSTANCE = new TrackerSingleLazyLoad2();
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
        TrackerSingleLazyLoad2 tracker = TrackerSingleLazyLoad2.getInstance();
    }
    */


}
