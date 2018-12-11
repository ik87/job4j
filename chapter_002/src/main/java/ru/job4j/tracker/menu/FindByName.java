package ru.job4j.tracker.menu;

import ru.job4j.tracker.*;

/**
 * find request by name
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 */
public class FindByName implements UserAction {
    @Override
    public String key() {
        return StartUI.FIND_BY_NAME;
    }

    @Override
    public void execute(Input input, Tracker tracker) {
        String title = "-------------------------------- Find by name --------------------------------";
        System.out.println();
        String name = input.ask("Enter name request");
        Item[] item = tracker.findByName(name);
        if (item.length == 0) {
            System.out.printf("Request where name %s hasn't found%n", name);
        } else {
            Show.show(title, item);
        }
    }

    @Override
    public String info() {
        return String.format("%s%s%n", key(), ".Find item by name");
    }
}
