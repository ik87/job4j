package ru.job4j.concurrency_unblocking;

import java.util.concurrent.ConcurrentHashMap;

public class CacheStorage {
    private final ConcurrentHashMap<Integer, Base> concurrentHashMap = new ConcurrentHashMap<>();
    private volatile int version = 0;
    public void add(Base model) {
            concurrentHashMap.put(model.id, model);
    }

    public void update(Base model) {
      //  concurrentHashMap.computeIfPresent()
    }

    public void delete(Base model) {
        concurrentHashMap.remove(model.id);
    }
}
