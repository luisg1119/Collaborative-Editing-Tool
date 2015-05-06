package Paint;

import java.awt.event.MouseEvent;

import Server.PaintServer;

public class ReleasedCommand extends PaintCommand<PaintServer>{
	private static final long serialVersionUID = -6028657422847938867L;
	private MouseEvent evt;
	
	public ReleasedCommand(MouseEvent evt){
		this.evt = evt;
	}
	
	public void execute(PaintServer executeOn) {
		// add message to server's chat log
		executeOn.releasedMouse(evt);
	}

}