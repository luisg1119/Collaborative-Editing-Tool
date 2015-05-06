package Editor;

import java.io.Serializable;

/** Description of EditorCommand<T>:
* This class is called EditorCommand<T> that is a client that launches the editor panel.
* This class creates a final long serialVersionUID that creates the thread as a unique identifier.
* There is an abstract class that is called execute that takes in object T called executeOn.
* There is also a undo method that takes in an object T called undoOn
*@author HCS Group: Siddharth Sharma, Luis Guerrero, Maverick Tudisco, Chintan Patel
*@version Final Version: May 6th, 2015
*/

public abstract class EditorCommand<T> implements Serializable {
	private static final long serialVersionUID = -8690760556791062372L;

	public abstract void execute(T executeOn);
		
	public void undo(T undoOn){
	}
}
