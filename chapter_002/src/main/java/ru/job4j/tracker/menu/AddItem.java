package ru.job4j.tracker.menu;

import ru.job4j.tracker.*;

/**
 * Create Item
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 */
public class AddItem implements UserAction {
    @Override
    public String key() {
        return StartUI.ADD;
    }

    @Override
    public void execute(Input input, Tracker tracker) {
        System.out.println("----------------- Add new request -----------------");
        String name = input.ask("Enter request name");
        String desc = input.ask("Enter request description");
        Item item = new Item(name, desc);
        tracker.add(item);
        System.out.println("-------------- Added new request with gitId: " + item.getId());
    }

    @Override
    public String info() {
        return String.format("%s%s%n", key(), ".Add new item");
    }
}