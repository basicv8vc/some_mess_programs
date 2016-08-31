package thinking.chapter.ten;

public class Parcel1 {
	
	Parcel1() {
		System.out.println("Parcel1");
	}
	
	class Contents {
		Contents() {
			System.out.println("Contents");
		}
		private int i = 11;
		public int value() { return i; }
	}
	
	class Destination {
		private String label;
		Destination(String whereTo) {
			this.label = whereTo;
			System.out.println("Destination");
		}
		String readLabel() { return label; }
		
	}// Destination
	
	public void ship(String dest) {
		System.out.println("Parcel1.ship()");
		Contents c = new Contents();
		Destination d = new Destination(dest);
		System.out.println(d.readLabel());
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Parcel1 p = new Parcel1();
		p.ship("China");
		
//		Contents cc = new Contents();
//		Parcel1.Contents ccc = new Contents();
	}

}
