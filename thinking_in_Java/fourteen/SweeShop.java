package thinking.chapter.fourteen;

class Candy {
	static {
		System.out.println("Loading Candy");
	}
}

class Gum {
	static {
		System.out.println("Loading Gum");
	}
}

class Cookie {
	static {
		System.out.println("Loading Cookie");
	}
}

public class SweeShop {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("inside main");
		new Candy();
		System.out.println("After creating Candy");
		try {
			Class.forName("thinking.chapter.fourteen.Gum"); //动态加载类的一种方法
//			
		} catch(ClassNotFoundException e) {
			System.out.println("Couldn't find Gum");
		}
		System.out.println("After Class.forName(\"Gum\")");
		new Cookie();
		System.out.println("After creating Cookie");
	}

}
