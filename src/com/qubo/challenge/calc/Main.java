package com.qubo.challenge.calc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.qubo.challenge.calc.logics.Calculator;
import com.qubo.challenge.calc.logics.InvalidFormulaException;
import com.qubo.challenge.calc.tokens.Value;

/**
 * コンソールプログラムのエントリポイントが定義されたクラス
 * @author Qubo
 */
public class Main {
	/** 実数出力用のオプション: {@code "-d"} */
	private static final String OPTION_REAL_VALUE = "-d";
	/** 計算過程出力用のオプション: {@code "-s"} */
	private static final String OPTION_SHOW_FORMULA = "-s";

	/** {@link Calculator}インスタンス */
	private static Calculator calculator = new Calculator();
	/** {@link FormulaAnalyzer}インスタンス */
	private static FormulaAnalyzer analyzer = new FormulaAnalyzer();
	/** 実数出力が有効かどうか */
	private static boolean realValueRequired;
	/** 計算過程出力が有効かどうか */
	private static boolean showFormula;

	/**
	 * <p>
	 * コンソールプログラムのエントリポイント。
	 * 引数として
	 * <ul>
	 * <li>数式</li>
	 * <li>実数表示オプション({@code "-d"})</li>
	 * <li>計算過程出力オプション({@code "-s"})</li>
	 * </ul>
	 * の３つを取ることができる。
	 * <br />
	 * 起動コマンド例：<br />
	 * <code>
	 * java com.qubo.caea0121.calc.Main "1 + 4 * -5"<br />
	 * java com.qubo.caea0121.calc.Main 3-5/2+8 -d -s<br />
	 * java com.qubo.caea0121.calc.Main 3*-5+(-7*4) -s<br />
	 * </code>
	 * </p>
	 * <p>
	 * 引数に数式を含めなかった場合、コンソールから数式の入力を求める「連続実行モード」で起動する。
	 * このモードでは、オプション指定がない場合、「実数表示オプションなし」「計算過程出力オプションあり」の状態で起動する。
	 * また、何も入力せずEnterキーを押して終了するまで、何度も計算を実行することができる。
	 * </p>
	 * @param args コマンドライン引数
	 */
	public static void main(String[] args) {
		realValueRequired = isRealValueRequired(args);
		showFormula = showFormula(args);
		String formula = getFormula(args);
		if (formula != null) {
			doCalculate(formula);
		} else {
			if (args.length == 0) {
				showFormula = true;
			}
			while ((formula = requestInput()).length() > 0) {
				doCalculate(formula);
			}
		}
	}
	/**
	 * 与えられた数式の計算を行う
	 * @param formula 数式
	 */
	private static void doCalculate(String formula) {
		try {
			Value value = calculator.eval(formula);
			if (showFormula) {
				print("中置記法", calculator.getInfixNotationTokens());
				print("後置記法", calculator.getPostfixNotationTokens());
				analyzer.printStructure(formula);
				System.out.print("計算結果：");
			}
			System.out.println(realValueRequired ? value.getRealValue() : value);
		} catch (InvalidFormulaException e) {
			System.out.println(e.getMessage());
		} catch (ArithmeticException e) {
			System.out.println(e.getMessage());
		}
	}
	/**
	 * コマンドライン引数から、数式を取得する。数式が指定されていない場合は{@code null}を返す。
	 * @param args コマンドライン引数
	 * @return 数式
	 */
	private static String getFormula(String[] args) {
		for (String string : args)
			if (!OPTION_REAL_VALUE.equals(string) && !OPTION_SHOW_FORMULA.equals(string))
				return string;
		return null;
	}
	/**
	 * コマンドライン引数から、実数出力を有効にするかどうかのオプション({@link #OPTION_REAL_VALUE})指定を取得する
	 * @param args コマンドライン引数
	 * @return オプションが有効かどうか
	 */
	private static boolean isRealValueRequired(String[] args) {
		for (String string : args)
			if (OPTION_REAL_VALUE.equals(string))
				return true;
		return false;
	}
	/**
	 * コマンドライン引数から、計算過程出力を有効にするかどうかのオプション({@link #OPTION_SHOW_FORMULA})指定を取得する
	 * @param args コマンドライン引数
	 * @return オプションが有効かどうか
	 */
	private static boolean showFormula(String[] args) {
		for (String string : args)
			if (OPTION_SHOW_FORMULA.equals(string))
				return true;
		return false;
	}
	/**
	 * ユーザーの数式入力を求める。何も入力しなかった場合、そこで処理を終了する。
	 * @return 数式
	 */
	private static String requestInput() {
		System.out.println("数式を入力して下さい。何も入力しない場合は、そのまま終了します。");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			try {
				return reader.readLine();
			} catch (IOException e) { }
		}
	}
	/**
	 * トークン列をスペース区切りで、一行で出力する
	 * @param title タイトル
	 * @param tokens トークン列
	 */
	private static void print(String title, Iterable<Object> tokens) {
		System.out.print(title + ":");
		for (Object token : tokens) {
			System.out.print(" " + token);
		}
		System.out.println();
	}
}
