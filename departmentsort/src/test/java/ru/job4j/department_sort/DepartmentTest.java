package ru.job4j.department_sort;

import org.hamcrest.core.Is;
import org.junit.Test;

import java.util.Comparator;
import java.util.StringJoiner;

import static org.junit.Assert.*;

public class DepartmentTest {

    @Test
    public void whenDeportmentNormalSortThenSorted() {
        String[] str = {
                "K1\\SK1",
                "K1\\SK1\\SSK1",
                "K1\\SK1\\SSK2",
                "K1\\SK2",
                "K2",
                "K2\\SK1",
                "K2\\SK1\\SSK1",
                "K2\\SK1\\SSK2"
        };
        Department dep = new Department(str);
        assertThat(dep.toString(), Is.is(
                new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                        .add("K1")
                        .add("K1\\SK1")
                        .add("K1\\SK1\\SSK1")
                        .add("K1\\SK1\\SSK2")
                        .add("K1\\SK2")
                        .add("K2")
                        .add("K2\\SK1")
                        .add("K2\\SK1\\SSK1")
                        .add("K2\\SK1\\SSK2")
                        .toString()
        ));
    }

    @Test
    public void whenDeportmentReverseSortThenSorted() {
        String[] str = {
                "K1\\SK1",
                "K1\\SK1\\SSK1",
                "K1\\SK1\\SSK2",
                "K1\\SK2",
                "K2",
                "K2\\SK1",
                "K2\\SK1\\SSK1",
                "K2\\SK1\\SSK2"
        };
        Department dep = new Department(str);
        dep.sort(Comparator.reverseOrder());
        assertThat(dep.toString(), Is.is(
                new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                        .add("K2")
                        .add("K2\\SK1")
                        .add("K2\\SK1\\SSK1")
                        .add("K2\\SK1\\SSK2")
                        .add("K1")
                        .add("K1\\SK1")
                        .add("K1\\SK1\\SSK1")
                        .add("K1\\SK1\\SSK2")
                        .add("K1\\SK2")
                        .toString()
        ));
    }

}