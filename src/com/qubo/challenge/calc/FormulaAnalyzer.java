package com.qubo.challenge.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.qubo.challenge.calc.logics.Calculator;
import com.qubo.challenge.calc.logics.InvalidFormulaException;
import com.qubo.challenge.calc.tokens.BinaryOperator;
import com.qubo.challenge.calc.tokens.Operator;
import com.qubo.challenge.calc.tokens.UnaryOperator;

/**
 * 数式を、演算の優先順位に基づいて階層構造で表示するためのクラス
 * @author Qubo
 */
public class FormulaAnalyzer {
	/**
	 * 数式を、演算の優先順位に基づいて階層構造で表示
	 * @param input
	 * @throws InvalidFormulaException 数式にエラーがあった場合に発生
	 */
	public void printStructure(String input) throws InvalidFormulaException {
		String[] lines = getStructure(input);
		System.out.println("数式構造：");
		System.out.println("┌" + pad(lines[0].length() + 2, '-') + "┐");
		for (String line : lines) {
			System.out.println("│ " + line + " │");
		}
		System.out.println("└" + pad(lines[0].length() + 2, '-') + "┘");
	}
	/**
	 * 数式を、演算の優先順位に基づいて階層構造にしたものを{@link List}{@code <}{@link String}{@code >}として取得する
	 * @param input
	 * @return 階層構造化された数式
	 * @throws InvalidFormulaException 数式にエラーがあった場合に発生
	 */
	public String[] getStructure(String input) throws InvalidFormulaException {
		LeveledToken value = getLeveledValue(input);
		List<String> lines = new ArrayList<String>();
		for (int i = 0; i <= value.level(); i++) {
			lines.add(value.getLevelString(i));
		}
		return lines.toArray(new String[0]);
	}
	/**
	 * 与えられた数式を元に、階層レベル付の数式トークンを生成する内部用メソッド
	 * @param input 数式
	 * @return {@link LeveledToken}オブジェクト
	 * @throws InvalidFormulaException 数式にエラーがあった場合に発生
	 */
	private LeveledToken getLeveledValue(String input) throws InvalidFormulaException {
		Calculator calc = new Calculator();
		calc.eval(input);

		Stack<LeveledToken> stack = new Stack<LeveledToken>();
		Iterable<Object> tokens = calc.getPostfixNotationTokens();
		for (Object token : tokens) {
			if (token instanceof Integer) {
				stack.push(new LeveledToken(token));
			} else if (token instanceof UnaryOperator) {
				UnaryOperator unaryOperator = (UnaryOperator) token;
				LeveledToken operand = stack.pop();
				LeveledToken child = LeveledToken.combine(null, unaryOperator, operand);
				stack.push(child);
			} else if (token instanceof BinaryOperator) {
				BinaryOperator binaryOperator = (BinaryOperator) token;
				LeveledToken operand2 = stack.pop();
				LeveledToken operand1 = stack.pop();
				LeveledToken child = LeveledToken.combine(operand1, binaryOperator, operand2);
				stack.push(child);
			} else {
				throw new UnsupportedOperationException(token + "は処理できません！");
			}
		}

		return (LeveledToken) stack.pop();
	}
	/**
	 * 階層レベル付の数式トークン。
	 * @author Qubo
	 */
	static class LeveledToken {
		/** 内包するトークン */
		Object[] tokens;
		/**
		 * コンストラクタ
		 * @param tokens 内包するトークンの配列
		 */
		LeveledToken(Object... tokens) { this.tokens = tokens; }
		/**
		 * {@link LeveledToken}オブジェクトとしての被演算子と演算子を結合し、新しく{@link LeveledToken}オブジェクトを生成する。
		 * @param operand1 被演算子１
		 * @param operator 演算子
		 * @param operand2 被演算子２
		 * @return 新たに生成された{@link LeveledToken}オブジェクト
		 */
		static LeveledToken combine(LeveledToken operand1, Operator operator, LeveledToken operand2) {
			List<Object> list = new ArrayList<Object>();
			if (operand1 != null) {
				if (operand1.isOperatorPriority(operator.getPriority())) {
					for (Object token : operand1.tokens) {
						list.add(token);
					}
				} else {
					list.add(operand1);
				}
			}
			list.add(operator);
			if (operand2 != null) {
				if (operand2.isOperatorPriority(operator.getPriority())) {
					for (Object token : operand2.tokens) {
						list.add(token);
					}
				} else {
					list.add(operand2);
				}
			}
			return new LeveledToken(list.toArray());
		}


