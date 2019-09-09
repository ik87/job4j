package ru.job4j.concurrency_unblocking;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.atomic.AtomicReference;


public class CacheStorageTest {
    @Test
    public void whenThrowException() throws InterruptedException {
        CacheStorage cacheStorage = new CacheStorage();
        cacheStorage.add(new Base(1, 1));

        AtomicReference<Exception> ex = new AtomicReference<>();

        Thread thread = new Thread(
                () -> {
                    try {
                        cacheStorage.update(new Base(1, 1));
                    } catch (Exception e) {
                        ex.set(e);
                    }
                }
        );
        thread.start();

        try {
            cacheStorage.update(new Base(1, 1));
        } catch (Exception e) {
            ex.set(e);
        }

        thread.join();
        assertThat(ex.get().getMessage(), is("OptimisticException"));
    }

    @Test
    public void whenDeleteElementThenEmpty() {
        CacheStorage cacheStorage = new CacheStorage();
        cacheStorage.add(new Base(1, 1));
        cacheStorage.delete(new Base(1, 1));
        assertTrue(cacheStorage.isEmpty());
    }
}