package Paint;

import java.io.Serializable;

/** Description of PaintCommand<T>:
* This class called PaintCommand<T> implements Serializable and creates a final long serialVersionUID that creates a unique thread for the PaintCommand.
* It has a void undo method that takes in a type T.
*@author HCS Group: Siddharth Sharma, Luis Guerrero, Maverick Tudisco, Chintan Patel
*@version Final Version: May 6th, 2015
*/

public abstract class PaintCommand<T> implements Serializable {
	private static final long serialVersionUID = -8690760556791062372L;

	public abstract void execute(T executeOn);
			
	public void undo(T undoOn){
	}
}

