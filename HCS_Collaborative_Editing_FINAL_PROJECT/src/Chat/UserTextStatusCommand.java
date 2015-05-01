package Chat;

import Server.ChatServer;

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
