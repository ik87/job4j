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
public class MatrixTest {
	
	@Test
	public void when2on2() {
		Matrix matrix = new Matrix();
		int[][] table = matrix.multiple(2);
		int[][] except = {
			{1, 2},
			{2, 4}
		};
		assertThat(table, is(except));
	}
	
	@Test
	public void when5on5() {
		Matrix matrix = new Matrix();
		int[][] table = matrix.multiple(5);
		int[][] except = {
			{1, 2, 3, 4, 5},
			{2, 4, 6, 8, 10},
			{3, 6, 9, 12, 15},
			{4, 8, 12, 16, 20},
			{5, 10, 15, 20, 25}
		};
		assertThat(table, is(except));
	}
}