package ru.job4j.tracker;

import java.util.function.Consumer;

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
    private boolean exit = false;
    private final Consumer<String> output;

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    public StartUI(Input input, Tracker tracker, Consumer<String> output) {
        this.input = input;
        this.tracker = tracker;
        this.output = output;
    }

    public void init() {
        MenuTracker menu = new MenuTracker(this.input, this.tracker, this.output);
        menu.setStartui(this);
        menu.fillActions();

        String key;

        int[] range = {0, 1, 2, 3, 4, 5, 6};
        do {
            output.accept(String.format("Menu:%n"));
            menu.show();
            key = String.valueOf(input.ask("Enter the menu item: ", range));
            menu.select(key);
        } while (!exit);

    }

    /**
     * Start program
     *
     * @param args arguments list
     */
    public static void main(String[] args) {
        new StartUI(new ValidateInput(new ConsoleInput()), new Tracker(), System.out::print).init();
    }
}
