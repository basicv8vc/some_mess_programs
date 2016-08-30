package thinking.chapter.eight;

class Base {
	public void method1() {
		System.out.println("Base: method1()");
		method2();
	}
	public void method2() {
		System.out.println("Base: method2()");
	}
}


class Children extends Base {
	@Override
	public void method2() {
		System.out.println("Children: method2()");
	}
}
public class ExerciseTen {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Base b = new Children();
		b.method1();

	}

}
