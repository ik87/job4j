package ru.job4j.tracker;

import ru.job4j.tracker.menu.*;

import java.util.ArrayList;
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

}
