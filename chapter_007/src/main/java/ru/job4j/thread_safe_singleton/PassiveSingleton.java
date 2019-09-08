package ru.job4j.thread_safe_singleton;

/**
 * Passive singleton, prefer used when system has costly resources
 * !Init occur in critical section! This pattern degree performance
 * Don't use it!
 */
public class PassiveSingleton {
    public static PassiveSingleton INSTANCE;

    private PassiveSingleton() {
    }

    public static synchronized PassiveSingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PassiveSingleton();
        }
        return INSTANCE;

    }

    public Item add(Item model) {
        return model;
    }

    public static void main(String[] args) {
        PassiveSingleton singleton = PassiveSingleton.getInstance();
    }
}
