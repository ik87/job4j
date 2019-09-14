package ru.job4j.email_sending_services;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;

public class EmailNotificationTest {
    @Test
    public void whenSendEmailsThenGetThem() {
        List<User> users = List.of(
                new User("Jon", "jon@mail.com"),
                new User("Pol", "pol@mail.com")
        );
        List<String> expectedEmails = new ArrayList<>();
        EmailNotification emailNotification = new EmailNotification() {
            @Override
            void send(String subject, String body, String email) {
                expectedEmails.add(email);
            }
        };

        emailNotification.emailTo(users.get(0));
        emailNotification.emailTo(users.get(1));
        emailNotification.close();

        Assert.assertThat(users.get(0).email, is(expectedEmails.get(0)));
        Assert.assertThat(users.get(1).email, is(expectedEmails.get(1)));

    }

}