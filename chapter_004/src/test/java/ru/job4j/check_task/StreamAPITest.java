package ru.job4j.check_task;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class StreamAPITest {
    @Test
    public void whenPutArrayTheGetValue() {
        int sum = StreamAPI.evenSquaredSum(new int[]{1, 2, 3, 4, 5, 6});
        assertThat(sum, is(56));
    }

}