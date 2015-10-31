import java.util.*;

public class ChatBoard {
	
	private List<User> users;
	private int maxMessages;
	private Buffer cbuffer;
	public ChatServer(User[] users, int maxMessages) {
		// TODO Complete the constructor
		this.users = new LinkedList<User>();
		User rootuser = new User("root", "cs180");
		this.users.add(rootuser);

		for(User user: users)
			this.users.add(user);
		this.maxMessages = maxMessages;
		this.cbuffer = new Buffer(this.maxMessages);
		
	}

	/**
	 * This method begins server execution.
	 */
	public void run() {
		boolean verbose = false;
		System.out.printf("The VERBOSE option is off.\n\n");
		Scanner in = new Scanner(System.in);

		while (true) {
			System.out.printf("Input Server Request: ");
			String command = in.nextLine();

			// this allows students to manually place "\r\n" at end of command
			// in prompt
			command = replaceEscapeChars(command);

			if (command.startsWith("kill"))
				break;

			if (command.startsWith("verbose")) {
				verbose = !verbose;
				System.out.printf("VERBOSE has been turned %s.\n\n", verbose ? "on" : "off");
				continue;
			}

			String response = null;
			try {
				response = parseRequest(command);
			} catch (Exception ex) {
				response = MessageFactory.makeErrorMessage(MessageFactory.UNKNOWN_ERROR,
						String.format("An exception of %s occurred.", ex.getMessage()));
			}

			// change the formatting of the server response so it prints well on
			// the terminal (for testing purposes only)
			if (response.startsWith("SUCCESS\t"))
				response = response.replace("\t", "\n");

			// print the server response
			if (verbose)
				System.out.printf("response:\n");
			System.out.printf("\"%s\"\n\n", response);
		}

		in.close();
	}

	/**
	 * Replaces "poorly formatted" escape characters with their proper values.
	 * For some terminals, when escaped characters are entered, the terminal
	 * includes the "\" as a character instead of entering the escape character.
	 * This function replaces the incorrectly inputed characters with their
	 * proper escaped characters.
	 * 
	 * @param str
	 *            - the string to be edited
	 * @return the properly escaped string
	 */
	private static String replaceEscapeChars(String str) {
		str = str.replace("\\r", "\r");
		str = str.replace("\\n", "\n");
		str = str.replace("\\t", "\t");

		return str;
	}

