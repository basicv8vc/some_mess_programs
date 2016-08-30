package thinking.chapter.eight;

class Useful {
	public void f() {}
	public void g() {}
}

class MoreUseful extends Useful {
	public void m() {System.out.println("m()");}
}

public class RTTI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Useful uf = new MoreUseful();
//		uf.m(); // uf 已经被转型成了Useful, 没有m()
		MoreUseful muf = new MoreUseful();
		muf.m();
	}

}
