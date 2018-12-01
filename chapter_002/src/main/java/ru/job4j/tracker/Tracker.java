package ru.job4j.tracker;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class Tracker {
    /**
     * Array for requests
     */
    private Item[] items = new Item[100];
    /**
     * ref to cell on new request
     */
    private int position = 0;
    private static final Random RM = new Random();
    /**
     * Add new item
     * @param item new item
     * @return added item
     */
    public Item add(Item item) {
        Item result = null;
        if (position < items.length) {
            item.setId(generateId());
            items[position++] = item;
            result = item;
        }
        return result;
    }

    /**
     * replace item
     * @param id id old item
     * @param item new item
     */
    public void replace(String id, Item item) {
        for (int i = 0; i < items.length; i++) {
            if (items[i].getId().equals(id)) {
                items[i] = item;
                break;
            }
        }
    }

    /**
     * Delete item
     * @param id
     */
    public void delete(String id) {
        for (int i = 0; i < items.length; i++) {
            if (items[i].getId().equals(id)) {
                items[i] = items[position - 1];
                items[position - 1] = null;
                position--;
                break;
            }
        }
    }

    /**
     * Find item by id
     * @param id id item
     * @return found item
     */
    public Item findById(String id) {
        Item result = null;
        for (Item item : items) {
            if (item != null && item.getId().equals(id)) {
                result = item;
                break;
            }
        }
        return result;
    }

    /**
     * Find items by name
     * @param name name item
     * @return array found items
     */
    public Item[] findByName(String name) {
        int count = 0;
        Item[] result = new Item[position];
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null && items[i].getName().equals(name)) {
                result[count++] = items[i];
            }
        }

        return Arrays.copyOf(result, count);
    }

    public Item[] findAll() {
        return Arrays.copyOf(items, position);
    }

    /**
     * Generate unique id for request
     * @return generated id
     */
    private String generateId() {

        return String.valueOf(System.currentTimeMillis() + RM.nextInt() * 13);
    }

}
