package Editor;

import java.util.LinkedList;
import java.util.List;


public class UpdateEditorCommand extends EditorCommand<EditorClientStart> {
		private static final long serialVersionUID = -8023276704073306322L;
		private List<String> texts; // the message log from the server
			
		public UpdateEditorCommand(List<String> texts){
			this.texts = new LinkedList<String>(texts); // note: we are making a copy of the given list
		}
		
		public void execute(EditorClientStart executeOn) {
			executeOn.update(texts);
		}
	
}
