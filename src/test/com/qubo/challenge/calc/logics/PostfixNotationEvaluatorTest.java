package test.com.qubo.challenge.calc.logics;

import static org.junit.Assert.*;

import org.junit.Test;

import com.qubo.challenge.calc.logics.InfixNotationTokenizer;
import com.qubo.challenge.calc.logics.InvalidFormulaException;
import com.qubo.challenge.calc.logics.PostfixNotationConverter;
import com.qubo.challenge.calc.logics.PostfixNotationEvaluator;
import com.qubo.challenge.calc.tokens.Value;

/**
 * {@link PostfixNotationEvaluator}用のテストクラス
 * @author Qubo
 */
public class PostfixNotationEvaluatorTest {
	InfixNotationTokenizer tokenizer = new InfixNotationTokenizer();
	PostfixNotationConverter converter = new PostfixNotationConverter();
	PostfixNotationEvaluator evaluator = new PostfixNotationEvaluator();

	/** {@link PostfixNotationEvaluator#eval(Iterable)}のテスト */
	@Test
	public void testEval() {
		doTestEval("2+5", 7);
		doTestEval("2 + 5", 7);
		doTestEval("-11", -11);
		doTestEval("--11", 11);
		doTestEval("2/1+5*3", 17);
		doTestEval("2 - 4 * (3 - 1)", -6);
		doTestEval("-5 * -3 / -(1---4)", 5);
		doTestEval("1/3 + 2/3 - 3/2 * 8 - 4", -15);
		doTestEval("(2 / 7 + 5 / 14) * 10 / (9/2)", 10/7.0f);
	}
	/** {@link #testEval()}用の内部クラス */
	private void doTestEval(String input, double expected) {
		try {
			Value value = evaluator.eval(converter.convert(tokenizer.tokenize(input)));
			assertEquals(value.getRealValue(), expected, 0.0000001f);
		} catch (InvalidFormulaException e) {
			fail(e.getMessage());
		}
	}

}
