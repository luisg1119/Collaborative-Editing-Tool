package Editor;

import java.util.LinkedList;
import java.util.List;


public class UpdateEditorCommand extends EditorCommand<EditorClient> {
		private static final long serialVersionUID = -8023276704073306322L;
		private String text;
		private List<String> texts; // the message log from the server
			
		public UpdateEditorCommand(String text){//List<String> texts){
			this.text = text;
			//this.texts = new LinkedList<String>(texts); // note: we are making a copy of the given list
		}
		
		public void execute(EditorClient executeOn) {
			executeOn.update(text);//(texts);
		}
	
}
