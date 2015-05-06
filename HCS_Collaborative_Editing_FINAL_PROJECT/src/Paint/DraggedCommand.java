package Paint;

import java.awt.event.MouseEvent;

import Server.PaintServer;

public class DraggedCommand extends PaintCommand<PaintServer>{
	private static final long serialVersionUID = 4253765450318264621L;
	private MouseEvent evt;
	
	public DraggedCommand(MouseEvent evt){
		this.evt = evt;
	}
	
	public void execute(PaintServer executeOn) {
		// add message to server's chat log
		executeOn.draggedMouse(evt);
	}

}