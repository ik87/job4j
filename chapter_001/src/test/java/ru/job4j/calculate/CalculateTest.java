package ru.job4j.calculate;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CalculateTest {
	/**
	* Test.
	*
	* @author Petr Arsentev (parsentev@yandex.ru)
	* @version $Id$
	* @since 0.1
	*/
	@Test
	public void whenTakeNameThenTreeEchoPlusName() {
		String input = "Petr Arsentev";
		String expect = "Echo, echo, echo: Petr Arsentev";
		Calculate calc = new Calculate();
		String result = calc.echo(input);
		assertThat(result, is(expect));
	}
}