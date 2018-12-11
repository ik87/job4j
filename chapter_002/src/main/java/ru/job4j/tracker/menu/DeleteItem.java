package ru.job4j.tracker.menu;

import ru.job4j.tracker.*;

/**
 * Delete request
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 */
public class DeleteItem implements UserAction {
    @Override
    public String key() {
        return StartUI.DELETE;
    }

    @Override
    public void execute(Input input, Tracker tracker) {
        String title = "----------------------------- Delete some request -----------------------------";
        Show.show(title, tracker.findAll());
        String id = input.ask("Enter id request");
        if (tracker.delete(id)) {
            System.out.printf("Request where id %s has deleted%n", id);
        } else {
            System.out.printf("Request where id %s hasn't found%n", id);
        }
    }

    @Override
    public String info() {
        return String.format("%s%s%n", key(), ".Delete item");
    }
}
