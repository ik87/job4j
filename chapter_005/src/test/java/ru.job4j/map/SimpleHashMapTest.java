package ru.job4j.map;

import static org.hamcrest.core.Is.is;

import org.junit.Before;
import org.junit.Test;


import java.util.*;
import java.util.function.Function;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.*;

/**
 * This is test try to compare work Map and SimpleMap
 * result work must be same
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @since 27.03.2019
 * @version 0.1
 */
public class SimpleHashMapTest {

    private Cat[] cats;
    private Cat[] catsHash;
    private Cat[] catsEquals;
    private Cat[] catsHashEquals;
    private SimpleMap<Cat, Integer> simpleHashMap;
    private Map<Cat, Integer> hashMap;


    /**
     *
     * These classes for check SimpleMap
     *
     * Classes:
     * Cat - has default hashCode and equals
     * HashCode - extends Cat and overrides hashCode
     * Equals - extends Cat and overrides equals
     * HashCodeEquals - extends Cat and overrides hashCode and equals
     *
     */
    // ****************************** start block **************************************
    class Cat {
        protected String name;

        public Cat(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    class HashCode extends Cat {
        public HashCode(String name) {
            super(name);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(name);
        }
    }

    class Equals extends Cat {
        public Equals(String name) {
            super(name);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Cat cat = (Cat) o;
            return Objects.equals(name, cat.name);
        }

        @Override
        public int hashCode() {
            return System.identityHashCode(this);
        }
    }

    class HashCodeEquals extends Cat {
        public HashCodeEquals(String name) {
            super(name);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Cat cat = (Cat) o;
            return Objects.equals(name, cat.name);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(name);
        }
    }

    class Pair {
        private Cat cat;
        private Integer num;

        public Pair(Cat cat, Integer num) {
            this.cat = cat;
            this.num = num;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Pair pair = (Pair) o;
            return Objects.equals(cat, pair.cat) && Objects.equals(num, pair.num);
        }

        @Override
        public int hashCode() {
            return Objects.hash(cat, num);
        }
    }
    // ****************************** end block **************************************

    /**
     * Checker classes through compare Map and SimpleMap
     */
    private void assertFromIterator() {
        List<Pair> result = new ArrayList<>();
        List<Pair> expected = new ArrayList<>();

        for (Map.Entry<Cat, Integer> p : hashMap.entrySet()) {
            expected.add(new Pair(p.getKey(), p.getValue()));
        }

        for (SimpleMap.Entry<Cat, Integer> p : simpleHashMap) {
            result.add(new Pair(p.getKey(), p.getValue()));
        }

        assertThat(result, containsInAnyOrder(expected.toArray()));
    }

    /**
     * Fill array cats different kind of Cat
     * with hashCode or equals or hashCode & equals or
     * neither one nor the other
     *
     * @param names array with cats name
     * @param fun   get constructor the kinds of Cat
     * @return some Cat array
     */
    private Cat[] maker(String[] names, Function<String, Cat> fun) {
        Cat[] cats = new Cat[names.length];
        for (int i = 0; i < names.length; i++) {
            cats[i] = fun.apply(names[i]);
        }
        return cats;
    }

    @Before
    public void init() {
        String[] names = {null, "Oliver", "Max", "Charlie", "Max", "Milo", "Jack",
                null, "Charlie", "Simba", null, "Leo", "Milo", "Milo", null,
                null, "Oliver", "Max", "Charlie", "Max", "Milo", "Jack", null, "Charlie",
                "Simba", null, "Leo", "Milo", "Milo", null, "Charlie",
                "Simba", null, "Leo", "Milo", "Milo", null};

        catsHash = maker(names, HashCode::new);
        catsEquals = maker(names, Equals::new);
        catsHashEquals = maker(names, HashCodeEquals::new);
        cats = maker(names, Cat::new);

        simpleHashMap = new SimpleHashMap<>();
        hashMap = new HashMap<>();

    }

    @Test
    public void whenPutObjectWithoutEqualsAndHashCodeThenSame() {
        //fill Map and SimpleMap
        for (int i = 0; i < cats.length; i++) {
            hashMap.put(cats[i], i);
            simpleHashMap.put(cats[i], i);
        }
        assertFromIterator();
    }

    @Test
    public void whenPutObjectHasOnlyEqualsThenSame() {
        //fill Map and SimpleMap
        for (int i = 0; i < catsEquals.length; i++) {
            hashMap.put(catsEquals[i], i);
            simpleHashMap.put(catsEquals[i], i);
        }
        assertFromIterator();
    }

    @Test
    public void whenPutObjectHasOnlyHashCodeThenSame() {
        //fill Map and SimpleMap
        for (int i = 0; i < catsHash.length; i++) {
            hashMap.put(catsHash[i], i);
            simpleHashMap.put(catsHash[i], i);
        }
        assertFromIterator();
    }

    @Test
    public void whenPutObjectWithEqualsAndHashCodeThenSame() {
        //fill Map and SimpleMap
        for (int i = 0; i < catsHashEquals.length; i++) {
            hashMap.put(catsHashEquals[i], i);
            simpleHashMap.put(catsHashEquals[i], i);
        }
        assertFromIterator();
    }

    @Test
    public void whenPutSameKeysSomeTimesThenGetValueFromLastOne() {
        SimpleMap<String, Integer> map = new SimpleHashMap<>();
        for (int i = 0; i < 500; i++) {
            map.put("Boom", i);
        }
        assertThat(map.get("Boom"), is(499));
    }

    @Test
    public void whenNotFoundThenNull() {
        SimpleMap<String, Integer> map = new SimpleHashMap<>();
        assertNull(map.get("Boom"));
        assertNull(map.remove("Boom"));
    }

    @Test
    public void whenRemoveThenEmpty() {
        List<Pair> result = new ArrayList<>();
        List<Pair> expected = new ArrayList<>();

        //fill Map and SimpleMap
        for (int i = 0; i < catsHashEquals.length; i++) {
            hashMap.put(catsHashEquals[i], i);
            simpleHashMap.put(catsHashEquals[i], i);
        }


        for (var e : hashMap.entrySet()) {
            //remove data and populate lists
            result.add(new Pair(e.getKey(), simpleHashMap.remove(e.getKey())));
            //fill list for equality test
            expected.add(new Pair(e.getKey(), e.getValue()));
        }
        //equality test between two list's
        assertThat(result, containsInAnyOrder(expected.toArray()));

        //after remove element it must be empty
        assertTrue(simpleHashMap.isEmpty());

    }
}