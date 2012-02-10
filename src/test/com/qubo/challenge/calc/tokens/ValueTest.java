package test.com.qubo.challenge.calc.tokens;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.qubo.challenge.calc.tokens.Value;

/**
 * {@link Value}用のテストクラス
 * @author Qubo
 */
public class ValueTest {
	private static final float DELTA = 0.000001f;
	Value value;

	/** {@link Value#Value(int)}のテスト */
	@Test
	public void testValueInt() {
		value = new Value(1);
		assertEquals("正しくありません！", value.getRealValue(), 1.0f, DELTA);
		value = new Value(0);
		assertEquals("正しくありません！", value.getRealValue(), 0.0f, DELTA);
		value = new Value(1582);
		assertEquals("正しくありません！", value.getRealValue(), 1582.0f, DELTA);
	}
	/** {@link Value#Value(int, int)}のテスト */
	@Test
	public void testValueIntInt() {
		value = new Value(1, 2);
		assertEquals("正しくありません！", value.getRealValue(), 0.5f, DELTA);
		value = new Value(5, 3);
		assertEquals("正しくありません！", value.getRealValue(), 1.666666666666666f, DELTA);
		try {
			value = new Value(4, 0);
			fail();
		} catch (ArithmeticException e) {
			assertThat(e.getMessage(), is(Value.EXCEPTION_DIVIDED_BY_ZERO));
		}
		try {
			value = new Value(0, 0);
			fail();
		} catch (ArithmeticException e) {
			assertThat(e.getMessage(), is(Value.EXCEPTION_DIVIDED_BY_ZERO));
		}
	}


	/** {@link Value#getDenominator()}のテスト */
	@Test
	public void testGetDenominator() {
		value = new Value(1, 2);
		assertThat(value.getDenominator(), is(2));
		value = new Value(6, 9);
		assertThat(value.getDenominator(), is(3));
		value = new Value(4, 7);
		assertThat(value.getDenominator(), is(7));
		value = new Value(26, 32);
		assertThat(value.getDenominator(), is(16));
	}
	/** {@link Value#setDenominator(int)}のテスト */
	@Test
	public void testSetDenominator() {
		value = new Value(1, 1);
		value.setDenominator(5);
		assertThat(value.getDenominator(), is(5));
		value = new Value(6, 1);
		value.setDenominator(9);
		assertThat(value.getDenominator(), is(3));
		value = new Value(4, 1);
		value.setDenominator(7);
		assertThat(value.getDenominator(), is(7));
		value = new Value(26, 1);
		value.setDenominator(32);
		assertThat(value.getDenominator(), is(16));

		try {
			value.setDenominator(0);
		} catch (ArithmeticException e) {
			assertThat(e.getMessage(), is(Value.EXCEPTION_DIVIDED_BY_ZERO));
		}
	}

	/** {@link Value#getNumerator()}のテスト */
	@Test
	public void testGetNumerator() {
		value = new Value(1, 2);
		assertThat(value.getNumerator(), is(1));
		value = new Value(6, 9);
		assertThat(value.getNumerator(), is(2));
		value = new Value(4, 7);
		assertThat(value.getNumerator(), is(4));
		value = new Value(26, 32);
		assertThat(value.getNumerator(), is(13));
	}

	/** {@link Value#setNumerator(int)}のテスト */
	@Test
	public void testSetNumerator() {
		value = new Value(0, 2);
		value.setNumerator(1);
		assertThat(value.getNumerator(), is(1));
		value = new Value(1, 9);
		value.setNumerator(6);
		assertThat(value.getNumerator(), is(2));
		value = new Value(1, 7);
		value.setNumerator(4);
		assertThat(value.getNumerator(), is(4));
		value = new Value(1, 32);
		value.setNumerator(26);
		assertThat(value.getNumerator(), is(13));
	}

	/** {@link Value#toString()}のテスト */
	@Test
	public void testToString() {
		value = new Value(1, 2);
		assertThat(value.toString(), is("1/2"));
		value = new Value(6, 9);
		assertThat(value.toString(), is("2/3"));
		value = new Value(4, 7);
		assertThat(value.toString(), is("4/7"));
		value = new Value(26, 32);
		assertThat(value.toString(), is("13/16"));
	}

}
