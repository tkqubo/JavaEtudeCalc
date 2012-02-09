package com.qubo.challenge.calc.logics;

import java.util.Collections;
import java.util.Stack;

import com.qubo.challenge.calc.tokens.Operator;
import com.qubo.challenge.calc.tokens.Paren;
import com.qubo.challenge.calc.tokens.UnaryOperator;

/**
 * 中置記法を、後置記法に変換するクラス
 * @author Qubo
 */
public class PostfixNotationConverter {
	/** エラーメッセージ: <code>"閉じ括弧が余っています！"</code> */
	public static final String ERROR_UNPROCESSED_LEFTPAREN = "開き括弧が余っています！";
	/** エラーメッセージ: <code>"開き括弧が不足しています！"</code> */
	public static final String ERROR_DEFICIT_LEFTPAREN = "開き括弧が不足しています！";

	/**
	 * {@link Iterable}{@code <}{@link Object}{@code >}で与えられた中置記法トークン列を、後置記法トークン列に変換する
	 * @param tokens 中置記法で表現した数式
	 * @return 後置記法で表現した数式
	 * @throws InvalidFormulaException トークンが不足している場合に発生
	 */
	public Iterable<Object> convert(Iterable<Object> tokens) throws InvalidFormulaException {
		// 後置記法スタック
		Stack<Object> stackPn = new Stack<Object>();
		// 演算子スタック
		Stack<Object> stackOp = new Stack<Object>();

		for (Object token : tokens) {
			if (token instanceof Integer) {
				stackPn.push(token);
			} else if (token == Paren.Left) {
				stackOp.push(token);
			} else if (token == Paren.Right) {
				checkEmpty(stackOp, ERROR_DEFICIT_LEFTPAREN);
				while (stackOp.peek() != Paren.Left) {
					stackPn.push(stackOp.pop());
					checkEmpty(stackOp, ERROR_DEFICIT_LEFTPAREN);
				}
				stackOp.pop();
			} else if (token instanceof UnaryOperator) {
				stackOp.push(token);
			} else if (token instanceof Operator) {
				consumeOperatorStack(stackPn, stackOp, (Operator) token);
			} else {
				throw new UnsupportedOperationException(token + "は処理できません！");
			}
		}

		while (!stackOp.empty()) {
			checkRightParen(stackOp);
			stackPn.push(stackOp.pop());
		}

		return Collections.unmodifiableList(stackPn);
	}

	/**
	 * 対象スタック内の未処理の開き括弧をチェックする。未処理の開き括弧が存在した場合は例外が発生
	 * @param stackOp 対象スタック
	 * @throws InvalidFormulaException 未処理の開き括弧が存在した場合に発生
	 */
	private void checkRightParen(Stack<Object> stackOp) throws InvalidFormulaException {
		if (stackOp.peek() == Paren.Left) throw new InvalidFormulaException(ERROR_UNPROCESSED_LEFTPAREN);
	}
	/**
	 * 対象スタックが空でないかチェックする。空の場合は例外が発生。
	 * @param stackOp 対象スタック
	 * @param message エラーメッセージ
	 * @throws InvalidFormulaException トークンが不足している場合に発生
	 */
	private void checkEmpty(Stack<Object> stackOp, String message) throws InvalidFormulaException {
		if (stackOp.isEmpty()) throw new InvalidFormulaException(message);
	}
	/**
	 * 優先順位に基づいて、演算子スタックにある{@link Operator}インスタンスを後置記法スタックにプッシュする。
	 * @param stackPn 後置記法スタック
	 * @param stackOp 演算子スタック
	 * @param currentOperatorToken 新たな{@link Operator}インスタンス
	 */
	private void consumeOperatorStack(Stack<Object> stackPn, Stack<Object> stackOp, Operator currentOperatorToken) {
		while (!stackOp.empty() && stackOp.peek() instanceof Operator) {
			Operator stackTopOperatorToken = (Operator) stackOp.peek();
			if (currentOperatorToken.getPriority() <= stackTopOperatorToken.getPriority())
				stackPn.push(stackOp.pop());
			else
				break;
		}
		stackOp.push(currentOperatorToken);
	}
}
