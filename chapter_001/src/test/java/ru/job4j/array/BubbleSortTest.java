package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/*
*Test
*
*@author Kosolapov Ilya (d_dexter@mail.ru)
*@version $ID$
*@since 0.1
*/
public class BubbleSortTest {
  
  @Test
  public void whenSortArrayWithTenElementsThenSortedArray() {
    BubbleSort bubble = new BubbleSort();
    int[] input = new int[]{1, 5, 4, 2, 3, 1, 7, 8, 0, 5};
    int[] result = bubble.sort(input);
    int[] expect = new int[]{0, 1, 1, 2, 3, 4, 5, 5, 7, 8};
    assertThat(result, is(expect));
  }
}