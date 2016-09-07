package item.two.second;

/**
 * JavaBeans模式
 * 先调用一个无参构造器来创建对象，然后调用Setter方法来设置每个参数
 * @author basicv8bc
 *
 */
public class NutritionFacts {
	
	private int servingSize = -1;
	private int servings = -1;
	private int calories = 0;
	private int fat = 0;
	private int sodium = 0;
	private int carbohydrate = 0;
	
	public NutritionFacts() { }
	//Setters
	public void setServingSize(int val) {
		servingSize = val;
	}
	
	public void setServings(int val) {
		servings = val;
	}
	
	public void setCalories(int val) {
		calories = val;
	}
	
	public void setFat(int val) {
		fat = val;
	}
	
	public void setSodium(int val) {
		sodium = val;
	}
	
	public void setCarbohydrate(int val) {
		carbohydrate = val;
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NutritionFacts cocaCola = new NutritionFacts();
		cocaCola.setServingSize(240);
		cocaCola.setServings(8);
		cocaCola.setCalories(100);
		cocaCola.setSodium(35);
		cocaCola.setCarbohydrate(27);

	}

}
