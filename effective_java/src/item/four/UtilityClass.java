package item.four;

//Noninstantiable utility class
public class UtilityClass {
	//通过私有构造器强化不可实例化的能力
	private UtilityClass() {
		throw new AssertionError();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
