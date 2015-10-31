import java.util.LinkedList;
import java.util.List;

public class User {
	
	//username
	private String username = null;
	//password
	private String password = null;
	//cookie
	private Cookie cookie = null;
	//USERPOOL
//	public static List<User> USERPOOL = new LinkedList<User>();
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @param cookie , may null
	 */
	public User(String username, String password, Cookie cookie){
		this.username = username;
		this.password = password;
		this.cookie = cookie; // It's OK that the cookie is null value 
//		USERPOOL.add(this);
	}
	
	/**
	 * Cookie is null
	 * @param username
	 * @param password
	 */
	public User(String username, String password){
		this.username = username;
		this.password = password;
//		USERPOOL.add(this);
	}
	
	/**
	 * 
	 * @return username
	 */
	public String getName(){
		return username;
	}
	
	/**
	 * 
	 * @param password
	 * @return
	 */
	public boolean checkPassword(String password){
		boolean isMatch = false;
		if(this.password.contentEquals(password))
			isMatch = true;
		return isMatch;
	}
	
	/**
	 * 
	 * @return cookie
	 */
	public Cookie getCookie(){
		return cookie;
	}
	
	/**
	 *  updates teh users session cookie
	 * @param cookie
	 */
	public void setCookie(Cookie cookie){
		this.cookie = cookie;
	}
	
	// TODO complete this class

}
