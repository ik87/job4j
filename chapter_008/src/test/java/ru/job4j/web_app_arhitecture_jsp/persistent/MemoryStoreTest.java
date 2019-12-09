package ru.job4j.web_app_arhitecture_jsp.persistent;

import org.junit.Assert;
import org.junit.Test;
import ru.job4j.web_app_arhitecture_jsp.model.User;

import java.util.concurrent.CyclicBarrier;

import static org.hamcrest.core.Is.is;



public class MemoryStoreTest {
    @Test
    public void whenAddTwoUsersInTheSameTimeThenOK() throws Exception {
        Store store = MemoryStore.getInstance();
        int parties = 10000;
        CyclicBarrier cbStart = new CyclicBarrier(parties);
        CyclicBarrier cbEnd = new CyclicBarrier(parties + 1);

        for (int i = 1; i < parties + 1; i++) {
            new Run(store, cbStart, cbEnd, new User(0, String.valueOf(i), String.valueOf(i), String.valueOf(i), String.valueOf(i)));
        }

        cbEnd.await();

        int expect = 0;
        int result = 0;

        for (User user : store.findAll()) {
            expect += user.getId();
            result += Integer.valueOf(user.getName());
        }

        Assert.assertThat(expect, is(result));
        Assert.assertNotEquals(expect, is(0));
        Assert.assertNotEquals(result, is(0));
    }

}

class Run extends Thread {
    private CyclicBarrier cdStart;
    private CyclicBarrier cdEnd;
    private Store store;
    private User user;

    Run(Store store, CyclicBarrier cdStart, CyclicBarrier cdEnd, User user) {
        this.store = store;
        this.cdStart = cdStart;
        this.cdEnd = cdEnd;
        this.user = user;
        this.start();
    }

    @Override
    public void run() {
        try {
            cdStart.await();
            store.add(user);
            cdEnd.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}