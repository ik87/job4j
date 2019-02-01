package ru.job4j.stream.addresslist;

import java.util.Objects;

/**
 * @author Kosolapov Ilya(d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class Address {
    private String street;
    private String city;
    private int home;
    private int apartment;

    public Address(String city, String street, int home, int apartment) {
        this.street = street;
        this.city = city;
        this.home = home;
        this.apartment = apartment;
    }


    public String getCity() {
        return city;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address) o;
        return home == address.home
                && apartment == address.apartment
                && Objects.equals(street, address.street)
                && Objects.equals(city, address.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, city, home, apartment);
    }
}
