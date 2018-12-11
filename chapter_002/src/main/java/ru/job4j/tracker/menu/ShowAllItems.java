package ru.job4j.tracker.menu;

import ru.job4j.tracker.*;

/**
 * Show All Items
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 */
public class ShowAllItems implements UserAction {
    @Override
    public String key() {
        return StartUI.SHOW;
    }

    @Override
    public void execute(Input input, Tracker tracker) {
        String title = "------------------------------ Show all requests -----------------------------";
        Show.show(title, tracker.findAll());
    }

    @Override
    public String info() {
        return String.format("%s%s%n", key(), ".Show all items");
    }
}
