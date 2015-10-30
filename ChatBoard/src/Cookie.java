import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SessionCookie {

	//session id, long type
	private long id;
	//timeout length, value represents seconds
	public static int timeoutLength = 300;
	//last user action time
	private long userActionStartTime;

	public SessionCookie(long id){
		this.id = id;
	}
	
	public SessionCookie(){
		this.id = -1;
	}
	
	
	/**
	 * 
	 * @return cookie ID
	 */
	public long cookieIdentifier(){
		Random rand = new Random();
		id = rand.nextInt(10000); //怎么判断这个id 和以前的不冲突, 交给 ChatServer 
		
		return id;
		
		
	}
	
	
	/**
	 * determine if the user login session has ended and if the cookie hash timed out
	 * @return true if timeout, otherwise else.
	 */
	public boolean hasTimeOut(){
		boolean isTimeOut = false;
		
		long currentTime = System.currentTimeMillis();
		int activeSeconds = (int)(currentTime - userActionStartTime) / 1000;
		
		if(activeSeconds > timeoutLength)
			isTimeOut = true;
		
		return isTimeOut;
		
	}
	
	/**
	 * 
	 * update the cookie's time of last activity by setting it to the current time,
	 */
	public void updateTimeOfActivity(){
		userActionStartTime = System.currentTimeMillis();
		int i=0;
	
	}
	
	/**
	 * 
	 * @return the ID of the cookie
	 */
	public long getID(){
		return id;
	}


}
