package Chat;

import java.util.LinkedList;
import java.util.List;

import Chat.ChatClientStart;

/** Description of UpdateChatCommand:
* This class updates the text that the user is entering as a message to all the users on the document editor.
*@author HCS Group: Siddharth Sharma, Luis Guerrero, Maverick Tudisco, Chintan Patel
*@version Final Version: May 6th, 2015
*/

public class UpdateChatCommand extends ChatCommand<ChatClientStart> {
	private static final long serialVersionUID = -3462914452177753474L;
	private List<String> messages; // the message log from the server
		
	public UpdateChatCommand(List<String> messages){
		this.messages = new LinkedList<String>(messages); // note: we are making a copy of the given list
	}
	
	public void execute(ChatClientStart executeOn) {
		executeOn.update(messages);
	}
}
