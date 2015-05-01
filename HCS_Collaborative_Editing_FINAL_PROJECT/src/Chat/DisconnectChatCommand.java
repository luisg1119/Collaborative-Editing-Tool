package Chat;

import Server.ChatServer;

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
