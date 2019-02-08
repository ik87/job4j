package ru.job4j.var_search;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PhoneDictionaryTest {
    @Test
    public void whenFindByName() {
        var phones = new PhoneDictionary();
        phones.add(new Person("Petr", "Arsentev", "5434872", "Bryansk"));
        var persons = phones.find("Petr");
        assertThat(persons.iterator().next().getSurname(), is("Arsentev"));
    }

    @Test
    public void whenFindByShortPhone() {
        var phones = new PhoneDictionary();
        phones.add(new Person("Petr", "Arsentev", "5434872", "Bryansk"));
        var persons = phones.find("543");
        assertThat(persons.iterator().next().getSurname(), is("Arsentev"));
    }


}
