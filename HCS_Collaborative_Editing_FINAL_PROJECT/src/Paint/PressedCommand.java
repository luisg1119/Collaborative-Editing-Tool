package Paint;

import java.awt.event.MouseEvent;

import Server.PaintServer;



public class PressedCommand extends PaintCommand<PaintServer>{
	private static final long serialVersionUID = -7729072721500083246L;
	private MouseEvent evt;
	
	public PressedCommand(MouseEvent evt){
		this.evt = evt;
	}
	
	public void execute(PaintServer executeOn) {
		// add message to server's chat log

		executeOn.pressedMouse(evt);
	}

}
