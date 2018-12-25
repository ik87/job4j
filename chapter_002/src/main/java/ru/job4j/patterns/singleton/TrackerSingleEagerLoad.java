package ru.job4j.patterns.singleton;

import ru.job4j.tracker.Item;
import ru.job4j.tracker.Tracker;

//Eager loading - энергичная загрузка
public enum TrackerSingleEagerLoad {
    INSTANCE;

    private Tracker tracker = new Tracker();

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
        TrackerSingleEagerLoad tracker = TrackerSingleEagerLoad.INSTANCE;
        //tracker.add()
    }
    */
}
