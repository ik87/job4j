package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.List;
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
    private List<Item> items = new ArrayList<>();

    private static final Random RM = new Random();

    /**
     * Add new item
     *
     * @param item new item
     * @return added item
     */
    public Item add(Item item) {
        item.setId(generateId());
        items.add(item);
        return item;
    }

    /**
     * replace item
     *
     * @param id   id old item
     * @param item new item
     * @return condition executed operation. If success then true otherwise false
     */
    public boolean replace(String id, Item item) {


        boolean result = false;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(id)) {
                result = true;
                item.setId(id);
                items.set(i, item);
                break;
            }
        }

        return result;


    }

    /**
     * Delete item
     *
     * @param id id
     * @return condition executed operation. If success then true otherwise false
     */
    public boolean delete(String id) {


        boolean result = false;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(id)) {
                result = true;
                items.remove(i);
                break;
            }
        }
        return result;

    }

    /**
     * Find item by id
     *
     * @param id id item
     * @return found item
     */
    public Item findById(String id) {
        Item result = null;
        for (Item item : items) {
            if (item.getId().equals(id)) {
                result = item;
                break;
            }
        }
        return result;

    }

    /**
     * Find items by name
     *
     * @param name name item
     * @return array found items
     */
    public List<Item> findByName(String name) {
        List<Item> list = new ArrayList<>();
        for (Item item : items) {
            if (item.getName().equals(name)) {
                list.add(item);
            }
        }
        return list;

    }

    public List<Item> findAll() {
        return items;
    }

    /**
     * Generate unique id for request
     *
     * @return generated id
     */
    private String generateId() {

        return String.valueOf(System.currentTimeMillis() + RM.nextInt() * 13);
    }

}
