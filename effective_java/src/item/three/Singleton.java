package item.three;

// Singleton with public static final field
class Elvis {
	public static final Elvis instance = new Elvis();
	private Elvis() { }
}

//Singleton with static factory
class Tom {
	private static final Tom instance = new Tom();
	private Tom() { }
	public static Tom getInstance() {
		return instance;
	}
}

//Enum singleton - the preferred approach
enum Jack {
	instance;
	public void leaveTheBuilding() { }
}
/**
 * 
 * An enum type is a special type of class type. Your enum declaration actually compiles to something like
 *
 *   public final class MySingleton {
 *   	public final static MySingleton INSTANCE = new MySingleton(); 
 *	}
 *
 */

public class Singleton {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
