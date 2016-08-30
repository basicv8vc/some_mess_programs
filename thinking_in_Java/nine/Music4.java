//package thinking.chapter.nine;
//
//abstract class Abstract {
//	
//}
//
//abstract class Instrument {
//	private int i; // Storage allocated for each
//	public abstract void play(Note n);
//	public String what() { return "Instrument"; }
//	public abstract void adjust();
//}
//
//class Wind extends Instrument {
//	public void play(Note n) {
//		System.out.println("Wind.play() " + n);
//	}
//	public String what() { return "Wind"; }
//	public void adjust() {}
//}
//
//class Percussion extends Instrument {
//	public void play(Note n) {
//		System.out.println("Percussion.play() "  + n);
//	}
//	public String what() { return "Percussion"; }
//	public void adjust() {}
//}
//
//class Stringed extends Instrument {
//	public void play(Note n) {
//		System.out.println("Stringed.play() "  + n);
//	}
//	public String what() { return "Stringed"; }
//	public void adjust() {}
//}
//
//class Brass extends Wind {
//	public void play(Note n) {
//		System.out.println("Brass.play() " + n);
//	}
//	public void adjust() {
//		System.out.println("Brass.adjust()");
//	}
//}
//
//class WoodWind extends Wind {
//	public void play(Note n) {
//		System.out.println("WoodWind.play() "  + n);
//	}
//	public String what() { return "WoodWind"; }
//}
//
//public class Music4 {
//	
//	static void tune(Instrument i) {
//		i.play(Note.MIDDLE_C);
//	}
//	static void tuneAll(Instrument[] e) {
//		for(Instrument i : e)
//			tune(i);
//	}
//
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		Instrument[] orchestra = { new Wind(), new Percussion(), new Stringed(), new Brass(),
//				new WoodWind() };
//		tuneAll(orchestra);
//
//	}
//
//}