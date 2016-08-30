package thinking.chapter.nine;

interface Service {
	void method1();
	void method2();
}

interface ServiceFactory {
	Service getService();
}

/**
 *  接口 Service的一种实现
 * @author lj
 *
 */
class Implementation1 implements Service {
	Implementation1() {}
	public void method1(){
		System.out.println("Implementation1 method");
	}
	public void method2() {
		System.out.println("Implementation1 method2");
	}
}

/**
 *  接口Service的另一种实现
 * @author lj
 *
 */
class Implementation2 implements Service {
	Implementation2() {}
	public void method1() {
		System.out.println("Implementation2 method1");
	}
	public void method2() {
		System.out.println("Implementation2 method2");
	}
}


class Implementation1Factory implements ServiceFactory {
	public Service getService(){
		return new Implementation1();
	}
}

class Implementation2Factory implements ServiceFactory {
	public Service getService(){
		return new Implementation2();
	}
}
public class Factories {
	public static void serviceConsumer(ServiceFactory fact) {
		Service ser = fact.getService();
		ser.method1();
		ser.method2();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		serviceConsumer(new Implementation1Factory());
		serviceConsumer(new Implementation2Factory());
		

	}

}
