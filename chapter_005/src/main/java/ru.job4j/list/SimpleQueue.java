package ru.job4j.list;

/**
 * This is the simple queue (FIFO) is based on the two simple stacks
 * @param <E>
 */
public class SimpleQueue<E> {
    private SimpleStack<E> aStack = new SimpleStack<>();
    private SimpleStack<E> bStack = new SimpleStack<>();

    /**
     * an element put on the top one stack after put existing elements
     * @param element some element
     */
    public void push(E element) {
        swap(aStack, bStack);
        aStack.push(element);
    }

    /**
     * get a last element
     * @return top element
     */
    public E pop() {
        swap(bStack, aStack);
        return bStack.pop();
    }

    /**
     * swap elements
     * @param a first stack
     * @param b second stack
     */
    private void swap(SimpleStack<E> a, SimpleStack<E> b) {
        while (b.getCount() > 0) {
            a.push(b.pop());
        }
    }

    /**
     * get capacity
     * @return size
     */
    public int getCount() {
        return aStack.getCount() + bStack.getCount();
    }
}
