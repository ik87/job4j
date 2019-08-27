package ru.job4j.user_storage;

import java.util.Objects;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class User {
    private int id;
    private int amount;

    User(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    void setAmount(int amount) {
        this.amount = amount;
    }

    int getId() {
        return id;
    }

    int getAmount() {
        return amount;
    }
}
