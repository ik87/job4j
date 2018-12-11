package ru.job4j.tracker.menu;

import ru.job4j.tracker.*;

/**
 * Edit request
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 */
public class EditItem implements UserAction {
    @Override
    public String key() {
        return StartUI.EDIT;
    }

    @Override
    public void execute(Input input, Tracker tracker) {
        String title = "------------------------------ Edit some request ------------------------------";
        Show.show(title, tracker.findAll());
        String id = input.ask("Enter id request");
        String name = input.ask("Enter request name");
        String desc = input.ask("Enter request description");
        Item item = new Item(name, desc);
        if (tracker.replace(id, item)) {
            System.out.printf("Request where id %s has changed%n", id);
        } else {
            System.out.printf("Request where id %s hasn't changed%n", id);
        }
    }

    @Override
    public String info() {
        return String.format("%s%s%n", key(), ".Edit item");
    }
}
