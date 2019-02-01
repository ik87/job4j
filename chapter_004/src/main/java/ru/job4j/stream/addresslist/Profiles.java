package ru.job4j.stream.addresslist;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kosolapov Ilya(d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class Profiles {
    /**
     * Get list addresses from Profiles list
     * @param profiles list profiles
     * @return list addresses
     */
    public List<Address> collect(List<Profile> profiles) {
        return profiles.stream().map(Profile::getAddress).collect(Collectors.toList());
    }

    /**
     * Get unique and sorted by City list addresses from Profiles list
     * @param profiles list profiles
     * @return list addresses
     */
    public List<Address> uniqueSortedCollect(List<Profile> profiles) {
        return profiles
                .stream()
                .map(Profile::getAddress)
                .distinct()
                .sorted(Comparator.comparing(Address::getCity))
                .collect(Collectors.toList());
    }
}
