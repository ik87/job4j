package ru.job4j.tracker;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class StubInput implements Input {
    /**
     * Storage actions. For example {"0", "name", "desc", "6"}
     */
    private final String[] value;

    /**
     * Every invoke this.ask(...) incriminate position
     */
    private int position;

    /**
     * Constructor
     *
     * @param value list command
     */
    public StubInput(final String... value) {
        this.value = value;
        position = 0;
    }

    @Override
    public String ask(String question) {
        return this.value[position++];
    }

    @Override
    public int ask(String question, int[] range) throws MenuOutException {
        int key = Integer.valueOf(this.ask(question));
        boolean exist = false;
        for (int value : range) {
            if (value == key) {
                exist = true;
                break;
            }
        }
        if (!exist) {
            throw new MenuOutException("Out of range");
        }
        return key;

    }
}
