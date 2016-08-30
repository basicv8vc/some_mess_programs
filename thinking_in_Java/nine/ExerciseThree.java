package thinking.chapter.nine;

abstract class Base {
	public abstract void print();
	Base() {
		this.print();
	}
}

class Children extends Base {
	private int i = 2;
	public void print() {
		System.out.println(i);
	}
}

public class ExerciseThree {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Children child = new Children(); //第一步 为对象分配空间, 初始化为0
		child.print();

	}

}