	/**
	 * Determines which client command the request is using and calls the
	 * function associated with that command.
	 * 
	 * @param request
	 *            - the full line of the client request (CRLF included)
	 * @return the server response
	 */
	public String parseRequest(String request) {
		// TODO: Replace the following code with the actual code
		String result;
		if(request.endsWith("\r\n") == false)
			return  MessageFactory.makeErrorMessage(10);//
		else{
			String[] attributes = request.split("\t"); 
			if(attributes[0].contentEquals("ADD-USER")){
				if(attributes.length != 4)
					 return MessageFactory.makeErrorMessage(10);//, "Format Command Error: Miss \t");
				else{
					//verify user's login session through his cookie
					try{
						long id = Integer.valueOf(attributes[1]);
						
						boolean isCookieIDCorrect = false;
						for(User user: users){
							if(user.getCookie() != null){
								if(user.getCookie().getID() == id){
									isCookieIDCorrect = true;
//									username = user.getName();
									if(user.getCookie().hasTimeOut()){
										user.setCookie(null);
										return MessageFactory.makeErrorMessage(05, "Cookie Timeout Error");
									}
								}
							}
						}
						
						if(isCookieIDCorrect == false)
							return MessageFactory.makeErrorMessage(23);//, "Invalid Value Error: the cookie ID is invalid!");
						
						//调用 add-user method
						attributes[3] = attributes[3].replaceAll("\r\n", "");
						return addUser(attributes);
						
					}catch (Exception e){// cookie is null,
						return MessageFactory.makeErrorMessage(23, "Login Error: The specified user has not logged in, so it's not to add other user");
					}
					
				}
				
			}else if (attributes[0].contentEquals("USER-LOGIN")){
				if(attributes.length != 3)
					return MessageFactory.makeErrorMessage(10);//
				else{
					attributes[2] = attributes[2].replaceAll("\r\n", "");
					return userLogin(attributes); //
				}
				
			}else if(attributes[0].contentEquals("POST-MESSAGE")){
				if(attributes.length != 3)
					return MessageFactory.makeErrorMessage(10);//, "Format Command Error: Miss \t");
				else{
					//verify user's login session through his cookie, post
					try{
						long id = Integer.valueOf(attributes[1]);
						boolean isCookieIDCorrect = false;
						String username = null;
						for(User user: users){
							if(user.getCookie() != null){
								if(user.getCookie().getID() == id){
									isCookieIDCorrect = true;
									username = user.getName();
									if(user.getCookie().hasTimeOut()){
										user.setCookie(null);
										return MessageFactory.makeErrorMessage(05, "Cookie Timeout Error");
									}
								}
							}
						}
						if(isCookieIDCorrect == false)
							return MessageFactory.makeErrorMessage(23);//, "Invalid Value Error: the cookie ID is invalid!");
						

						attributes[2] = attributes[2].replaceAll("\r\n", "");
						return postMessage(attributes, username); //调用 post
						

					}catch (Exception e){// cookie is null
						return MessageFactory.makeErrorMessage(23, "Login Error: The specified user has not logged in, so it's not to add other user");
					}
					
				}
				
			
			
			}else if(attributes[0].contentEquals("GET-MESSAGES")){
				if(attributes.length != 3)
					return MessageFactory.makeErrorMessage(10);//, "Format Command Error: Miss \t");
				else{
					try{
						long id = Integer.valueOf(attributes[1]);
						boolean isCookieIDCorrect = false;
						String username = null;
						for(User user: users){
							if(user.getCookie() != null){
								if(user.getCookie().getID() == id){
									isCookieIDCorrect = true;
									username = user.getName();
									if(user.getCookie().hasTimeOut()){
										user.setCookie(null);
										return MessageFactory.makeErrorMessage(05, "Cookie Timeout Error");
									}
								}
							}
						}
						if(isCookieIDCorrect == false)
							return MessageFactory.makeErrorMessage(23);//, "Invalid Value Error: the cookie ID is invalid!");
						
						attributes[2] = attributes[2].replaceAll("\r\n", "");
						return getMessages(attributes); //调用 get

					}catch (Exception e){// cookie is null
						return MessageFactory.makeErrorMessage(23, "Login Error: The specified user has not logged in, so it's not to add other user");
					}
					
				}
			}else{
				if(attributes.length == 1){ //don't contain \t
					return MessageFactory.makeErrorMessage(10);
					
				}
				return MessageFactory.makeErrorMessage(11, "Unknown Command Error: The specified client command doesn't exist");
			
				
			}
			
			

		}
			
		
//		return result;
	}
	
	/**
	 * username and passwords only contain [A-Za-z0-9]
	 * username must be 1-20 char length
	 * password must be 4-40 char length
	 * @param args
	 * @return
	 */
	public String addUser(String[] args){
		//args: command, cookie ID, username, password
		//username has already been taken
		for(User user: users){
			if(user.getName().contentEquals(args[2]))
				return MessageFactory.makeErrorMessage(22, "User Error: The user cannot be created because the username has already been taken");
			
		}
		// username and password's range 
		if(isLetterDigit(args[2]) == false || isLetterDigit(args[3]) == false)
			return MessageFactory.makeErrorMessage(24);
		
		if(args[2].length() > 20 || args[2].length() < 1)
			return MessageFactory.makeErrorMessage(24);
		
		if(args[3].length() > 40 || args[3].length() < 4)
			return MessageFactory.makeErrorMessage(24);
		
		User newuser = new User(args[2], args[3]);
		users.add(newuser);
		//update old user's session cookie
		for(int i=0;i<users.size();i++){
			if(users.get(i).getCookie() != null){
				if(users.get(i).getCookie().getID() == Integer.valueOf(args[1])){
					users.get(i).getCookie().updateTimeOfActivity(); // update Cookie 指的就是仅仅update time吧？不需要改变session id吧？
//					System.out.println();
					break;
					
				}
				
			}
			
		}
		return "SUCCESS\r\n";
		
	}
	
