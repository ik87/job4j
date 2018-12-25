package ru.job4j.patterns.singleton;

import ru.job4j.tracker.Item;
import ru.job4j.tracker.Tracker;

//lazy loading
public class TrackerSingleLazyLoad {
    private static TrackerSingleLazyLoad instance;
    private Tracker tracker = new Tracker();

    private TrackerSingleLazyLoad() {

    }

    public static TrackerSingleLazyLoad getInstance() {
        if (instance == null) {
            instance = new TrackerSingleLazyLoad();
        }
        return instance;
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
        TrackerSingleLazyLoad tracker = TrackerSingleLazyLoad.getInstance();
    }
    */

}
