package com.qubo.challenge.calc.tokens;

/**
 * 数学の便利関数を収めたクラス
 * @author Qubo
 */
public class Function {
	/** このコンストラクタは使わないい */
	private Function() { throw new RuntimeException("このクラスはインスタンス化できません！"); }

	/**
	 * 二つの整数の最大公約数を求める
	 * @param a 整数１
	 * @param b 整数２
	 * @return 最大公約数
	 */
	public static int gcd(int a, int b) {
		a = Math.abs(a);
		b = Math.abs(b);
		int r = a % b;
		return r == 0 ? b : gcd(b, r);
	}
	/**
	 * 二つの整数の最小公倍数を求める
	 * @param a 整数１
	 * @param b 整数２
	 * @return 最小公倍数
	 */
	public static int lcm(int a, int b) {
		a = Math.abs(a);
		b = Math.abs(b);
		return a * b / gcd(a, b);
	}
}
