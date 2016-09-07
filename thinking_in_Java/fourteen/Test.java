package thinking.chapter.fourteen;

class A {
	static {
		System.out.println("A");
	}
}

class B extends A {
	static {
		System.out.println("B");
	}
}
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		try {
//			Class.forName("thinking.chapter.fourteen.B");
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		B b = new B();
	}

}
