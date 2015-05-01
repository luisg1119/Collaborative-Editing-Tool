package Chat;

import java.util.LinkedList;
import java.util.List;

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
