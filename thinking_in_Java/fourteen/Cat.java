package syntax.staticinherit.extend;

class Animal {

	public static void testClassMethod() {
		System.out.println("The static method in Animal");
	}
	public void testInstanceMethod() {
		System.out.println("The instance method in Animal");
	}
	

}

public class Cat extends Animal {
	public static void testClassMethod() {
		System.out.println("The static method in Cat");
	}
	
	public void testInstanceMethod() {
		System.out.println("The instance method in Cat");
	}
	
	public static void main(String[] args) {
		Cat myCat = new Cat();
		Animal myAnimal = myCat;
		
		Cat.testClassMethod();
		Animal.testClassMethod();
		
		myAnimal.testClassMethod();
		myAnimal.testInstanceMethod();
		
	}
}
