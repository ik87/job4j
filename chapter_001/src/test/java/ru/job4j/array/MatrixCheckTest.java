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
public class MatrixCheckTest {
	
	@Test
	public void when2on2ThenTrue() {
		MatrixCheck check = new MatrixCheck();
		boolean[][] input = new boolean[][]{
			{false, true},
			{true, false}
		};
		boolean result = check.mono(input);
		assertThat(result, is(true));
	}
	
	@Test
	public void when2on2ThenFalse() {
		MatrixCheck check = new MatrixCheck();
		boolean[][] input = new boolean[][]{
			{false, true},
			{false, false}
		};
		boolean result = check.mono(input);
		assertThat(result, is(false));
	}
	
	@Test
	public void whenOddDataMonoByTrueThenTrue() {
		MatrixCheck check = new MatrixCheck();
		boolean[][] input = new boolean[][]{
			{true, true, true},
			{false, true, true},
			{true, false, true}
		};
		boolean result = check.mono(input);
		assertThat(result, is(true));
	}
	
	@Test
	public void whenOddDataNotMonoByTrueThenFalse() {
		MatrixCheck check = new MatrixCheck();
		boolean[][] input = new boolean[][]{
			{true, true, false},
			{false, false, true},
			{true, false, true}
		};
		boolean result = check.mono(input);
		assertThat(result, is(false));
	}
	
	@Test
	public void whenEvenDataMonoByTrueThenTrue() {
		MatrixCheck check = new MatrixCheck();
		boolean[][] input = new boolean[][]{
			{true, true, true, true},
			{false, true, true, false},
			{true, true, true, false},
			{true, false, true, true}
		};
		boolean result = check.mono(input);
		assertThat(result, is(true));
	}
	
	@Test
	public void whenEvenDataNotMonoByTrueThenFalse() {
		MatrixCheck check = new MatrixCheck();
		boolean[][] input = new boolean[][]{
			{true, true, false, false},
			{false, false, true, false},
			{true, false, true, false},
			{true, false, true, false}
		};
		boolean result = check.mono(input);
		assertThat(result, is(false));
	}
}