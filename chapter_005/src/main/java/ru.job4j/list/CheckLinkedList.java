package ru.job4j.list;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class CheckLinkedList {
    /**
     * Check node collision
     * @param first some node
     * @return
     */
    public boolean hasCycle(Node first) {
        int firstHash = first.hashCode();
        boolean result = false;
        while (first != null) {
            first = first.next;
            if (firstHash == first.hashCode()) {
                result = true;
                break;
            }
        }
        return result;
    }
}
