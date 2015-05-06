package Chat;

import Server.ChatServer;

/** Description of DisconnectChatCommand:
* This class is responsible for taking the actions of the user to disconnect the chat server when prompted to do so.
* This class contains a final long serialVersionUID that creates the thread as a unique identifier and a method called DisconnectChatCommand as well as execut that disconnects the client.
*@author HCS Group: Siddharth Sharma, Luis Guerrero, Maverick Tudisco, Chintan Patel
*@version Final Version: May 6th, 2015
*/

public class DisconnectChatCommand extends ChatCommand<ChatServer>{
	private static final long serialVersionUID = 3443862965322227600L;
	private String clientName; // client who is disconnecting
	
	/**
	 * Creates a disconnect command for the given client
	 * 
	 * @param name	username of client to disconnect
	 */
	public DisconnectChatCommand(String name){
		clientName = name;
	}
	
	@Override
	public void execute(ChatServer executeOn) {
		// disconnect client
		executeOn.disconnectChat(clientName);
	}

}
