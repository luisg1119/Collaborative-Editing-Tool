package Editor;

import Server.EditorServer;

public class RevisionCommand extends EditorCommand<EditorServer>{
	private static final long serialVersionUID = -3900115710175428446L;
	private String text; 
	
	/**
	 * Creates a Revision command for the given client
	 * 
	 * @param name	text of current gui to save
	 */
	public RevisionCommand(String text){
		this.text = text;
	}
	
	@Override
	public void execute(EditorServer executeOn) {
		// disconnect client
		executeOn.editorRevision(text);
	}
}
