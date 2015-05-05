package Editor;

import java.io.Serializable;

/** Description of EditorCommand<T>:
* This class is called EditorClientStart that is a client that launches the editor panel.
*@author HCS Group: Siddharth Sharma, Luis Guerrero, Maverick Tudisco, Chintan Patel
*@version Final Version: May 6th, 2015
*/

public abstract class EditorCommand<T> implements Serializable {
	private static final long serialVersionUID = -8690760556791062372L;

	public abstract void execute(T executeOn);
		
	public void undo(T undoOn){
	}
}
