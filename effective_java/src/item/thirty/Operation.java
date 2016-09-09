package item.thirty;

/**
 * 特定于常量的方法实现(constant-specific method implementation)
 * @author basicv8vc
 *
 */
//public enum Operation {
//	PLUS  { double apply(double x, double y) { return x + y; } },
//	MINUS { double apply(double x, double y) { return x - y; } },
//	TIMES { double apply(double x, double y) { return x * y; } },
//	DIVIDE{ double apply(double x, double y) { return x / y; } };
//	
//	abstract double apply(double x, double y);
//	
//
//}

/**
 * 特定于常量的方法实现可以与特定于常量的数据结合起来
 * @author basicv8vc
 *
 */
public enum Operation {
	PLUS("+") {
		double apply(double x, double y) { return x + y; }
	},
	MINUS("-") {
		double apply(double x, double y) { return x - y; }
	},
	TIMES("*") {
		double apply(double x, double y) { return x * y; }
	},
	DIVIDE("/") {
		double apply(double x, double y) { return x / y; }
	};
	private final String symbol;
	Operation(String symbol) { this.symbol = symbol; }
	
	@Override
	public String toString() { return symbol; }
	
	abstract double apply(double x, double y);
	
}

//class Test {
//	public final String s;
//	public final int b;
//	
//}
//
//enum TestEnum {
//	A("a"), B("b"), C("c");
//	private final String s;
////	private final int b;
//	TestEnum(String s) { this.s = s;}
//}

