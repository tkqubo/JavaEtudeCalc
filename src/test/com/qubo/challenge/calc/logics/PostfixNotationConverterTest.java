package test.com.qubo.challenge.calc.logics;

import static com.qubo.challenge.calc.tokens.Operator.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.qubo.challenge.calc.logics.InfixNotationTokenizer;
import com.qubo.challenge.calc.logics.InvalidFormulaException;
import com.qubo.challenge.calc.logics.PostfixNotationConverter;

/**
 * {@link PostfixNotationConverter}用のテストクラス
 * @author Qubo
 */
public class PostfixNotationConverterTest {
	InfixNotationTokenizer tokenizer = new InfixNotationTokenizer();
	PostfixNotationConverter converter = new PostfixNotationConverter();

	/** {@link PostfixNotationConverter#convert(Iterable)}のテスト */
	@Test
	public void testConvert() {
		doTestConvert("2+5", 2, 5, Add);
		doTestConvert("2 + 5", 2, 5, Add);
		doTestConvert("-11", 11, Neg);
		doTestConvert("--11", 11, Neg, Neg);
		doTestConvert("2/1+5*3", 2, 1, Div, 5, 3, Mul, Add);
		doTestConvert("2 - 4 * (3 - 1)", 2, 4, 3, 1, Sub, Mul, Sub);
		doTestConvert("-5 * -3 / -(1---4)", 5, Neg, 3, Neg, Mul, 1, 4, Neg, Neg, Sub, Neg, Div);
		doTestConvert("(2 / 7 + 5 / 14) * 10 / (9/2)", 2, 7, Div, 5, 14, Div, Add, 10, Mul, 9, 2, Div, Div);
		doFailTestConvert("2 +( 3", PostfixNotationConverter.ERROR_UNPROCESSED_LEFTPAREN);
		doFailTestConvert("2 * 6 - 3)", PostfixNotationConverter.ERROR_DEFICIT_LEFTPAREN);
		doFailTestConvert(")", PostfixNotationConverter.ERROR_DEFICIT_LEFTPAREN);
		doFailTestConvert("(", PostfixNotationConverter.ERROR_UNPROCESSED_LEFTPAREN);
		doFailTestConvert(")(", PostfixNotationConverter.ERROR_DEFICIT_LEFTPAREN);
	}
	/**
	 * {@link #testConvert()}用の内部クラス。
	 * 間違った数式を入力して、正しく例外が発生するかどうかをチェック。
	 * @param formula 数式
	 * @param message 例外メッセージ
	 */
	private void doFailTestConvert(String formula, String message) {
		try {
			converter.convert(tokenizer.tokenize(formula));
			fail();
		} catch (InvalidFormulaException e) {
			assertThat(e.getMessage(), is(message));
		}
	}
	/**
	 * {@link #testConvert()}用の内部クラス
	 * @param input
	 * @param expecteds
	 */
	private void doTestConvert(String input, Object... expecteds) {
		try {
			int pos = 0;
			for (Object token : converter.convert(tokenizer.tokenize(input))) {
				assertThat(token, is(expecteds[pos]));
				pos++;
				assertTrue(pos <= expecteds.length);
			}
			assertThat(pos, is(expecteds.length));
		} catch (InvalidFormulaException e) {
			fail(e.getMessage());
		}
	}
}
