package ru.job4j.list;

/**
 * This is the simple queue (FIFO) is based on the two simple stacks
 *
 * @param <E>
 */
public class SimpleQueue<E> {
    private SimpleStack<E> in = new SimpleStack<>();
    private SimpleStack<E> out = new SimpleStack<>();

    /**
     * an element put on the top one stack after put existing elements
     *
     * @param element some element
     */
    public void push(E element) {
        in.push(element);
    }

    /**
     * get a last element
     *
     * @return top element
     */
    public E pop() {
        if (out.isEmpty()) {
            while (!in.isEmpty()) {
                out.push(in.pop());
            }
        }
        return out.pop();
    }

    /**
     * get capacity
     * @return size
     */
    public int count() {
        return in.count() + out.count();
    }

}
