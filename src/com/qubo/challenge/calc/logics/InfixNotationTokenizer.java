package com.qubo.challenge.calc.logics;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.qubo.challenge.calc.tokens.Operator;
import com.qubo.challenge.calc.tokens.Paren;

/**
 * 文字列として与えられた数式を、中置記法のトークン列に切り分けるためのクラス
 * @author Qubo
 */
public class InfixNotationTokenizer {
	/** エラー文字列： <code>"認識されないトークン[{0}]が見つかりました！"</code> */
	public static final String ERROR_UNRECOGNIZED_TOKEN = "認識されないトークン[{0}]が見つかりました！";
	/** 単項演算子「負数」検出のため、直前のトークンを保存しておくオブジェクト */
	private Object prevToken;

	/**
	 * 与えられた数式を、中置記法のトークン列に切り分ける
	 * @param input 数式
	 * @return 中置記法トークン列
	 * @throws InvalidFormulaException 認識できないトークンが見つかった場合に発生
	 */
	public List<Object> tokenize(String input) throws InvalidFormulaException {
		input = putSpace(input.toLowerCase());
		prevToken = null;
		StringTokenizer stringTokenizer = new StringTokenizer(input);

		Stack<Object> stack = new Stack<Object>();
		while (stringTokenizer.hasMoreElements()) {
			stack.add(getToken(stringTokenizer.nextToken()));
		}

		return Collections.unmodifiableList(stack);
	}

	/**
	 * 数式内の構成要素の間にスペースを挿入する
	 * @param input 数式
	 * @return スペースで区切られた数式
	 */
	private String putSpace(String input) {
		Pattern pattern = Pattern.compile("\\+|\\-|\\*|/|\\(|\\)|abs");
		Matcher matcher = pattern.matcher(input);
		String result = matcher.replaceAll(" $0 ");
		return result;
	}
	/**
	 * 文字列トークンを、適切なオブジェクトトークンに変換する
	 * @param tokenString 文字列トークン
	 * @return 文字列に対応するオブジェクト
	 * @throws InvalidFormulaException 認識できないトークンが見つかった場合に発生
	 */
	private Object getToken(String tokenString) throws InvalidFormulaException {
		Object token = doGetToken(tokenString);
		prevToken = token;
		return token;
	}
	/**
	 * 文字列トークンを、適切なオブジェクトトークンに変換する（内部用）
	 * @param tokenString 文字列トークン
	 * @return
	 * @throws InvalidFormulaException 認識できないトークンが見つかった場合に発生
	 */
	private Object doGetToken(String tokenString) throws InvalidFormulaException {
		if (Operator.SYMBOL_ABS.equals(tokenString)) {
			return Operator.Abs;
		} else if (tokenString.length() == 1) {
			switch (tokenString.charAt(0)) {
			case Operator.SYMBOL_ADD: return Operator.Add;
			case Operator.SYMBOL_SUB:
				if (prevToken == Paren.Right || prevToken instanceof Integer)
					return Operator.Sub;
				else
					return Operator.Neg;
			case Operator.SYMBOL_MUL: return Operator.Mul;
			case Operator.SYMBOL_DIV: return Operator.Div;
			case '(': return Paren.Left;
			case ')': return Paren.Right;
			}
		}
		try {
			return Integer.parseInt(tokenString);
		} catch (NumberFormatException e) {
			String message = MessageFormat.format(ERROR_UNRECOGNIZED_TOKEN, tokenString);
			throw new InvalidFormulaException(message, e);
		}
	}
}
