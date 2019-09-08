package ru.job4j.thread_safe_singleton;

/**
 * Active singleton, prefer used when need init database and cache
 */
public class ActiveSingleton {
    private static final ActiveSingleton INSTANCE = new ActiveSingleton();

    private ActiveSingleton() {
    }

    public static ActiveSingleton getInstance() {
        return INSTANCE;
    }

    public Item add(Item model) {
        return model;
    }
    public static void main(String[] args) {
        ActiveSingleton singleton = ActiveSingleton.getInstance();
    }
}
