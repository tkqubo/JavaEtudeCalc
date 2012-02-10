package test.com.qubo.challenge.calc.tokens;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import com.qubo.challenge.calc.tokens.Function;

/**
 * {@link Function}用のテストクラス
 * @author Qubo
 */
public class FunctionTest {
	/** {@link Function#gcd(int, int)}のテスト */
	@Test
	public void testGcd() {
		assertThat(Function.gcd(12, 8), is(4));
		assertThat(Function.gcd(1, 3), is(1));
		assertThat(Function.gcd(7, 5), is(1));
		assertThat(Function.gcd(6, 6), is(6));
		assertThat(Function.gcd(4, 82), is(2));
	}
	/** {@link Function#lcm(int, int)}のテスト */
	@Test
	public void testLcm() {
		assertThat(Function.lcm(12, 8), is(24));
		assertThat(Function.lcm(1, 3), is(3));
		assertThat(Function.lcm(7, 5), is(35));
		assertThat(Function.lcm(6, 6), is(6));
		assertThat(Function.lcm(4, 82), is(164));
	}
}
