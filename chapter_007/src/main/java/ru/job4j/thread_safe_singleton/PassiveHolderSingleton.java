package ru.job4j.thread_safe_singleton;
/**
 * Passive singleton, prefer used when system has costly resources
 * It recommend use
 */
public class PassiveHolderSingleton {
    private PassiveHolderSingleton() {
    }

    public static PassiveHolderSingleton getInstance() {
        return Holder.INSTANCE;
    }

    public Item add(Item model) {
        return model;
    }

    private static final class Holder {
        private static final PassiveHolderSingleton INSTANCE = new PassiveHolderSingleton();
    }

    public static void main(String[] args) {
        PassiveHolderSingleton singleton = PassiveHolderSingleton.getInstance();
    }
}
