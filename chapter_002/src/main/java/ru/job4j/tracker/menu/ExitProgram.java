package ru.job4j.tracker.menu;

import ru.job4j.tracker.*;

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
