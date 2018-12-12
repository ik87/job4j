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
        this.actions.add(new AddItem());
        this.actions.add(new ShowAllItems());
        this.actions.add(new EditItem());
        this.actions.add(new DeleteItem());
        this.actions.add(new FindById());
        this.actions.add(new FindByName());
        this.actions.add(new ExitProgram());
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

    /**
     * Show All Items
     */
    public class ShowAllItems implements UserAction {
        @Override
        public String key() {
            return StartUI.SHOW;
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            String title = "------------------------------ Show all requests -----------------------------";
            print(title, tracker.findAll());
        }

        @Override
        public String info() {
            return String.format("%s%s%n", key(), ".Show all items");
        }
    }

    /**
     * Edit request
     */
    public class EditItem implements UserAction {
        @Override
        public String key() {
            return StartUI.EDIT;
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

        @Override
        public String info() {
            return String.format("%s%s%n", key(), ".Edit item");
        }
    }

    /**
     * Delete request
     */
    public class DeleteItem implements UserAction {
        @Override
        public String key() {
            return StartUI.DELETE;
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

        @Override
        public String info() {
            return String.format("%s%s%n", key(), ".Delete item");
        }
    }

    /**
     * find request by id
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
                print(title, item);
            }
        }

        @Override
        public String info() {
            return String.format("%s%s%n", key(), ".Find item by id");
        }
    }

    /**
     * find request by name
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
                print(title, item);
            }
        }

        @Override
        public String info() {
            return String.format("%s%s%n", key(), ".Find item by name");
        }
    }

    /**
     * Exit the program
     */
    public class ExitProgram implements UserAction {
        @Override
        public String key() {
            return StartUI.EXIT;
        }

        @Override
        public void execute(Input input, Tracker tracker) {

        }

        @Override
        public String info() {
            return String.format("%s%s%n", key(), ".Exit program");
        }
    }


}
