package ru.job4j.tracker.menu;

import ru.job4j.tracker.*;

/**
 * find request by id
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 */
public class FindById implements UserAction {
    @Override
    public String key() {
        return StartUI.FIND_BY_ID;
    }

    @Override
    public void execute(Input input, Tracker tracker) {
        String title = "----------------------------- Find by Id -------------------------------------";
        System.out.println();
        String id = input.ask("Enter id request");
        Item[] item = {tracker.findById(id)};
        if (item[0] == null) {
            System.out.printf("Request where id %s hasn't found%n", id);
        } else {
            Show.show(title, item);
        }
    }

    @Override
    public String info() {
        return String.format("%s%s%n", key(), ".Find item by id");
    }
}
