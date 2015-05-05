package Chat;

import Server.ChatServer;

/** Description of AddMessageCommand:
* This is where one can send a message using the ChatCommand to send messages through the ChatServer.
*@author HCS Group: Siddharth Sharma, Luis Guerrero, Maverick Tudisco, Chintan Patel
*@version Final Version: May 6th, 2015
*/

public class AddMessageCommand extends ChatCommand<ChatServer>{
	private static final long serialVersionUID = -4786261013473386911L;
	private String message; // message from client
	
	/**
	 * Creates an AddMessageCommand with the given message
	 * 
	 * @param message	message to add to log
	 */
	public AddMessageCommand(String message){
		this.message = message;
	}
	
	public void execute(ChatServer executeOn) {
		// add message to server's chat log
		executeOn.addMessage(message);
	}
	
}