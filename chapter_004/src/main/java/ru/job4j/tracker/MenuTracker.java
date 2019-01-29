package ru.job4j.tracker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

public class MenuTracker {
    /**
     * The way interaction with main
     */
    private Input input;

    private final Consumer<String> output;
    /**
     * Storage tracker
     */
    private Tracker tracker;
    /**
     * StartUI
     */
    private StartUI startui;

    public void setStartui(StartUI startui) {
        this.startui = startui;
    }

    /**
     * List actions
     */


    private List<UserAction> actions = new ArrayList<>();

    public MenuTracker(Input input, Tracker tracker, Consumer<String> output) {
        this.tracker = tracker;
        this.input = input;
        this.output = output;
    }

    public int getActionsLength() {
        return this.actions.size();
    }

    /**
     * Fill Items to ArrayList
     */
    public void fillActions() {
        this.actions.add(new AddItem(StartUI.ADD, "Add new item"));
        this.actions.add(new ShowAllItems(StartUI.SHOW, "Show all items"));
        this.actions.add(new EditItem(StartUI.EDIT, "Edit item"));
        this.actions.add(new DeleteItem(StartUI.DELETE, "Delete item"));
        this.actions.add(new FindById(StartUI.FIND_BY_ID, "Find item by id"));
        this.actions.add(new FindByName(StartUI.FIND_BY_NAME, "Find item by name"));
        this.actions.add(new ExitProgram(StartUI.EXIT, "Exit program"));
    }

    /**
     * Select action
     *
     * @param key key to some action
     */
    public void select(String key) {
        for (UserAction action : this.actions) {
            if (action.key().equals(key)) {
                action.execute(this.input, this.tracker);
                break;
            }
        }
    }

    /**
     * Output main menu
     */
    public void show() {
        for (UserAction action : this.actions) {
            System.out.print(action.info());
        }
    }


    /**
     * Print items
     *
     * @param title title name
     * @param items array of items
     */
    private void print(String title, List<Item> items) {
        output.accept(String.format("%s%n", title));
        output.accept(String.format("%-20s%-11s%-25s%n", "Id", "Name", "Description"));
        output.accept("------------------------------------------------------------------------------");
        output.accept(System.lineSeparator());
        for (Item item : items) {
            output.accept(String.format("%-20s%-11s%-25s%n",
                    item.getId(), item.getName(), item.getDesc()));
        }
        output.accept("------------------------------------------------------------------------------");
        output.accept(System.lineSeparator());
    }

    /**
     * Convert long to date
     *
     * @param date date
     * @return formated date
     */
    private static String longToDate(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd,MM,yy");
        return sdf.format(new Date(date));
    }

    /**
     * Create Item
     */
    public class AddItem extends BaseAction {
        public AddItem(String key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            output.accept("----------------- Add new request -----------------\n");
            String name = input.ask("Enter request name");
            String desc = input.ask("Enter request description");
            Item item = new Item(name, desc);
            tracker.add(item);
            output.accept(String.format("-------------- Added new request with gitId: %s%n", item.getId()));
        }

    }

    /**
     * Show All Items
     */
    public class ShowAllItems extends BaseAction {
        public ShowAllItems(String key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            String title = "------------------------------ Show all requests -----------------------------";
            print(title, tracker.findAll());
        }

    }

    /**
     * Edit request
     */
    public class EditItem extends BaseAction {
        public EditItem(String key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            String title = "------------------------------ Edit some request ------------------------------";
            //  print(title, tracker.findAll());
            String id = input.ask("Enter id request");
            String name = input.ask("Enter request name");
            String desc = input.ask("Enter request description");
            Item item = new Item(name, desc);
            if (tracker.replace(id, item)) {
                output.accept(String.format("Request where id %s has changed%n", id));
            } else {
                output.accept(String.format("Request where id %s hasn't changed%n", id));
            }
        }

    }

    /**
     * Delete request
     */
    public class DeleteItem extends BaseAction {
        public DeleteItem(String key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            String title = "----------------------------- Delete some request -----------------------------";
            // print(title, tracker.findAll());
            String id = input.ask("Enter id request");
            if (tracker.delete(id)) {
                output.accept(String.format("Request where id %s has deleted%n", id));
            } else {
                output.accept(String.format("Request where id %s hasn't found%n", id));
            }
        }

    }

    /**
     * find request by id
     */
    public class FindById extends BaseAction {

        public FindById(String key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            String title = "----------------------------- Find by Id -------------------------------------";
            output.accept(System.lineSeparator());
            String id = input.ask("Enter id request");
            List<Item> item = new ArrayList<>();
            item.add(tracker.findById(id));
            if (item.get(0) == null) {
                output.accept(String.format("Request where id %s hasn't found%n", id));
            } else {
                print(title, item);
            }
        }

    }

    /**
     * find request by name
     */
    public class FindByName extends BaseAction {
        public FindByName(String key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            String title = "-------------------------------- Find by name --------------------------------";
            output.accept(System.lineSeparator());
            String name = input.ask("Enter name request");
            List<Item> item = tracker.findByName(name);
            if (item.size() == 0) {
                output.accept(String.format("Request where name %s hasn't found%n", name));
            } else {
                print(title, item);
            }
        }

    }

    /**
     * Exit the program
     */
    public class ExitProgram extends BaseAction {
        public ExitProgram(String key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            startui.setExit(true);
        }

    }


}
