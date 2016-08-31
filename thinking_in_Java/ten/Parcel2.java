package thinking.chapter.ten;

public class Parcel2 {
	
	class Contents {
		private int i = 11;
		public int value() { return i; }
	}
	
	class Destination {
		private String label;
		Destination(String whereTo) {
			label = whereTo;
		}
		String readLabel() { return label; }
	}
	
	public Destination to(String s) { //返回指向Destination的引用
		return new Destination(s);
	}
	
	public Contents contents() {
		return new Contents();
	}
	
	public void ship(String dest) {
		Contents c = contents(); //这里可以直接new 内部类对象
		Destination d = to(dest);
		System.out.println(d.readLabel());
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Parcel2 p = new Parcel2();
		p.ship("China");
		
		Parcel2 q = new Parcel2();
		Parcel2.Contents c = q.contents();
		Parcel2.Destination d = q.to("BeiJing");
		
		Parcel2 w = new Parcel2();
		Parcel2.Contents cc = w.new Contents();
		Parcel2.Destination ddd = w.new Destination("TianJin");
		
		

	}

}
