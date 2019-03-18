package ru.job4j.generics;

/**
 * storage for Role
 *
 * @author Koslapov Ilya (d_dexter@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class RoleStore extends GeneralStore {
    public RoleStore(int size) {
        super(new SimpleArray<Role>(size));
    }
}
