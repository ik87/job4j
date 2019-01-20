package ru.job4j.tracker;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public abstract class BaseAction implements UserAction {
    private final String key;
    private final String name;

    protected BaseAction(String key, String name) {
        this.key = key;
        this.name = name;
    }
    @Override
    public String key() {
        return this.key;
    }

    @Override
    public String info() {
        return String.format("%s.%s%n", this.key, this.name);
    }
}
