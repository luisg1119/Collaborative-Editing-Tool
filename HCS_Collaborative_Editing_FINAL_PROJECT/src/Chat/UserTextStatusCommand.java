package Chat;

import Server.ChatServer;

/** Description of UserTextStatusCommand:
* This class shows when the user may be typing right above the message bar.
* This contains a constructor as well as a execute method that adds the message to the servers log, it contains a serialVersionUID that creates the thread as a unique identifier.
*@author HCS Group: Siddharth Sharma, Luis Guerrero, Maverick Tudisco, Chintan Patel
*@version Final Version: May 6th, 2015
*/

public class UserTextStatusCommand extends ChatCommand<ChatServer>{
	private static final long serialVersionUID = 7298275904352724414L;
	private String message; // message from client
		
	/**
	 * Creates an AddMessageCommand with the given message
	 * 
	 * @param message	message to add to log
	 */
	public UserTextStatusCommand(String message){
		this.message = message;
	}
		
	public void execute(ChatServer executeOn) {
	// add message to server's log
		executeOn.changeTextStatus(message);
	}
}
