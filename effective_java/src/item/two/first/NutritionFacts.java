package item.two.first;

/**
 *  重叠构造器(telescoping constructor)模式
 * @author basicv8vc
 *
 */
public class NutritionFacts {
	private int servingSize; //required
	private int servings;    //required
	private int calories;    //optional
	private int fat;
	private int sodium;
	private int carbohydrate;
	
	public NutritionFacts(int servingSize, int servings) {
		this(servingSize, servings, 0);
	}
	
	public NutritionFacts(int servingSize, int servings, int calories) {
		this(servingSize, servings, calories, 0);
	}
	
	public NutritionFacts(int servingSize, int servings, int calories,
			int fat) {
		this(servingSize, servings, calories, fat, 0);
	}
	
	public NutritionFacts(int servingSize, int servings, int calories,
			int fat, int sodium) {
		this(servingSize, servings, calories, fat, sodium, 0);
	}
	
	public NutritionFacts(int servingSize, int servings, int calories,
			int fat, int sodium, int carbohydrate) {
		this.servingSize = servingSize;
		this.servings = servings;
		this.calories = calories;
		this.fat = fat;
		this.sodium = sodium;
		this.carbohydrate = carbohydrate;
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NutritionFacts cocaCola = new NutritionFacts(240, 8, 0, 35, 272);
		
	}

}
