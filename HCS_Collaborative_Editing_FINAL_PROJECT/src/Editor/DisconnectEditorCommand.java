package Editor;

import Server.EditorServer;


public class DisconnectEditorCommand extends EditorCommand<EditorServer>{
		private static final long serialVersionUID = -5459574209300377477L;
		private String clientName; // client who is disconnecting
		
		/**
		 * Creates a disconnect command for the given client
		 * 
		 * @param name	username of client to disconnect
		 */
		public DisconnectEditorCommand(String name){
			clientName = name;
		}
		
		@Override
		public void execute(EditorServer executeOn) {
			// disconnect client
			executeOn.disconnectChat(clientName);
		}
	
	
}
