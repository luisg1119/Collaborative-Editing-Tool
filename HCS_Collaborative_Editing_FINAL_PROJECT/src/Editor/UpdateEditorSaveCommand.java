package Editor;

import java.util.List;



public class UpdateEditorSaveCommand extends EditorCommand<EditorClient> {
		private static final long serialVersionUID = -1047878828220029561L;
		private List<String> texts; // the message log from the server

		public UpdateEditorSaveCommand(List<String> texts){
			this.texts = texts;
		}
		
		@Override
		public void execute(EditorClient executeOn) {
			executeOn.updateSave(texts);
		}

}
