package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
*Test
*
*@author Kosolapov Ilya (d_dexter@mail.ru)
*@version $ID$
*@since 0.1
*/
public class ArrayCharTest {
  
  @Test
  public void whenStartWithPrefixThenTrue() {
    ArrayChar word = new ArrayChar("Hello");
    boolean result = word.startWith("He");
    assertThat(result, is(true));
  }

  @Test
  public void whenNoStartWithPrefixThenFalse() {
    ArrayChar word = new ArrayChar("Hello");
    boolean result = word.startWith("Hi");
    assertThat(result, is(false));
  }
} 