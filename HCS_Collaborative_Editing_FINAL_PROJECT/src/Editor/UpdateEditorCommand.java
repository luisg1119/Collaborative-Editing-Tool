package Editor;

import java.util.LinkedList;
import java.util.List;

/** Description of UpdateEditorCommand:
* This class called UpdateEditorCommand extends EditorCommand<EditorClientStart> and Updates the users editor commands.
* It has 3 instance variables, final long serialVersionUID that creates the thread as a unique identifier, String text, String name. A copy of the name and text is saved.
* There is also a execute method that updates the text and name.
*@author HCS Group: Siddharth Sharma, Luis Guerrero, Maverick Tudisco, Chintan Patel
*@version Final Version: May 6th, 2015
*/

public class UpdateEditorCommand extends EditorCommand<EditorClient> {
		private static final long serialVersionUID = -8023276704073306322L;
		private String text;
		private String name;
			
		public UpdateEditorCommand(String text, String name){
			this.text = text;
			this.name = name;
		}
		
		public void execute(EditorClient executeOn) {
			executeOn.update(text,name);
		}
	
}
