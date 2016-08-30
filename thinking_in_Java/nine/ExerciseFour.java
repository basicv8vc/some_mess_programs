package thinking.chapter.nine;

abstract class AbstractBase {
	
	
}

class NormalClass extends AbstractBase {
	public static void staticMethod(AbstractBase ab) {
		NormalClass na = (NormalClass) ab;
	}
	public void print() {
		staticMethod(new NormalClass());
		
	}
	
}
public class ExerciseFour {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NormalClass na = new NormalClass();
		na.print();
		
		

	}

}