	/**
	 * 
	 * @param str
	 * @return
	 */
	public boolean isLetterDigit(String str){
		for(char ch: str.toCharArray()){
			if(Character.isLetterOrDigit(ch) == false)
				return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @param args
	 * @return
	 */
	public String userLogin(String[] args){
		//command,username,password
		String results = null;
		boolean isAlreadyCreated = false;
		boolean isPasswordCorrect = false;
		User thisuser = null;
		for(User user:users){
			if(user.getName().contentEquals(args[1])){
				isAlreadyCreated = true;
				if(user.checkPassword(args[2])){
					isPasswordCorrect = true;
					thisuser = user; //reference////////////////////////////////////////////////////////////////
				}
				break;
			}
		}
		if(!isAlreadyCreated)
			return MessageFactory.makeErrorMessage(20, "Username Lookup Error: The specified user doesnot not exist");
		if(isAlreadyCreated && (!isPasswordCorrect))
			return MessageFactory.makeErrorMessage(21, "The given password is not correct for the specified user");
		
		//check is the given user already be authenticated(the Cookie associated should be null)
		if(thisuser.getCookie() != null)
			return MessageFactory.makeErrorMessage(25, "The specified user is already logged in the server"); //看来如果timeout, Cookie要设置为null
		
		Cookie newcookie = new Cookie();
		while(true){
			long newid = newcookie.cookieIdentifier();
			boolean isIDAlreadyExist = false;
			for(User user:users){
				if(user.getCookie() != null){
					if(user.getCookie().getID() == newid){
						isIDAlreadyExist = true;
						break;
					}
				}
			}//for
			if(!isIDAlreadyExist){
				results = String.valueOf(newid);
				break;
			}
		}
		newcookie.updateTimeOfActivity();
		thisuser.setCookie(newcookie);
//		System.out.println("SUCCESS\t" + results + "\r\n");
		return "SUCCESS\t" + results + "\r\n";
		
	}
	
	
	/**
	 * 
	 * @param args
	 * @param name
	 * @return
	 */
	public String postMessage(String[] args, String name){
		//command, cookieID,message
		args[2] = args[2].trim(); //remove white space head and tail
		if(args[2].length() < 1)
			return MessageFactory.makeErrorMessage(24, "PostMessage should at least contain 1 chars");
		String newmessage = name + ": " + args[2];
		cbuffer.put(newmessage); //add message to the board
		
		//update old user's session cookie
		for(int i=0;i<users.size();i++){
			if(users.get(i).getName().contentEquals(name)){
					users.get(i).getCookie().updateTimeOfActivity(); // update Cookie
					break;
				
			}
			
		}
		return "SUCCESS\r\n";
	}
	
	
	public String getMessages(String[] args){
		//command, cookie ID, numMessages
		//should not update user's session cookie   
		int numMessages = Integer.valueOf(args[2]);
		if(numMessages < 1)
			return MessageFactory.makeErrorMessage(24, "Error: numMessage < 1");
		
		String[] results = cbuffer.getNewest(numMessages);
		if(results.length == 0)
			return "SUCCESS\r\n";
		else{
			String line = "SUCCESS";
			for(String message: results){
				line += "\t";
				line += message;
			}
			line += "\r\n";
			return line;
			
		}
	}
}
