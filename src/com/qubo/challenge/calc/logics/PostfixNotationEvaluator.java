package com.qubo.challenge.calc.logics;

import java.text.MessageFormat;
import java.util.Stack;

import com.qubo.challenge.calc.tokens.BinaryOperator;
import com.qubo.challenge.calc.tokens.UnaryOperator;
import com.qubo.challenge.calc.tokens.Value;

/**
 * 後置記法で表現した数式トークン列を評価し、{@link Value}型で戻すクラス
 * @author Qubo
 */
public class PostfixNotationEvaluator {
	private static final String ERROR_UNPROCESSED_VALUES = "数値列[{0}]を処理できませんでした！";
	/** オペランド名: <code>"単項演算子のオペランド"</code> */
	public static final String UNARY_OPERAND = "単項演算子[{0}]のオペランド";
	/** オペランド名: <code>"二項演算子の左オペランド"</code> */
	public static final String BINARY_OPERAND1 = "二項演算子[{0}]の左オペランド";
	/** オペランド名: <code>"二項演算子の右オペランド"</code> */
	public static final String BINARY_OPERAND2 = "二項演算子[{0}]の右オペランド";
	/** エラーメッセージ: <code>"{0}が足りません！"</code> */
	public static final String ERROR_DEFICIENT_OPERAND = "{0}が足りません！";

	/**
	 * 後置記法で表現した数式トークン列を評価し、{@link Value}型で戻す
	 * @param tokens 数式
	 * @return 計算結果
	 * @throws InvalidFormulaException 数式にエラーがある場合に発生
	 */
	public Value eval(Iterable<Object> tokens) throws InvalidFormulaException {
		Stack<Value> stack = new Stack<Value>();
		for (Object token : tokens) {
			if (token instanceof Integer) {
				stack.push(new Value((int) token));
			} else if (token instanceof UnaryOperator) {
				UnaryOperator unaryOperator = (UnaryOperator) token;
				checkOperandDeficiency(stack, MessageFormat.format(UNARY_OPERAND, unaryOperator));
				Value operand = stack.pop();
				Value result = unaryOperator.operate(operand);
				stack.push(result);
			} else if (token instanceof BinaryOperator) {
				BinaryOperator binaryOperator = (BinaryOperator) token;
				checkOperandDeficiency(stack, MessageFormat.format(BINARY_OPERAND1, binaryOperator));
				Value operand2 = stack.pop();
				checkOperandDeficiency(stack, MessageFormat.format(BINARY_OPERAND2, binaryOperator));
				Value operand1 = stack.pop();
				Value result = binaryOperator.operate(operand1, operand2);
				stack.push(result);
			} else {
				throw new UnsupportedOperationException(token + "は処理できません！");
			}
		}

		checkSingleValue(stack);

		return stack.pop();
	}

	/**
	 * 演算により、スタック内の{@link Value}が1個に収束されたかどうかをチェックする。
	 * スタック内に{@link Value}が2つ以上残っていた場合は例外が発生する。
	 * @param stack 対象スタック
	 * @throws InvalidFormulaException スタック内に{@link Value}が2つ以上残っていた場合に発生
	 */
	private void checkSingleValue(Stack<Value> stack) throws InvalidFormulaException {
		if (stack.size() != 1) {
			StringBuilder builder = new StringBuilder();
			for (Value value : stack) {
				if (builder.length() > 0) builder.append(", ");
				builder.append(value);
			}
			String message = MessageFormat.format(ERROR_UNPROCESSED_VALUES, builder);
			throw new InvalidFormulaException(message);
		}
	}

	/**
	 * オペランドが不足していないかチェック。足りない場合は例外が発生
	 * @param stack オペランドの入ったスタック
	 * @param item 対象オペランド名
	 * @throws InvalidFormulaException オペランドが足りない場合に発生
	 */
	private void checkOperandDeficiency(Stack<Value> stack, String item) throws InvalidFormulaException {
		if (stack.isEmpty()) throw new InvalidFormulaException(MessageFormat.format(ERROR_DEFICIENT_OPERAND, item));
	}
}
