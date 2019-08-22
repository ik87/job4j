package ru.job4j.user_storage;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class UserStorageTest {
    private abstract class ThreadStorage extends Thread {

        UserStorage storage;

        private ThreadStorage(final UserStorage storage) {
            this.storage = storage;
        }
    }

    @Test
    public void whenAddAndTransferThenSuccess() throws InterruptedException {
        UserStorage storage = new UserStorage();

        Thread threadA = new ThreadStorage(storage) {
            @Override
            public void run() {
                storage.add(new User(10, 100));
            }
        };
        Thread threadB = new ThreadStorage(storage) {
            @Override
            public void run() {
                storage.add(new User(20, 200));
            }
        };
        threadA.start();
        threadB.start();
        threadA.join();
        threadB.join();

        storage.transfer(10, 20, 100);

        assertThat(storage.getUser(20).getAmount(), is(300));
    }

    @Test
    public void whenDeleteThenSuccess() throws InterruptedException {
        UserStorage storage = new UserStorage();
        storage.add(new User(10, 100));

        Thread threadA = new ThreadStorage(storage) {
            @Override
            public void run() {
                storage.delete(new User(10, 100));
            }
        };
        Thread threadB = new ThreadStorage(storage) {
            @Override
            public void run() {
                storage.delete(new User(10, 100));
            }
        };
        threadA.start();
        threadB.start();
        threadA.join();
        threadB.join();

        assertNull(storage.getUser(10));

    }

    @Test
    public void whenUpdateThenSuccess() throws InterruptedException {
        UserStorage storage = new UserStorage();
        User user = new User(10, 100);

        storage.add(user);

        Thread thread = new ThreadStorage(storage) {
            @Override
            public void run() {
                storage.update(new User(10, 200));
            }
        };
        thread.start();
        thread.join();

        assertThat(storage.getUser(10).getAmount(), is(200));

    }
}