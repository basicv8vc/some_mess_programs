package item.thirty;

public class WeightTable {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		double earthWeight = Double.parseDouble(args[0]);
		double mass = earthWeight / Planet.EARTH.surfaceGravity();
		for(Planet p: Planet.values())
			System.out.printf("Weight on %s is %f%n",
							  p, p.surfaceWeight(mass));
	}

}
