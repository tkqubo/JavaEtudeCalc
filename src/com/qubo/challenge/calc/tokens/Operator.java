package com.qubo.challenge.calc.tokens;

/**
 * ‰‰Zq‚ğ•\Œ»‚·‚éƒCƒ“ƒ^[ƒtƒF[ƒX
 * @author Qubo
 */
public interface Operator {
	/** ‰ÁZ‚Ì•¶š—ñ•\Œ»: {@code "+"} */
	public static final char SYMBOL_ADD = '+';
	/** Œ¸Z‚Ì•¶š—ñ•\Œ»: {@code "-"} */
	public static final char SYMBOL_SUB = '-';
	/** æZ‚Ì•¶š—ñ•\Œ»: {@code "*"} */
	public static final char SYMBOL_MUL = '*';
	/** œZ‚Ì•¶š—ñ•\Œ»: {@code "/"} */
	public static final char SYMBOL_DIV = '/';
	/** •‰”‚Ì•¶š—ñ•\Œ»: {@code "neg"} */
	public static final String SYMBOL_NEG = "neg";
	/** â‘Î’l‚Ì•¶š—ñ•\Œ»: {@code "abs"} */
	public static final String SYMBOL_ABS = "abs";

	/** ‰ÁZ */
	public static final BinaryOperator Add = BinaryOperator.Add;
	/** Œ¸Z */
	public static final BinaryOperator Sub = BinaryOperator.Sub;
	/** æZ */
	public static final BinaryOperator Mul = BinaryOperator.Mul;
	/** œZ */
	public static final BinaryOperator Div = BinaryOperator.Div;
	/** •‰” */
	public static final UnaryOperator Neg = UnaryOperator.Neg;
	/** â‘Î’l */
	public static final UnaryOperator Abs = UnaryOperator.Abs;

	/** ‰‰Z‚Ì—Dæ‡ˆÊ‚Pi‰ÁZAŒ¸Zj */
	final int PRIORITY_1 = 1;
	/** ‰‰Z‚Ì—Dæ‡ˆÊ‚QiæZAœZj */
	final int PRIORITY_2 = 2;
	/** ‰‰Z‚Ì—Dæ‡ˆÊ‚Ri•‰”Aâ‘Î’lj */
	final int PRIORITY_3 = 3;
	/**
	 * ‰‰Zq‚Ì—Dæ‡ˆÊ‚ğ•Ô‚·
	 * @return ‰‰Zq‚Ì—Dæ‡ˆÊ
	 */
	public abstract int getPriority();
}