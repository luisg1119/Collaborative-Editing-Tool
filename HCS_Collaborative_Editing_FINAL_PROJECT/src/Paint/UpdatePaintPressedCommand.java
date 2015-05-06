package Paint;

import java.awt.event.MouseEvent;


public class UpdatePaintPressedCommand extends PaintCommand<PaintClient> {
	private static final long serialVersionUID = 7181351058926233512L;
	private MouseEvent evt;
				
	public UpdatePaintPressedCommand(MouseEvent evt){
		this.evt = evt;
	}
			
	public void execute(PaintClient executeOn) {
		System.out.println("In UpdatePaintPressedCommand");
		executeOn.updatePressed(evt);
	}
}
