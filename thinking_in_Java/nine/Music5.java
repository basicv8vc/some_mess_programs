package thinking.chapter.nine;

interface Instrument {
	int VALUE = 5; //static & final
	void play(Note n); // Automatically public
	void adjust();
}

class Wind implements Instrument {
	public void play(Note n) {
		System.out.println(this + ".play() " + n);
	}
	public String toString() {
		return "Wind";
	}
	public void adjust() {
		System.out.println(this + ".adjust()");
	}
}

class Percussion implements Instrument {
	public void play(Note n) {
		System.out.println(this + ".play() " + n);
	}
	public String toString() {
		return "Percussion";
	}
	public void adjust() {
		System.out.println(this + ".adjust()");
	}
}


class Stringed implements Instrument {
	public void play(Note n) {
		System.out.println(this + ".play() " + n);
	}
	public String toString() {
		return "Stringed";
	}
	public void adjust() {
		System.out.println(this + ".adjust()");
	}
}

class Brass extends Wind {
	@Override
	public String toString() {
		return "Brass";
	}
}

class WoodWind extends Wind {
	@Override
	public String toString() {
		return "WoodWind";
	}
}




public class Music5 {

	static void tune(Instrument i) {
		i.play(Note.MIDDLE_C);
	}
	
	static void tuneAll(Instrument[] e) {
		for (Instrument i: e)
			tune(i);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Instrument[] orchestra = {new Wind(), new Percussion(), new Stringed(), new Brass(),
				new WoodWind()};
		tuneAll(orchestra);
		
	}

}
