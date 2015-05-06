package Paint;

import java.awt.event.MouseEvent;

public class UpdatePaintDraggedCommand extends PaintCommand<PaintClient> {
	private static final long serialVersionUID = 977953653374220285L;
	private MouseEvent evt;

	public UpdatePaintDraggedCommand(MouseEvent evt){
		this.evt = evt;
	}
	
	public void execute(PaintClient executeOn) {
		executeOn.updateDragged(evt);
	}
}