		/**
		 * 数式の文字列としての長さを取得する。
		 * @return 数式の文字列としての長さ
		 */
		int length() {
			int total = 1;
			for (Object token : tokens) {
				if (token instanceof Integer) {
					total += token.toString().length();
				} else if (token instanceof Operator) {
					total += token.toString().length();
				} else if (token instanceof LeveledToken) {
					LeveledToken child = (LeveledToken) token;
					if (isInteger(child)) {
						total += child.tokens[0].toString().length();
					} else {
						total += child.length();
					}
				} else {
					throw new UnsupportedOperationException(token + "は処理できません！");
				}
			}
			total += tokens.length - 1;
			return total;
		}
		private boolean isInteger(LeveledToken token) {
			return token.tokens.length == 1 && token.tokens[0] instanceof Integer;
		}
		/**
		 * 数式のレベルを取得する
		 * @return 数式のレベル
		 */
		int level() {
			int level = 0;
			for (Object token : tokens) {
				if (token instanceof Integer) {
				} else if (token instanceof Operator) {
				} else if (token instanceof LeveledToken) {
					LeveledToken child = (LeveledToken) token;
					if (!isInteger(child)) {
						level = Math.max(level, child.level() + 1);
					}
				} else {
					throw new UnsupportedOperationException(token + "は処理できません！");
				}
			}
			return level;
		}
		/**
		 * 指定したレベルに存在する数式トークンを文字列として取得する。
		 * @param level
		 * @return
		 */
		String getLevelString(int level) {
			StringBuilder builder = new StringBuilder();
			for (Object token : tokens) {
				if (token instanceof Integer || token instanceof Operator) {
					if (token instanceof BinaryOperator)
						builder.append(this.level() < level ? '_' : ' ');

					if (this.level() == level) {
						builder.append(token);
					} else if (this.level() < level) {
						builder.append(pad(token, '_'));
					} else {
						builder.append(pad(token, ' '));
					}

					if (token instanceof Operator)
						builder.append(this.level() < level ? '_' : ' ');
				} else if (token instanceof LeveledToken) {
					LeveledToken child = (LeveledToken) token;
					if (isInteger(child)) {
						Object integer = child.tokens[0];
						if (this.level() == level) {
							builder.append(integer);
						} else if (this.level() < level) {
							builder.append(pad(integer, '_'));
						} else {
							builder.append(pad(integer, ' '));
						}
					} else {
						builder.append(child.getLevelString(level));
					}
				} else {
					throw new UnsupportedOperationException(token + "は処理できません！");
				}
			}
			return builder.toString();
		}
		/**
		 * 数式トークン列に含まれている演算子が、指定された値と同じかどうかをチェックする。
		 * トークン列に演算子が含まれない（つまり数値のみ）の場合は、{@code true}を返す。
		 * @param priority 演算子の優先順位
		 * @return チェックされた値
		 */
		boolean isOperatorPriority(int priority) {
			for (Object token : tokens) {
				if (token instanceof Operator) {
					Operator operator = (Operator) token;
					return operator.getPriority() == priority;
				}
			}
			return true;
		}
	}
	/**
	 * トークンの文字列長に相当するパディング文字列を返す
	 * @param token トークン
	 * @param padding パディング
	 * @return パディング文字列
	 */
	private static String pad(Object token, char padding) {
		return pad(token.toString().length(), padding);
	}
	/**
	 * 指定した長さのパディング文字列を返す
	 * @param length 長さ
	 * @param padding パディング
	 * @return パディング文字列
	 */
	private static String pad(int length, char padding) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			builder.append(padding);
		}
		return builder.toString();
	}
}
