package Chat;

import java.io.Serializable;

/** Description of ChatCommand<T>:
* This is an abstract class called ChatCommand<T> that implements Serializable and creates a final long with a serialVersionUID. 
*@author HCS Group: Siddharth Sharma, Luis Guerrero, Maverick Tudisco, Chintan Patel
*@version Final Version: May 6th, 2015
*/

public abstract class ChatCommand<T> implements Serializable {
	private static final long serialVersionUID = -3896673955888941134L;

	public abstract void execute(T executeOn);
		
	public void undo(T undoOn){
	}
}
