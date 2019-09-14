package ru.job4j.email_sending_services;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Easy serves, Email notification with used  ExecutorServices
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public abstract class EmailNotification {
    ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    /**
     * Interrupt notification
     */
    public void close() {
        pool.shutdown();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send email to some User
     * @param user some user
     */
    public void emailTo(User user) {
        String subject = String.format("Notification %s to email %s", user.name, user.email);
        String body = String.format("Add a new event to %s", user.name, user.email);
        pool.submit(() -> send(subject, body, user.email));
    }

    abstract void send(String subject, String body, String email);
}


