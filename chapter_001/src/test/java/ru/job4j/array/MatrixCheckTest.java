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
public class MatrixCheckTest {
	
	@Test
	public void when2on2ThenTrue() {
		MatrixCheck checker = new MatrixCheck();
		boolean[][] input = new boolean[][]{
			{false, true},
			{true, false}
		};
		boolean result = checker.mono(input);
		assertThat(result, is(true));
	}
	
	@Test
	public void when2on2ThenFalse() {
		MatrixCheck checker = new MatrixCheck();
		boolean[][] input = new boolean[][]{
			{false, true},
			{false, false}
		};
		boolean result = checker.mono(input);
		assertThat(result, is(false));
	}
	
	@Test
	public void whenOddDataMonoByTrueThenTrue() {
		MatrixCheck checker = new MatrixCheck();
		boolean[][] input = new boolean[][]{
			{true, true, true},
			{false, true, true},
			{true, false, true}
		};
		boolean result = checker.mono(input);
		assertThat(result, is(true));
	}
	
	@Test
	public void whenOddDataNotMonoByTrueThenFalse() {
		MatrixCheck checker = new MatrixCheck();
		boolean[][] input = new boolean[][]{
			{true, true, false},
			{false, false, true},
			{true, false, true}
		};
		boolean result = checker.mono(input);
		assertThat(result, is(false));
	}
	
	@Test
	public void whenEvenDataMonoByTrueThenTrue() {
		MatrixCheck checker = new MatrixCheck();
		boolean[][] input = new boolean[][]{
			{true, true, true, true},
			{false, true, true, false},
			{true, true, true, false},
			{true, false, true, true}
		};
		boolean result = checker.mono(input);
		assertThat(result, is(true));
	}
	
	@Test
	public void whenEvenDataNotMonoByTrueThenFalse() {
		MatrixCheck checker = new MatrixCheck();
		boolean[][] input = new boolean[][]{
			{true, true, false, false},
			{false, false, true, false},
			{true, false, true, false},
			{true, false, true, false}
		};
		boolean result = checker.mono(input);
		assertThat(result, is(false));
	}
}