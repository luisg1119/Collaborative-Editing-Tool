package Editor;

import java.util.LinkedList;
import java.util.List;


public class UpdateEditorCommand extends EditorCommand<EditorClientStart> {
		private static final long serialVersionUID = -8023276704073306322L;
		private List<String> messages; // the message log from the server
			
		public UpdateEditorCommand(List<String> messages){
			this.messages = new LinkedList<String>(messages); // note: we are making a copy of the given list
		}
		
		public void execute(EditorClientStart executeOn) {
			executeOn.update(messages);
		}
	
}
