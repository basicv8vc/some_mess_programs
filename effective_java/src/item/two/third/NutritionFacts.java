package item.two.third;

/**
 * Builder模式
 * 不直接生成想要的对象，而是让客户端利用所有必要的参数调用构造器或静态工厂，得到一个builder对象，
 * 然后客户端在builder对象上调用类似于setter的方法，来设置每个相关的可选参数，
 * 最后，客户端调用无参的build方法来生成不可变的对象
 * @author basicv8vc
 *
 */
public class NutritionFacts {
	private int servingSize;
	private int servings;
	private int calories;
	private int fat;
	private int sodium;
	private int carbohydrate;   

	public static class Builder {
		//Required parameters
		private int servingSize;
		private int servings;
		
		//Optional parameters - initialized to default values
		private int calories = 0;
		private int fat = 0;
		private int sodium = 0;
		private int carbohydrate = 0;
		
		public Builder(int servingSize, int servings) {
			this.servingSize = servingSize;
			this.servings = servings;
		}
		
		public Builder calories(int val) {
			this.calories = val;
			return this;
		}
		
		public Builder fat(int val) {
			this.fat = val;
			return this;
		}
		
		public Builder sodium(int val) {
			this.sodium = val;
			return this;
		}
		
		public Builder carbohydrate(int val) {
			this.carbohydrate = val;
			return this;
		}
		
		public NutritionFacts build() {
			return new NutritionFacts(this);
		}
	}
		
	private NutritionFacts(Builder builder) {
		servingSize = builder.servingSize;
		servings = builder.servings;
		calories = builder.calories;
		fat = builder.fat;
		sodium = builder.sodium;
		carbohydrate = builder.carbohydrate;
			
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NutritionFacts cocaCola = new NutritionFacts.Builder(240, 8).
				calories(100).sodium(35).carbohydrate(27).build();

	}

}
