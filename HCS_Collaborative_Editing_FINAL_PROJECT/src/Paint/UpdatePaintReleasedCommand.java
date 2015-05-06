package Paint;

import java.awt.event.MouseEvent;


public class UpdatePaintReleasedCommand extends PaintCommand<PaintClient> {
	private static final long serialVersionUID = -2220393438142445549L;
	private MouseEvent evt;
	
	public UpdatePaintReleasedCommand(MouseEvent evt){
		this.evt = evt;
	}
		
	public void execute(PaintClient executeOn) {
		executeOn.updateReleased(evt);
	}
}
