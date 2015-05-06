package Editor;

import Server.EditorServer;

/** Description of DisconnectEditorCommand:
* This class is called DisconnectEditorCommand and it extends EditorCommand<EditorServer>. 
* It has a serialVersionUID that has a disconnect command that disconnects the user from the editor pane.
* It has a constructor that takes in a String thats called name and it represents the client name.
* It also has an execute method that takes in a EditorServer and it will disconnect the editor.
*@author HCS Group: Siddharth Sharma, Luis Guerrero, Maverick Tudisco, Chintan Patel
*@version Final Version: May 6th, 2015
*/

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
			executeOn.disconnectEditor(clientName);
		}
	
	
}
