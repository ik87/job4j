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
        address = new ArrayList<>();
        counter = new Profiles();
        address.add(new Address("Скопин", "Пырьева", 56, 30));
        address.add(new Address("Покровка", "Кропоткинская", 97, 144));
        address.add(new Address("Ольовка", "Кремлевский Проезд", 25, 196));
        address.add(new Address("Чунский", "Златоустовская", 49, 16));
        address.add(new Address("Скопин", "Пырьева", 56, 30));
        address.add(new Address("Покровка", "Кропоткинская", 97, 144));
        address.add(new Address("Алеевка", " Восточный 3-й Переулок", 15, 3));
        address.add(new Address("Шатки", "Каретный Б. Переулок", 57, 143));
        address.forEach(x->profiles.add(new Profile(x)));
    }

    @Test
    public void whenGetAddressThenListAddress() {
        List<Address> list = counter.collect(profiles);
        assertThat(list, is(address));
    }

    @Test
    public void whenGetAddressThenUniqueSortedListAddress() {
        List<Address> list = counter.uniqueSortedCollect(profiles);
        List<Address> expected = new ArrayList<>();
        expected.add(address.get(6));
        expected.add(address.get(2));
        expected.add(address.get(1));
        expected.add(address.get(0));
        expected.add(address.get(3));
        expected.add(address.get(7));
        assertThat(list, is(expected));
    }
}
