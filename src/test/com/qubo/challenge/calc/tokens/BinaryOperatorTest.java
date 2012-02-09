package test.com.qubo.challenge.calc.tokens;

import static com.qubo.challenge.calc.tokens.Operator.Add;
import static com.qubo.challenge.calc.tokens.Operator.Div;
import static com.qubo.challenge.calc.tokens.Operator.Mul;
import static com.qubo.challenge.calc.tokens.Operator.PRIORITY_1;
import static com.qubo.challenge.calc.tokens.Operator.PRIORITY_2;
import static com.qubo.challenge.calc.tokens.Operator.SYMBOL_ADD;
import static com.qubo.challenge.calc.tokens.Operator.SYMBOL_DIV;
import static com.qubo.challenge.calc.tokens.Operator.SYMBOL_MUL;
import static com.qubo.challenge.calc.tokens.Operator.SYMBOL_SUB;
import static com.qubo.challenge.calc.tokens.Operator.Sub;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.qubo.challenge.calc.tokens.BinaryOperator;
import com.qubo.challenge.calc.tokens.Operator;
import com.qubo.challenge.calc.tokens.Value;

/**
 * {@link BinaryOperator}用のテストクラス
 * @author Qubo
 */
public class BinaryOperatorTest {
	/** {@link Operator#Add}のテスト */
	@Test
	public void testAdd() {
		doTest(1, 1, 4, 1, Add, "5");
		doTest(1, 2, 6, 1, Add, "13/2");
		doTest(4, 1, -1, 3, Add, "11/3");
		doTest(1, 4, 1, 3, Add, "7/12");
		doTest(7, 20, 5, 16, Add, "53/80");
		doTest(1, 2, 1, 4, Add, "3/4");
		doTest(4, 7, 10, 7, Add, "2");
		assertThat(Add.getPriority(), is(PRIORITY_1));
		assertThat(Add.toString(), is("" + SYMBOL_ADD));
	}
	/** {@link Operator#Sub}のテスト */
	@Test
	public void testSub() {
		doTest(1, 1, 4, 1, Sub, "-3");
		doTest(1, 2, 6, 1, Sub, "-11/2");
		doTest(4, 1, -1, 3, Sub, "13/3");
		doTest(1, 4, 1, 3, Sub, "-1/12");
		doTest(7, 20, 5, 16, Sub, "3/80");
		doTest(1, 2, 1, 4, Sub, "1/4");
		doTest(4, 7, 10, 7, Sub, "-6/7");
		doTest(15, 7, 1, 7, Sub, "2");
		assertThat(Sub.getPriority(), is(PRIORITY_1));
		assertThat(Sub.toString(), is("" + SYMBOL_SUB));
	}
	/** {@link Operator#Mul}のテスト */
	@Test
	public void testMul() {
		doTest(1, 1, 4, 1, Mul, "4");
		doTest(1, 2, 6, 1, Mul, "3");
		doTest(4, 1, -1, 3, Mul, "-4/3");
		doTest(1, 4, 1, 3, Mul, "1/12");
		doTest(7, 20, 5, 16, Mul, "7/64");
		doTest(1, 2, 1, 4, Mul, "1/8");
		doTest(4, 7, 10, 7, Mul, "40/49");
		doTest(17, 35, 21, 34, Mul, "3/10");
		assertThat(Mul.getPriority(), is(PRIORITY_2));
		assertThat(Mul.toString(), is("" + SYMBOL_MUL));
	}
	/** {@link Operator#Div}のテスト */
	@Test
	public void testDiv() {
		doTest(1, 1, 4, 1, Div, "1/4");
		doTest(1, 2, 6, 1, Div, "1/12");
		doTest(4, 1, -1, 3, Div, "-12");
		doTest(1, 4, 1, 3, Div, "3/4");
		doTest(7, 20, 5, 16, Div, "28/25");
		doTest(1, 2, 1, 4, Div, "2");
		doTest(4, 7, 10, 7, Div, "2/5");
		doTest(1, 100, -3, 200, Div, "-2/3");
		assertThat(Div.getPriority(), is(PRIORITY_2));
		assertThat(Div.toString(), is("" + SYMBOL_DIV));
	}
	/**
	 * {@link #testAdd()}, {@link #testDiv()}, {@link #testMul()}, {@link #testSub()}用の内部クラス
	 * @param num1 オペランド１の分子
	 * @param den1 オペランド１の分母
	 * @param num2 オペランド２の分子
	 * @param den2 オペランド２の分母
	 * @param operator 二項演算
	 * @param expected 演算結果の文字列表現
	 */
	private void doTest(int num1, int den1, int num2, int den2, BinaryOperator operator, String expected) {
		Value operand1 = new Value(num1, den1);
		Value operand2 = new Value(num2, den2);
		Value result = operator.operate(operand1, operand2);
		assertThat(result.toString(), is(expected));
	}
}
