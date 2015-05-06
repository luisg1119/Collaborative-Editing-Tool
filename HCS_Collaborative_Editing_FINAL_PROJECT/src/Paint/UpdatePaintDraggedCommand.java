package Paint;

import java.awt.event.MouseEvent;

/** Description of UpdatePaintDraggedCommand:
* This class called UpdatePaintDraggedCommand extends PaintCommand<PaintClient> and is the command that executes this method to check as the cursor is dragged.
* It creates a final long serialVersionUID that creates a unique thread for the command.
* It has a private instance variable that is a MouseEvent and it is set inside the constructor to what is passed in.
* It contains an execute method that update the dragg.
*@author HCS Group: Siddharth Sharma, Luis Guerrero, Maverick Tudisco, Chintan Patel
*@version Final Version: May 6th, 2015
*/

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
