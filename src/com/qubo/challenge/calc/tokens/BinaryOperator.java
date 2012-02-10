package com.qubo.challenge.calc.tokens;


/**
 * 二項演算子を表現するインターフェース
 * @author Qubo
 */
public interface BinaryOperator extends Operator {
	/**
	 * 二項演算を行う
	 * @param operand1 オペランド１
	 * @param operand2 オペランド２
	 * @return 演算結果
	 */
	Value operate(Value operand1, Value operand2);

	/** 加算 */
	static BinaryOperator Add = new AbstractBinaryOperator(SYMBOL_ADD, PRIORITY_1) {
		@Override
		public Value operate(Value operand1, Value operand2) {
			int lcmOfDenominator = Function.lcm(operand1.getDenominator(), operand2.getDenominator());
			int num1 = operand1.getNumerator() * lcmOfDenominator / operand1.getDenominator();
			int num2 = operand2.getNumerator() * lcmOfDenominator / operand2.getDenominator();
			return new Value(num1 + num2, lcmOfDenominator);
		}
	};
	/** 減算 */
	final static BinaryOperator Sub = new AbstractBinaryOperator(SYMBOL_SUB, PRIORITY_1) {
		@Override
		public Value operate(Value operand1, Value operand2) {
			int lcmOfDenominator = Function.lcm(operand1.getDenominator(), operand2.getDenominator());
			int num1 = operand1.getNumerator() * lcmOfDenominator / operand1.getDenominator();
			int num2 = operand2.getNumerator() * lcmOfDenominator / operand2.getDenominator();
			return new Value(num1 - num2, lcmOfDenominator);
		}
	};
	/** 乗算 */
	final static BinaryOperator Mul = new AbstractBinaryOperator(SYMBOL_MUL, PRIORITY_2) {
		@Override
		public Value operate(Value operand1, Value operand2) {
			return new Value(operand1.getNumerator() * operand2.getNumerator(), operand1.getDenominator() * operand2.getDenominator());
		}
	};
	/** 除算 */
	final static BinaryOperator Div = new AbstractBinaryOperator(SYMBOL_DIV, PRIORITY_2) {
		@Override
		public Value operate(Value operand1, Value operand2) {
			return new Value(operand1.getNumerator() * operand2.getDenominator(), operand1.getDenominator() * operand2.getNumerator());
		}
	};

	/**
	 * 内部用。{@link BinaryOperator}を実装した抽象クラス。
	 * @author Qubo
	 */
	abstract class AbstractBinaryOperator implements BinaryOperator {
		/** 演算子のシンボル */
		private final char symbol;
		/** 演算の優先順位 */
		private final int priority;

		AbstractBinaryOperator(char symbol, int priority) {
			this.symbol = symbol;
			this.priority = priority;
		}
		/*
		 * (非 Javadoc)
		 * @see com.qubo.caea0121.calc.tokens.Operator#getPriority()
		 */
		@Override
		public int getPriority() { return priority; }
		/*
		 * (非 Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() { return "" + symbol; }

	}
}
