package ru.job4j.tracker;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class StartUI {

    public static final String ADD = "0";
    public static final String SHOW = "1";
    public static final String EDIT = "2";
    public static final String DELETE = "3";
    public static final String FIND_BY_ID = "4";
    public static final String FIND_BY_NAME = "5";
    public static final String EXIT = "6";
    public final Input input;
    public final Tracker tracker;

    public StartUI(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    public void init() {
        MenuTracker menu = new MenuTracker(this.input, this.tracker);
        menu.fillActions();
        String key;
        int[] range = {0, 1, 2, 3, 4, 5, 6};
        do {
            System.out.println("Menu:");
            menu.show();
            key = String.valueOf(input.ask("Enter the menu item: ", range));
            menu.select(key);
        } while (!EXIT.equals(key));

    }

    /**
     * Start program
     *
     * @param args arguments list
     */
    public static void main(String[] args) {
        new StartUI(new ValidateInput(), new Tracker()).init();
    }
}
