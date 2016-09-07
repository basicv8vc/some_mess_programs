package thinking.chapter.fourteen;

interface HasBatteries{}
interface Waterproof{}
interface Shoots{}

class Toy {
//	Toy() {}
	Toy(int i) {}
}

class FancyToy extends Toy implements HasBatteries, Waterproof, Shoots {
	FancyToy() {
		super(1);
	}
	
}

public class ToyTest {
	
	static void printInfo(Class cc) {
		System.out.println("Class name: " + cc.getName() + 
				" is interface? [" + cc.isInterface() + "]");
		System.out.println("Simple name: " + cc.getSimpleName());
		System.out.println("Canonical name :" + cc.getCanonicalName());
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Class c = null;
		try {
			c = Class.forName("thinking.chapter.fourteen.FancyToy");
		} catch(ClassNotFoundException e) {
			System.out.println("Can't find FancyToy");
			System.exit(1);
		}
		printInfo(c);
		
		for(Class face: c.getInterfaces())
			printInfo(face);
		
		Class up = c.getSuperclass(); // Class Toy
		Object obj = null;
		try {
			obj = up.newInstance();
		} catch(InstantiationException e) {
			System.out.println("Cannot instantiate");
			System.exit(1);
		} catch(IllegalAccessException e) {
			System.out.println("Cannot access");
			System.exit(1);
		}
		
		printInfo(obj.getClass());

	}

}
