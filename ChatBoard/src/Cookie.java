import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Cookie {

	//session id, long type
	private long id;
	//timeout length, value represents seconds
	public static int timeoutLength = 300;
	//last user action time
	private long userActionStartTime;

	public Cookie(long id){
		this.id = id;
		this.userActionStartTime = System.currentTimeMillis(); 
	}
	
	public Cookie(){
		this.id = -1;
	}
	
	
	/**
	 * 
	 * @return cookie ID
	 */
	public long cookieIdentifier(){
		Random rand = new Random();
		id = rand.nextInt(10000); 
		
		return id;
		
		
	}
	
	
	/**
	 * determine if the user login session has ended and if the cookie hash timed out
	 * @return true if timeout, otherwise else.
	 */
	public boolean hasTimeOut(){
		boolean isTimeOut = false;
		
		long currentTime = System.currentTimeMillis();
		long activeSeconds = currentTime - userActionStartTime;
		
		if(activeSeconds >= (timeoutLength*1000))
			isTimeOut = true;
		
		return isTimeOut;
		
	}
	
	/**
	 * 
	 * update the cookie's time of last activity by setting it to the current time,
	 */
	public void updateTimeOfActivity(){
		userActionStartTime = System.currentTimeMillis();
	
	}
	
	/**
	 * 
	 * @return the ID of the cookie
	 */
	public long getID(){
		return id;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
