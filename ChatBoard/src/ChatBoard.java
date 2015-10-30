import java.util.*;


public class CharBoard {
	
	private List<User> users;
	private int maxMessages;
	
	public CharBoard(User[] users, int maxMessages) {
		// TODO Complete the constructor
		User rootuser = new User("root", "cs180");
		this.users.add(rootuser);
		this.users = new LinkedList<User>();
		for(User user: users)
			this.users.add(user);
		this.maxMessages = maxMessages;
		
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
		//怎么区分10和11
		//10: 格式错误, 包括不用\r\n结尾，以及用错tab位置，位置不能在命令前和\t\r\n， tab个数用错属于参数个数错误吧？
		String result;
		if(request.endsWith("\r\n")==false)
			result = MessageFactory.makeErrorMessage(10, "Format Command Error: dosen't ends with \r\n");
		else{
			String[] attributes = request.split("\t"); //ADD-USERPar1Par2Par3 这种也归于 11了
			if(attributes[0].contentEquals("ADD-USER")){
				if(attributes.length != 4)
					result = MessageFactory.makeErrorMessage(10, "Format Command Error: Miss \t");
				else{
					//verify user's login session through his cookie, 添加用户只能是(logged in to the server's)老用户
					//由cookie ID怎么得到对应的cookie? 看来只能通过 IDPOOL ?
					try{
						long id = Integer.valueOf(attributes[1]); //先判断第一个参数
						int i = 0;
						for(;i<SessionCookie.COOKIEPOOL.size();i++){
							if(id == SessionCookie.COOKIEPOOL.get(i).getID()){
								boolean isTimeOut = SessionCookie.COOKIEPOOL.get(i).hasTimeOut();
								if(isTimeOut)
									result = MessageFactory.makeErrorMessage(05, "Cookie Timeout Error: ");
								break;
							}//fi
							
						}
						if(i == SessionCookie.COOKIEPOOL.size()){//给的cookie id是错的, 根本不存在
							result = MessageFactory.makeErrorMessage(24, "Invalid Value Error: the cookie ID is invalid!");
							
						}
						
						//调用 add-user method
						attributes[3] = attributes[3].replaceAll("\r\n", "");
						result = addUser(attributes);
						
					}catch (Exception e){// cookie is null, 这里参数是cookie id，怎么判断是不是null?
						result = MessageFactory.makeErrorMessage(23, "Login Error: The specified user has not logged in, so it's not to add other user");
					}
					
				}
				
			}else if (attributes[0].contentEquals("USER-LOGIN")){
				if(attributes.length != 3)
					result = MessageFactory.makeErrorMessage(10, "Format Command Error: Miss \t");
				else{
					//调用 user-login
				}
				
			}else if(attributes[0].contentEquals("POST-MESSAGE")){
				if(attributes.length != 3)
					result = MessageFactory.makeErrorMessage(10, "Format Command Error: Miss \t");
				else{
					//verify user's login session through his cookie, post
					//由cookie ID怎么得到对应的cookie? 看来只能通过 IDPOOL ?
					try{
						long id = Integer.valueOf(attributes[1]);
						int i = 0;
						for(;i<SessionCookie.COOKIEPOOL.size();i++){
							if(id == SessionCookie.COOKIEPOOL.get(i).getID()){
								boolean isTimeOut = SessionCookie.COOKIEPOOL.get(i).hasTimeOut();
								if(isTimeOut)
									result = MessageFactory.makeErrorMessage(05, "Cookie Timeout Error: ");
								break;
							}//fi
							
						}
						if(i == SessionCookie.COOKIEPOOL.size()){//给的cookie id是错的, 根本不存在
							result = MessageFactory.makeErrorMessage(24, "Invalid Value Error: the cookie ID is invalid!");
							
						}
						
						//调用 post 
						
					}catch (Exception e){// cookie is null, 这里参数是cookie id，怎么判断是不是null?
						result = MessageFactory.makeErrorMessage(23, "Login Error: The specified user has not logged in, so it's not to add other user");
					}
					
				}
				
			
			
			}else if(attributes[0].contentEquals("GET-MESSAGE")){
				if(attributes.length != 3)
					result = MessageFactory.makeErrorMessage(10, "Format Command Error: Miss \t");
				else{
					//verify user's login session through his cookie, post
					//由cookie ID怎么得到对应的cookie? 看来只能通过 IDPOOL ?
					try{
						long id = Integer.valueOf(attributes[1]);
						int i = 0;
						for(;i<SessionCookie.COOKIEPOOL.size();i++){
							if(id == SessionCookie.COOKIEPOOL.get(i).getID()){
								boolean isTimeOut = SessionCookie.COOKIEPOOL.get(i).hasTimeOut();
								if(isTimeOut)
									result = MessageFactory.makeErrorMessage(05, "Cookie Timeout Error: ");
								break;
							}//fi
							
						}
						if(i == SessionCookie.COOKIEPOOL.size()){//给的cookie id是错的, 根本不存在
							result = MessageFactory.makeErrorMessage(24, "Invalid Value Error: the cookie ID is invalid!");
							
						}
						
						//调用 get
						
					}catch (Exception e){// cookie is null, 这里参数是cookie id，怎么判断是不是null?
						result = MessageFactory.makeErrorMessage(23, "Login Error: The specified user has not logged in, so it's not to add other user");
					}
					
				}
			}else{
				result = MessageFactory.makeErrorMessage(11, "Unknown Command Error: The specified client command doesn't exist");
			
				
			}
			
			

		}
			
		
		return result;
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
					users.get(i).getCookie().updateTimeOfActivity(); // update sessioncookie 指的就是仅仅update time吧？不需要改变session id吧？
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
		
		
	}
}
