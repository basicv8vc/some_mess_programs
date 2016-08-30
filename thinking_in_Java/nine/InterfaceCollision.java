package thinking.chapter.nine;

interface I1 {
	void f();
}

interface I2 {
	int f(int i);
	int i = 1;
}

interface I3 {
	int f();
}

class CCC {
	public int f() { return 1; }
}

class C2 implements I1, I2 {
	public void f() {}
	public int f(int i) { return 1; }
}

class C3 extends CCC implements I2 {
	public int f(int i) { return 1; }
}

class C4 extends CCC implements I3 {
//	public int f() { return 1; } 
}

//class C5 extends CCC implements I1 {
//	
//}

//interface I4 extends I1, I3 {
//	
//}




public class InterfaceCollision {
	public static final int x = 9;
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
