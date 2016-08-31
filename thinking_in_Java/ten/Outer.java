package thinking.chapter.ten;

public class Outer {
	
	private String str;
	
	class Inner {
		public String toString() { //方法要是public的
			return str;
		}
		
	}
	
	public Inner getInner() {
		return new Inner();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Outer u = new Outer();
		u.str = "abc";
		
		Outer.Inner i = u.getInner();
		System.out.println(i.toString());
		
		

	}

}
