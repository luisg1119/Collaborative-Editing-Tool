package Editor;

import java.io.Serializable;


public abstract class EditorCommand<T> implements Serializable {
	private static final long serialVersionUID = -8690760556791062372L;

	public abstract void execute(T executeOn);
		
	public void undo(T undoOn){
	}
}
