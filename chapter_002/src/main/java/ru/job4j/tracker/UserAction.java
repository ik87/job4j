package ru.job4j.tracker;

public interface UserAction {



    /**
     * Get Key
     */
    String key();

    /**
     * Main method
     */
    void execute(Input input, ITracker tracker);

    /**
     * Return information about action
     * @return info
     */
    String info();
}
