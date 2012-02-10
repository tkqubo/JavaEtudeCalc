package test.com.qubo.challenge.calc.tokens;

import static com.qubo.challenge.calc.tokens.Operator.PRIORITY_3;
import static com.qubo.challenge.calc.tokens.Operator.SYMBOL_NEG;
import static com.qubo.challenge.calc.tokens.UnaryOperator.Abs;
import static com.qubo.challenge.calc.tokens.UnaryOperator.Neg;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.qubo.challenge.calc.tokens.Operator;
import com.qubo.challenge.calc.tokens.UnaryOperator;
import com.qubo.challenge.calc.tokens.Value;

/**
 * {@link UnaryOperator}用のテストクラス
 * @author Qubo
 */
public class UnaryOperatorTest {
	/** {@link UnaryOperator#Neg}のテスト */
	@Test
	public void testMinus() {
		doTest(1, 2, "-1/2", Neg);
		doTest(5, -10, "1/2", Neg);
		doTest(12, 43, "-12/43", Neg);
		doTest(-12, -38, "-6/19", Neg);
		assertThat(Neg.getPriority(), is(PRIORITY_3));
		assertThat(Neg.toString(), is(SYMBOL_NEG));
	}


	/** {@link UnaryOperator#Abs}のテスト */
	@Test
	public void testAbs() {
		doTest(-1, 2, "1/2", Abs);
		doTest(5, -10, "1/2", Abs);
		doTest(-12, -43, "12/43", Abs);
		doTest(12, 38, "6/19", Abs);
		Value value;
		value = new Value(-3);
		assertThat(Operator.Neg.operate(Operator.Abs.operate(value)), is(value));
		value = new Value(1, -8);
		assertThat(Operator.Neg.operate(Operator.Abs.operate(value)), is(value));
		value = new Value(6);
		assertThat(Operator.Abs.operate(Operator.Neg.operate(value)), is(value));
		value = new Value(1, 9);
		assertThat(Operator.Abs.operate(Operator.Neg.operate(value)), is(value));
		value = new Value(3, 17);
		assertThat(Operator.Abs.operate(Operator.Neg.operate(value)), is(value));
		assertThat(Operator.Abs.getPriority(), is(Operator.PRIORITY_3));
		assertThat(Operator.Abs.toString(), is(Operator.SYMBOL_ABS));
	}
	/**
	 * {@link #testMinus()}, {@link #testAbs()}用の内部クラス
	 * @param num オペランドの分子
	 * @param den オペランドの分母
	 * @param expected 演算結果の文字列表現
	 * @param operator テストする{@link UnaryOperator}インスタンス
	 */
	private void doTest(int num, int den, String expected, UnaryOperator operator) {
		Value value = new Value(num, den);
		value = operator.operate(value);
		assertThat(value.toString(), is(expected));
	}
}
