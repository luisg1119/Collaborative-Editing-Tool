package Chat;

import java.io.Serializable;


public abstract class Command<T> implements Serializable {
	private static final long serialVersionUID = -3896673955888941134L;

	public abstract void execute(T executeOn);
		
	public void undo(T undoOn){
	}
}
