package ru.job4j.list;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.Test;

public class CheckLinkedListTest {

    @Test
    public void checkCollision() {
        CheckLinkedList ch = new CheckLinkedList();
        Node first = new Node(1);
        Node two = new Node(2);
        Node third = new Node(3);
        Node four = new Node(4);
        Node five = new Node(5);

        first.next = two;
        two.next = third;
        third.next = four;
        four.next = five;
        five.next = four;

        assertThat(ch.hasCycle(first), is(true));
    }
}