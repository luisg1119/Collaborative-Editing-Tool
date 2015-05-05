package Editor;

import Server.EditorServer;

public class SaveEditorCommand extends EditorCommand<EditorServer>{
	private String text;
	
	public SaveEditorCommand(String text){
		this.text = text;
	}
	@Override
	public void execute(EditorServer executeOn) {
		executeOn.saveText(text);
		
	}

}
