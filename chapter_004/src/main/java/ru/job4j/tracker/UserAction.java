package ru.job4j.tracker;

public interface UserAction {



    /**
     * Get Key
     */
    String key();

    /**
     * StreamAPI method
     */
    void execute(Input input, Tracker tracker);

    /**
     * Return information about action
     * @return info
     */
    String info();
}
