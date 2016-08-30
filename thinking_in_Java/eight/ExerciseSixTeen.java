package thinking.chapter.eight;

class AlertStatus {
	public void performance() {
		System.out.println("AlertStatus");
	}
}

class PositiveStatus extends AlertStatus {
	@Override
	public void performance() {
		System.out.println("PositiveStatus");
	}
}

class NegativeStatus extends AlertStatus {
	@Override
	public void performance() {
		System.out.println("NegativeStatus");
	}
}

class NormalStatus extends AlertStatus {
	@Override
	public void performance() {
		System.out.println("NormalStatus");
	}
}

class StarShip {
	private AlertStatus as = new PositiveStatus();
	public void change1() {
		as = new NegativeStatus();
	}
	public void change2() {
		as = new NormalStatus();
	}
	public void getPerformance() {
		as.performance();
	}
	
}

public class ExerciseSixTeen {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StarShip ss = new StarShip();
		ss.getPerformance();
		ss.change1();
		ss.getPerformance();
		ss.change2();
		ss.getPerformance();

	}

}
