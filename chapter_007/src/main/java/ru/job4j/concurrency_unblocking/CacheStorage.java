package ru.job4j.concurrency_unblocking;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Easy example No-blocking concurrency
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class CacheStorage {
    private final ConcurrentHashMap<Integer, Base> concurrentHashMap = new ConcurrentHashMap<>();

    /**
     * Add element
     *
     * @param model model type Base
     */
    public void add(Base model) {
        concurrentHashMap.put(model.id, model);
    }

    /**
     * Change existence model from collection.
     * Here no-blocking model is used
     *
     * @param model model type Base
     */
    public void update(Base model) {
        concurrentHashMap.computeIfPresent(model.id, (k, v) -> {
            if (v.version != model.version) {
                throw new OptimisticException("OptimisticException");
            }
            model.version++;
            return model;
        });
    }

    /**
     * Delete existence model from collection
     *
     * @param model model type Base
     */
    public void delete(Base model) {
        concurrentHashMap.remove(model.id);
    }

    /**
     * Checking collection on empty
     */
    public boolean isEmpty() {
        return concurrentHashMap.isEmpty();
    }
}

