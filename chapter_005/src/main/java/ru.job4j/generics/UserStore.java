package ru.job4j.generics;

/**
 * storage for User
 *
 * @author Koslapov Ilya (d_dexter@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class UserStore extends GeneralStore {
    public UserStore(int size) {
        super(new SimpleArray<User>(size));
    }
}
