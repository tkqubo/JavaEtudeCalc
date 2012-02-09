package com.qubo.challenge.calc.logics;

/**
 * 数式にエラーがあった場合に発生。
 * @author Qubo
 */
public class InvalidFormulaException extends Exception {
	/** シリアルバージョンUID？ */
	private static final long serialVersionUID = 1L;
	/** 標準のコンストラクタ */
	public InvalidFormulaException() { super(); }
	public InvalidFormulaException(String message, Throwable throwable) { super(message, throwable); }
	public InvalidFormulaException(String mesasge) { super(mesasge); }
	public InvalidFormulaException(Throwable throwable) { super(throwable); }
}
