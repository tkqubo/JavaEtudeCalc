package com.qubo.challenge.calc.tokens;

/**
 * 単項演算子を表現するインターフェース
 * @author Qubo
 */
public interface UnaryOperator extends Operator {
	/**
	 * 単項演算を行う
	 * @param operand オペランド
	 * @return 演算結果
	 */
	Value operate(Value operand);
	/** 負数 */
	static UnaryOperator Neg = new AbstractUnaryOperator(SYMBOL_NEG, PRIORITY_3) {
		@Override
		public Value operate(Value operand) {
			return new Value(-operand.getNumerator(), operand.getDenominator());
		}
	};
	/** 絶対値 */
	static UnaryOperator Abs = new AbstractUnaryOperator(SYMBOL_ABS, PRIORITY_3) {
		@Override
		public Value operate(Value operand) {
			return new Value(Math.abs(operand.getNumerator()), operand.getDenominator());
		}
	};

	/**
	 * 内部用。{@link UnaryOperator}を実装した抽象クラス。
	 * @author Qubo
	 */
	abstract class AbstractUnaryOperator implements UnaryOperator {
		/** 演算子のシンボル */
		private final String symbol;
		/** 演算の優先順位 */
		private final int priority;

		AbstractUnaryOperator(String symbol, int priority) {
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
		public String toString() { return symbol; }

	}
}
