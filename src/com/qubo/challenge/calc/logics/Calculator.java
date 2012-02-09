package com.qubo.challenge.calc.logics;

import com.qubo.challenge.calc.tokens.Value;

/**
 * 文字列で与えられた数式を計算し、結果をValue型として返すクラス。
 * @author Qubo
 */
public class Calculator {
	/** 計算過程で生じる中置記法トークン列 */
	private Iterable<Object> infixNotationTokens;
	/** 計算過程で生じる後置記法トークン列 */
	private Iterable<Object> postfixNotationTokens;

	/**
	 * 最後に{@link #eval(String)}で実行された数式の、中置記法トークン列を返す
	 * @return 中置記法トークン列
	 */
	public Iterable<Object> getInfixNotationTokens() { return infixNotationTokens; }
	/**
	 * 最後に{@link #eval(String)}で実行された数式の、後置記法トークン列を返す
	 * @return 後置記法トークン列
	 */
	public Iterable<Object> getPostfixNotationTokens() { return postfixNotationTokens; }

	/**
	 * 文字列で与えられた数式を計算し、結果をValue型として返す。
	 * <br />
	 * 計算は以下のプロセスで成り立つ。
	 * <ol>
	 * <li>数式を、数・演算子・括弧などの構成要素ごとに切り分ける</li>
	 * <li>その配列を中置記法のトークン列へ変換</li>
	 * <li>それをが後置記法へ変換</li>
	 * <li>それを処理し、計算計算結果を取得する</li>
	 * </ol>
	 * 1～3の過程で発生した中置記法トークン列、後置記法トークン列は、メソッドの実行後、それぞれ
	 * {@link #getInfixNotationTokens()}、{@link #getPostfixNotationTokens()}で取得することができる。
	 *
	 * @param input 数式
	 * @return 計算結果
	 * @throws InvalidFormulaException 数式にエラーがあった場合に発生
	 */
	public Value eval(String input) throws InvalidFormulaException {
		InfixNotationTokenizer tokenizer = new InfixNotationTokenizer();
		PostfixNotationConverter converter = new PostfixNotationConverter();
		PostfixNotationEvaluator evaluator = new PostfixNotationEvaluator();

		infixNotationTokens = tokenizer.tokenize(input);
		postfixNotationTokens = converter.convert(infixNotationTokens);
		Value value = evaluator.eval(postfixNotationTokens);

		return value;
	}
}
