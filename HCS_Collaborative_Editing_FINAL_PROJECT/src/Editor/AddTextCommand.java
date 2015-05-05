package Editor;

import Server.EditorServer;

/** Description of AddTextCommand:
* This class adds the message using the EditorCommand<EditorSever>, prints with name of client followed by text written.
*@author HCS Group: Siddharth Sharma, Luis Guerrero, Maverick Tudisco, Chintan Patel
*@version Final Version: May 6th, 2015
*/

public class AddTextCommand extends EditorCommand<EditorServer>{
		private static final long serialVersionUID = -4786261013473386911L;
		private String text; // message from client
		private String clientName;
		
		/**
		 * Creates an AddMessageCommand with the given message
		 * 
		 * @param message	message to add to log
		 */
		public AddTextCommand(String name ,String text){
			this.clientName = name;
			this.text = text;
		}
		
		public void execute(EditorServer executeOn) {
			// add message to server's chat log
			executeOn.addText(text, clientName);
		}
		
}
