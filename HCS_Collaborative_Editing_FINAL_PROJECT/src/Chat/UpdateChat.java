package Chat;

import java.util.LinkedList;
import java.util.List;

import Chat.ChatClientStart;


public class UpdateChat extends Command<ChatClientStart> {
	private static final long serialVersionUID = -3462914452177753474L;
	private List<String> messages; // the message log from the server
		
	public UpdateChat(List<String> messages){
		this.messages = new LinkedList<String>(messages); // note: we are making a copy of the given list
	}
	
	public void execute(ChatClientStart executeOn) {
		executeOn.update(messages);
	}
}
