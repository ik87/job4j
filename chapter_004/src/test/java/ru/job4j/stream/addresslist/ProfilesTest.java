package ru.job4j.stream.addresslist;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

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
    Address[] rawAddress;
    private Profiles data;

    @Before
    public void init() {
        data = new Profiles();
        rawAddress = new Address[]{
                new Address("Moscow"),
                new Address("St. Petersburg"),
                new Address("Nizhny Novgorod"),
                new Address("Bryansk"),
                new Address("Tula"),
                new Address("Oslo")
        };
        profiles = new ArrayList<>();

        for (Address a : rawAddress) {
            profiles.add(new Profile(a));
        }
    }

    @Test
    public void whenGetAddressThenListAddress() {
        List<Address> address = data.collect(profiles);

        assertThat(address, is(Arrays.asList(rawAddress)));
    }
}
