/**
 * <b> CS 180 - Project 4 - Message Factory </b>
 * <p>
 * 
 * This is the error message factory class.
 *
 */
public class MessageFactory {
	
	public static final int UNKNOWN_ERROR = 0;
	public static final int COOKIE_TIMEOUT_ERROR = 5;
	
	public static final int FORMAT_COMMAND_ERROR = 10;
	public static final int UNKNOWN_COMMAND_ERROR = 11;
	
	public static final int USERNAME_LOOKUP_ERROR = 20;
	public static final int AUTHENTICATION_ERROR = 21;
	public static final int USER_ERROR = 22;
	public static final int LOGIN_ERROR = 23;
	public static final int INVALID_VALUE_ERROR = 24;
	public static final int USER_CONNECTED_ERROR = 25;
	
	/**
	 * Creates a "FAILURE" server response based on the error code
	 * and appends the user message to the end of the response.
	 * 
	 * @param errorCode - the generic error that occurred
	 * @param customMessage - the message describing the error
	 * @return a fully formatted server failure response or null if 
	 */
	public static String makeErrorMessage(int errorCode, String customMessage) {
		StringBuilder ret = new StringBuilder("FAILURE\t");
		ret.append(errorCode);
		ret.append("\t");
		
		switch(errorCode) {
			case UNKNOWN_ERROR:
				ret.append("Unknown Error: ");
				break;
				
			case COOKIE_TIMEOUT_ERROR:
				ret.append("Cookie Timeout Error: ");
				break;
				
			case FORMAT_COMMAND_ERROR:
				ret.append("Format Command Error: ");
				break;
				
			case UNKNOWN_COMMAND_ERROR:
				ret.append("Unknown Command Error: ");
				break;
				
			case USERNAME_LOOKUP_ERROR:
				ret.append("Username Lookup Error: ");
				break;
				
			case AUTHENTICATION_ERROR:
				ret.append("Authentication Error: ");
				break;
				
			case USER_ERROR:
				ret.append("User Error: ");
				break;
				
			case LOGIN_ERROR:
				ret.append("Login Error: ");
				break;
				
			case INVALID_VALUE_ERROR:
				ret.append("Invalid Value Error: ");
				break;
				
			case USER_CONNECTED_ERROR:
				ret.append("User Connected Error: ");
				break;
				
			default:
				return makeErrorMessage(0, String.format(
				"The error code \"%02d\" is not recognized.", errorCode));
		}
		
		ret.append(customMessage);
		ret.append("\r\n");
		
		return ret.toString();
	}
	
	public static String makeErrorMessage(int errorCode) {
		String message = "";
		
		switch(errorCode) {
			case UNKNOWN_ERROR:
				message = "An unknown error occurred. This was likely caused by an uncaught exception.";
				break;
				
			case COOKIE_TIMEOUT_ERROR:
				message = "Your login cookie has timed out.";
			break;
			
			case FORMAT_COMMAND_ERROR:
				message = "The specified client command isn't formatted properly.";
				break;
			
			case UNKNOWN_COMMAND_ERROR:
				message = "The specified client command doesn't exist.";
				break;

			case USERNAME_LOOKUP_ERROR:
				message = "The specified user does not exist.";
				break;
				
			case AUTHENTICATION_ERROR:
				message = "The given password is not correct for the specified user.";
				break;
				
			case USER_ERROR:
				message = "The user cannot be created because the username has already been taken.";
				break;
				
			case LOGIN_ERROR:
				message = "The specified user has not logged in to the server.";
				break;
				
			case INVALID_VALUE_ERROR:
				message = "One of the specified values is logically invalid.";
				break;
				
			case USER_CONNECTED_ERROR:
				message = "User Connected Error: The specified user is already logged in the server.";
				break;
		}
		
		return makeErrorMessage(errorCode, message.toString());
	}

}
