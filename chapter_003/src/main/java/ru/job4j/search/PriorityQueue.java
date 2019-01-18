package ru.job4j.search;

import java.util.Iterator;
import java.util.LinkedList;

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

        Iterator<Task> pnt = tasks.iterator();
        int i = 0;
        for (; pnt.hasNext(); i++) {
            if (pnt.next().getPriority() > task.getPriority()) {
                break;
            }
        }

        tasks.add(i, task);

    }


    public Task take() {
        return this.tasks.poll();
    }
}
