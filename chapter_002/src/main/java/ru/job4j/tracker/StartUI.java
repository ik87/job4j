package ru.job4j.tracker;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class StartUI {

    private static final String ADD = "0";
    private static final String SHOW = "1";
    private static final String EDIT = "2";
    private static final String DELETE = "3";
    private static final String FIND_BY_ID = "4";
    private static final String FIND_BY_NAME = "5";
    private static final String EXIT = "6";
    private final Input input;
    private final Tracker tracker;

    public StartUI(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    public void init() {
        boolean exit = false;
        while (!exit) {
            this.showMenu();
            String answer = this.input.ask("Enter the menu item: ");
            if (ADD.equals(answer)) {
                this.createItem();
            } else if (SHOW.equals(answer)) {
                this.showAllItems();
            } else if (EDIT.equals(answer)) {
                this.editItem();
            } else if (DELETE.equals(answer)) {
                this.deleteItem();
            } else if (FIND_BY_ID.equals(answer)) {
                this.findById();
            } else if (FIND_BY_NAME.equals(answer)) {
                this.findByName();
            } else if (EXIT.equals(answer)) {
                exit = true;
            }
        }
    }

    private void showMenu() {
        System.out.println("Menu:");
        System.out.println("0.Add new item");
        System.out.println("1.Show all items");
        System.out.println("2.Edit item");
        System.out.println("3.Delete item");
        System.out.println("4.Find item by id");
        System.out.println("5.Find item by name");
        System.out.println("6.Exit program");
    }

    /**
     * Create Item
     */
    private void createItem() {
        System.out.println("----------------- Add new request -----------------");
        String name = input.ask("Enter request name");
        String desc = input.ask("Enter request description");
        Item item = new Item(name, desc);
        this.tracker.add(item);
        System.out.println("-------------- Added new request with gitId: " + item.getId());
    }

    /**
     * Show items by array
     * @param title title name
     * @param items array of items
     */
    private void show(String title, Item[] items) {
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
     * Show All Items
     */
    private void showAllItems() {
        String title = "------------------------------ Show all requests -----------------------------";
        show(title, tracker.findAll());
    }
    /**
     * Edit request
     */
    private void editItem() {
        String title = "------------------------------ Edit some request ------------------------------";
        show(title, tracker.findAll());
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
    /**
     *Delete item
     */
     private void deleteItem() {
         String title = "----------------------------- Delete some request -----------------------------";
         show(title, tracker.findAll());
         String id = input.ask("Enter id request");
         if (tracker.delete(id)) {
             System.out.printf("Request where id %s has deleted%n", id);
         } else {
             System.out.printf("Request where id %s hasn't found%n", id);
         }
     }

    /**
     * find request by id
     */
    private void findById() {
        String title = "----------------------------- Find by Id -------------------------------------";
        System.out.println();
        String id = input.ask("Enter id request");
        Item[] item = {tracker.findById(id)};
        if (item[0] == null) {
            System.out.printf("Request where id %s hasn't found%n", id);
        } else {
            show(title, item);
        }
    }

    /**
     * Find by name
     */
    private void findByName() {
        String title = "-------------------------------- Find by name --------------------------------";
        System.out.println();
        String name = input.ask("Enter name request");
        Item[] item = tracker.findByName(name);
        if (item.length == 0) {
            System.out.printf("Request where name %s hasn't found%n", name);
        } else {
            show(title, item);
        }
    }
    /**
     * Convert long to date
     * @param date date
     * @return formated date
     */
    private String longToDate(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd,MM,yy");
        return sdf.format(new Date(date));
    }
    /**
     * Start program
     * @param args arguments list
     */
    public static void main(String[] args) {
        new StartUI(new ConsoleInput(), new Tracker()).init();
    }
}
