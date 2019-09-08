package ru.job4j.thread_safe_singleton;

/**
 * Passive singleton, prefer used when system has costly resources
 */
public class PassiveDoubleCheckedSingleton {
    private static volatile PassiveDoubleCheckedSingleton INSTANCE;

    private PassiveDoubleCheckedSingleton() {
    }

    public static PassiveDoubleCheckedSingleton getInstance() {
        if (INSTANCE == null) {
            synchronized (PassiveDoubleCheckedSingleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PassiveDoubleCheckedSingleton();
                }
            }
        }
        return INSTANCE;
    }

    public Item add(Item model) {
        return model;
    }

    public static void main(String[] args) {
        PassiveDoubleCheckedSingleton tracker =
                PassiveDoubleCheckedSingleton.getInstance();
    }
}
