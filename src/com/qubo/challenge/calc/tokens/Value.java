package com.qubo.challenge.calc.tokens;


/**
 * 有理数を表現したクラス。
 * @author Qubo
 */
public class Value {
	/** 分母が0になったときに発生する{@link ArithmeticException}のメッセージ内容 */
	public static final String EXCEPTION_DIVIDED_BY_ZERO = "0で除算しました！";
	/** 分母 */
	private int denominator;
	/** 分子 */
	private int numerator;

	/**
	 * 分母を取得する
	 * @return 分母
	 */
	public int getDenominator() { return denominator; }
	/**
	 * 分母を設定する
	 * @param denominator 設定する値
	 */
	public void setDenominator(int denominator) {
		if (denominator == 0) throw new ArithmeticException(EXCEPTION_DIVIDED_BY_ZERO);
		this.denominator = denominator;
		reduce();
	}
	/**
	 * 分子を取得する
	 * @return 分子
	 */
	public int getNumerator() { return numerator; }
	/**
	 * 分子を設定する
	 * @param numerator 設定する値
	 */
	public void setNumerator(int numerator) {
		this.numerator = numerator;
		reduce();
	}

	/**
	 * 分母が1の整数を生成する
	 * @param value 整数
	 */
	public Value(int value) { this(value, 1); }
	/**
	 * 標準のコンストラクタ
	 * @param numerator 分子
	 * @param denominator 分母
	 */
	public Value(int numerator, int denominator) {
		if (denominator == 0) throw new ArithmeticException(EXCEPTION_DIVIDED_BY_ZERO);
		this.numerator = numerator;
		this.denominator = denominator;
		reduce();
	}

	/** 約分を行う */
	private void reduce() {
		if (denominator < 0) {
			denominator = -denominator;
			numerator = -numerator;
		}
		int gcd = Function.gcd(numerator, denominator);
		if (gcd != 1) {
			numerator /= gcd;
			denominator /= gcd;
		}
	}

	/**
	 * 値を実数で取得する
	 * @return 数値の実数表現
	 */
	public double getRealValue() {
		return (double) numerator / denominator;
	}

	/**
	 * 値を分数表現の文字列で表示する
	 */
	@Override
	public String toString() {
		return numerator + (denominator != 1 ? "/" + denominator : "");
	}
	/*
	 * (非 Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Value) {
			Value other = (Value) obj;
			return this.numerator == other.numerator && this.denominator == other.denominator;
		} else if (obj instanceof Integer) {
			Integer other = (Integer) obj;
			return this.denominator == 1 && this.numerator == other;
		} else if (obj instanceof Double) {
			Double other = (Double) obj;
			return this.getRealValue() == other;
		}
		return false;
	}
}
