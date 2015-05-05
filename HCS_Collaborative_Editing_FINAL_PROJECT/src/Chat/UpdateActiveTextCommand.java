package Chat;

import java.util.LinkedList;
import java.util.List;

/** Description of UpdateActiveTextCommand:
* The UpdateActiveTextCommand updates the text that a user is entering from their given server to display on everyones computer at the same time.
*@author HCS Group: Siddharth Sharma, Luis Guerrero, Maverick Tudisco, Chintan Patel
*@version Final Version: May 6th, 2015
*/

public class UpdateActiveTextCommand extends ChatCommand<ChatClientStart> {
	private static final long serialVersionUID = -3462914452177753474L;
	private String messages; // the message log from the server
			
	public UpdateActiveTextCommand(String message){
		this.messages = message; // note: we are making a copy of the given list
	}
		
	public void execute(ChatClientStart executeOn) {
		executeOn.updateActive(messages);
	}
}
