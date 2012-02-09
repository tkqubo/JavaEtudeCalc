package test.com.qubo.challenge.calc.logics;

import static com.qubo.challenge.calc.tokens.Operator.*;
import static com.qubo.challenge.calc.tokens.Paren.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.MessageFormat;

import org.junit.Test;

import com.qubo.challenge.calc.logics.InfixNotationTokenizer;
import com.qubo.challenge.calc.logics.InvalidFormulaException;
import com.qubo.challenge.calc.tokens.Paren;

/**
 * {@link InfixNotationTokenizer}用のテストクラス
 * @author Qubo
 */
public class InfixNotationTokenizerTest {
	InfixNotationTokenizer tokenizer = new InfixNotationTokenizer();

	/** {@link InfixNotationTokenizer#tokenize(String)}のテスト */
	@Test
	public void testTokenize() {
		doTestTokenize("2+5", 2, Add, 5);
		doTestTokenize("2 + 5", 2, Add, 5);
		doTestTokenize("-11", Neg, 11);
		doTestTokenize("--11", Neg, Neg, 11);
		doTestTokenize("2/1+5*3", 2, Div, 1, Add, 5, Mul, 3);
		doTestTokenize("2 - 4 * (3 - 1)", 2, Sub, 4, Mul, Paren.Left, 3, Sub, 1, Right);
		doTestTokenize("-5 * -3 / -(1---4)", Neg, 5, Mul, Neg, 3, Div, Neg, Left, 1, Sub, Neg, Neg, 4, Right);
		doTestTokenize("(2 / 7 + 5 / 14) * 10 / (9/2)", Left, 2, Div, 7, Add, 5, Div, 14, Right, Mul, 10, Div, Left, 9, Div, 2, Right);
		failTestTokenize("3 = 4", "=");
		failTestTokenize("7.68", "7.68");
		failTestTokenize("4 ＋ 3", "＋");
		failTestTokenize("4 \\ 123", "\\");
	}
	/**
	 * {@link #testTokenize()}用の内部メソッド。定義されていないトークンを使用して、正しく例外が発生するかどうかをチェック
	 * @param input 数式
	 * @param invalidToken 例外で表示されるトークン
	 */
	private void failTestTokenize(String input, String invalidToken) {
		try {
			tokenizer.tokenize(input);
			fail();
		} catch (InvalidFormulaException e) {
			String message = MessageFormat.format(InfixNotationTokenizer.ERROR_UNRECOGNIZED_TOKEN, invalidToken);
			assertThat(e.getMessage(), is(message));
		}
	}

	/** {{@link #testTokenize()}用の内部クラス */
	private void doTestTokenize(String input, Object... expecteds) {
		try {
			int pos = 0;
			for (Object token : tokenizer.tokenize(input)) {
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
