package ru.job4j.stream.addresslist;

import java.util.List;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Kosolapov Ilya(d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class ProfilesTest {
    private List<Profile> profiles;
    private List<Address> address;
    private Profiles counter;

    @Before
    public void init() {
        profiles = new ArrayList<>();

        counter = new Profiles();
        address = List.of(
                new Address("Скопин", "Пырьева", 56, 30),
                new Address("Покровка", "Кропоткинская", 97, 144),
                new Address("Ольовка", "Кремлевский Проезд", 25, 196),
                new Address("Чунский", "Златоустовская", 49, 16),
                new Address("Скопин", "Пырьева", 56, 30),
                new Address("Покровка", "Кропоткинская", 97, 144),
                new Address("Алеевка", " Восточный 3-й Переулок", 15, 3),
                new Address("Шатки", "Каретный Б. Переулок", 57, 143));

        address.forEach(x -> profiles.add(new Profile(x)));
    }

    @Test
    public void whenGetAddressThenListAddress() {
        List<Address> list = counter.collect(profiles);
        assertThat(list, is(address));
    }

    @Test
    public void whenGetAddressThenUniqueSortedListAddress() {
        List<Address> list = counter.uniqueSortedCollect(profiles);
        List<Address> expected = List.of(
                address.get(6),
                address.get(2),
                address.get(1),
                address.get(0),
                address.get(3),
                address.get(7));
        assertThat(list, is(expected));
    }
}
