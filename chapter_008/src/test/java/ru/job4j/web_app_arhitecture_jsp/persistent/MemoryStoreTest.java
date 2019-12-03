package ru.job4j.web_app_arhitecture_jsp.persistent;

import org.junit.Test;
import ru.job4j.web_app_arhitecture_jsp.model.User;

import java.util.concurrent.CountDownLatch;


public class MemoryStoreTest {
    @Test
    public void whenAddTwoUsersInTheSameTimeThenOK() throws InterruptedException {
        Store store = MemoryStore.getInstance();
        CountDownLatch cd = new CountDownLatch(1);
        for (int i = 1; i < 5; i++) {
            new Run(store, cd, new User(0, String.valueOf(i), String.valueOf(i), String.valueOf(i), String.valueOf(i)));
        }
        cd.countDown();
        Thread.sleep(100);
        for (User user : store.findAll()) {
            System.out.println(user);
        }
    }

}

class Run extends Thread {
    private CountDownLatch cd;
    private Store store;
    private User user;

    Run(Store store, CountDownLatch cd, User user) {
        this.store = store;
        this.cd = cd;
        this.user = user;
        this.start();
    }

    @Override
    public void run() {
        try {
            cd.await();
            store.add(user);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}