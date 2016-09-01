package syntax.staticinherit.extend;

class A {
	static int a = 1;
	static int b = 2;
	
	public static void printA() {
		System.out.println(a);
	}
	
	public static void printB() {
		System.out.println(b);
	}

}

class B extends A {
	static int a = 3;
	static int b = 4;
	
	public static void printB(){
		System.out.println(b);
	}
	
	public  void printB(int a) {
		System.out.println(b);
	}
}

public class TestStatic {
	public static  void main(String[] args) {
		B.printB();  // 4
		// B继承自A的静态方法，虽然B中也定义了a的值(B.a), 但是因为调用的printA是A中的方法，打印的是A.a
		B.printA();  // 1
		System.out.println(B.a); // 3
		
		A a  = new B();
		a.printB(); // 2
		a.printA(); // 1
		
	}
}

/**
静态变量与静态方法说继承并不确切，静态方法与变量是属于类的方法与变量。而子类也属于超类，
比如说Manage extends Employee，则Manage也是一个Employee，所以子类能够调用属于超类的静态变量和方法。
注意，子类调用的其实就是超类的静态方法和变量，而不是继承自超类的静态方法与变量。
但是如果子类中有同名的静态方法与变量，这时候调用的就是子类本身的，
因为子类的静态变量与静态方法会隐藏父类的静态方法和变量。






*/