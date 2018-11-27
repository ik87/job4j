package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
*Test
*
*@autor Kosolapov Ilya (d_dexter@mail.ru)
*@version $ID$
*@since 0.1
*/
public class ArrayDuplicateTest {
	
	@Test
	public void whenRemoveDuplicatesThenArrayWithoutDuplicate() {
		ArrayDuplicate data = new ArrayDuplicate();
		String[] input = {"Hello", "World", "Super", "World"};
		String[] result = data.remove(input);
		String[] expect = {"Hello", "World", "Super"};
		assertThat(result, is(expect));
	}
}