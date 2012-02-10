package test.com.qubo.challenge.calc.logics;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.text.MessageFormat;

import org.junit.Test;

import com.qubo.challenge.calc.logics.Calculator;
import com.qubo.challenge.calc.logics.InfixNotationTokenizer;
import com.qubo.challenge.calc.logics.InvalidFormulaException;
import com.qubo.challenge.calc.tokens.Value;

/**
 * {@link Calculator}用のテストを定義したクラス
 * @author Qubo
 */
public class CalculatorTest {
	Calculator calculator = new Calculator();

	/** {@link Calculator#eval(String)}のテスト */
	@Test
	public void testEval() {
		try {
			doTestEval("2+5", 7);
			doTestEval("2 + 5", 7);
			doTestEval("-11", -11);
			doTestEval("--11", 11);
			doTestEval("2/1+5*3", 17);
			doTestEval("2 - 4 * (3 - 1)", -6);
			doTestEval("-5 * -3 / -(1---4)", 5);
			doTestEval("1/3 + 2/3 - 3/2 * 8 - 4", -15);
			doTestEval("(2 / 7 + 5 / 14) * 10 / (9/2)", 10/7.0f);
		} catch (InvalidFormulaException e) {
			fail(e.getMessage());
		}
		failTestEval("2+&", "&");
		failTestEval("4+%%%", "%%%");
		failTestEval("3.14", "3.14");
		failTestEval("3＊6", "3＊6");
		failTestEval("h", "h");
	}
	/**
	 * {@link #testEval()}用の内部メソッド。定義されていないトークンを使用して、例外が発生するかどうかをチェック
	 * @param input 数式
	 * @param invalidToken 例外で表示されるトークン
	 */
	private void failTestEval(String input, String invalidToken) {
		try {
			calculator.eval(input);
			fail();
		} catch (InvalidFormulaException e) {
			String message = MessageFormat.format(InfixNotationTokenizer.ERROR_UNRECOGNIZED_TOKEN, invalidToken);
			assertThat(e.getMessage(), is(message));
		}
	}
	/**
	 * {@link #testEval()}用の内部メソッド
	 * @param input
	 * @param expected
	 * @throws InvalidFormulaException 数式にエラーがあった場合に発生
	 */
	private void doTestEval(String input, double expected) throws InvalidFormulaException {
		Value value = calculator.eval(input);
		assertEquals(value.getRealValue(), expected, 0.0000001f);
	}

}
