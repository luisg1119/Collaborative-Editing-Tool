package Editor;

public class UpdateEditorRevisionCommand extends EditorCommand<EditorClient>{
	private static final long serialVersionUID = -9189492686877849950L;
	private String text; // client who is disconnecting
	
	/**
	 * Creates a Revision Update command for the given client
	 * 
	 * @param text 	text of current gui to save
	 */
	public UpdateEditorRevisionCommand(String text){
		this.text = text;
	}
	
	@Override
	public void execute(EditorClient executeOn) {
		executeOn.editorRevisionUpdate(text);
	}
}

