package ru.job4j.list;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class CheckLinkedList {
    /**
     * Check collisions cycle
     *
     * @param first some node
     * @return
     */
    public boolean hasCycle(Node first) {
        Node turtle = first;
        Node hare = first;
        boolean result = false;

        while (hare.next != null) {
            turtle = turtle.next;
            if (hare.next != null) {
                hare = hare.next.next;
            }
            if (turtle.value.equals(hare.value)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
