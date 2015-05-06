package Editor;

import Server.EditorServer;

/** Description of AddTextCommand:
* This class adds the message using the EditorCommand<EditorSever>, prints with name of client followed by text written.
* This class contains a final long serialVersionUID that creates the thread as a unique identifier as well as a String text that contains a text which is a message from client.
* It also contains a String with the clientName as well as a String that represents text.
* It contains a constructor that takes in the string name of the client followed by a string of the text entered.
* It also has an execute method and adds a message to the server's chat log.
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
