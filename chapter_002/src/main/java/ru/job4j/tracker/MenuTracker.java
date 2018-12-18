package ru.job4j.tracker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MenuTracker {
    /**
     * The way interaction with main
     */
    private Input input;

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

    public MenuTracker(Input input, Tracker tracker) {
        this.tracker = tracker;
        this.input = input;
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
    private static void print(String title, Item[] items) {
        System.out.println(title);
        System.out.printf("%-20s%-11s%-25s%n", "Id", "Name", "Description");
        System.out.println("------------------------------------------------------------------------------");
        for (Item item : items) {
            System.out.printf("%-20s%-11s%-25s%n",
                    item.getId(), item.getName(), item.getDesc());
        }
        System.out.println("------------------------------------------------------------------------------");
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
            System.out.println("----------------- Add new request -----------------");
            String name = input.ask("Enter request name");
            String desc = input.ask("Enter request description");
            Item item = new Item(name, desc);
            tracker.add(item);
            System.out.println("-------------- Added new request with gitId: " + item.getId());
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
                System.out.printf("Request where id %s has changed%n", id);
            } else {
                System.out.printf("Request where id %s hasn't changed%n", id);
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
                System.out.printf("Request where id %s has deleted%n", id);
            } else {
                System.out.printf("Request where id %s hasn't found%n", id);
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
            System.out.println();
            String id = input.ask("Enter id request");
            Item[] item = {tracker.findById(id)};
            if (item[0] == null) {
                System.out.printf("Request where id %s hasn't found%n", id);
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
            System.out.println();
            String name = input.ask("Enter name request");
            Item[] item = tracker.findByName(name);
            if (item.length == 0) {
                System.out.printf("Request where name %s hasn't found%n", name);
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
