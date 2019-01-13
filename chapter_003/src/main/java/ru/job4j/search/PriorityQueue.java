package ru.job4j.search;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class PriorityQueue {
    private LinkedList<Task> tasks = new LinkedList<>();

    /**
     * Method musts insert element in to desired position
     * Position is detecting by priority field
     * For insert use add(int index, E value)
     *
     * @param task task
     */
    public void put(Task task) {
        ListIterator<Task> iterator = tasks.listIterator();
        if (tasks.isEmpty()) {
            tasks.add(task);
        } else {
            while (iterator.hasNext()) {
                if (iterator.next().getPriority() > task.getPriority()) {
                    iterator.previous();
                    iterator.add(task);
                } else {
                    iterator.add(task);
                }
            }
        }
    }

    public Task take() {
        return this.tasks.poll();
    }
}
