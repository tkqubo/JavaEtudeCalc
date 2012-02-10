package com.qubo.challenge.calc.tokens;

/**
 * 括弧を表現するクラス
 * @author Qubo
 */
public class Paren {
	/** 開き括弧の文字列表現: {@code "("} */
	public static final char SYMBOL_PAREN_LEFT = '(';
	/** 閉じ括弧の文字表現: {@code ")"} */
	public static final char SYMBOL_PAREN_RIGHT = ')';

	/** シンボル */
	private final char symbol;
	private Paren(char symbol) { this.symbol = symbol; }
	@Override public String toString() { return "" + symbol; }

	/** 開き括弧: {@code "("} */
	public static Paren Left = new Paren(SYMBOL_PAREN_LEFT);
	/** 閉じ括弧: {@code ")"} */
	public static Paren Right = new Paren(SYMBOL_PAREN_RIGHT);
}
