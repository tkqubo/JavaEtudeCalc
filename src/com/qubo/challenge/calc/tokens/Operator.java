package com.qubo.challenge.calc.tokens;

/**
 * 演算子を表現するインターフェース
 * @author Qubo
 */
public interface Operator {
	/** 加算の文字列表現: {@code "+"} */
	public static final char SYMBOL_ADD = '+';
	/** 減算の文字列表現: {@code "-"} */
	public static final char SYMBOL_SUB = '-';
	/** 乗算の文字列表現: {@code "*"} */
	public static final char SYMBOL_MUL = '*';
	/** 除算の文字列表現: {@code "/"} */
	public static final char SYMBOL_DIV = '/';
	/** 負数の文字列表現: {@code "neg"} */
	public static final String SYMBOL_NEG = "neg";
	/** 絶対値の文字列表現: {@code "abs"} */
	public static final String SYMBOL_ABS = "abs";

	/** 加算 */
	public static final BinaryOperator Add = BinaryOperator.Add;
	/** 減算 */
	public static final BinaryOperator Sub = BinaryOperator.Sub;
	/** 乗算 */
	public static final BinaryOperator Mul = BinaryOperator.Mul;
	/** 除算 */
	public static final BinaryOperator Div = BinaryOperator.Div;
	/** 負数 */
	public static final UnaryOperator Neg = UnaryOperator.Neg;
	/** 絶対値 */
	public static final UnaryOperator Abs = UnaryOperator.Abs;

	/** 演算の優先順位１（加算、減算） */
	final int PRIORITY_1 = 1;
	/** 演算の優先順位２（乗算、除算） */
	final int PRIORITY_2 = 2;
	/** 演算の優先順位３（負数、絶対値） */
	final int PRIORITY_3 = 3;
	/**
	 * 演算子の優先順位を返す
	 * @return 演算子の優先順位
	 */
	public abstract int getPriority();
}