package Paint;

/** Description of DraggedCommand:
* This class called DraggedCommand creates a unique final long serialVersionUID that creates a thread for the commands.
* It also creates a private MouseEvent called evt and it created a copy in the constructor.
* This class has an execute method that adds a mouse event to the paint server log
*@author HCS Group: Siddharth Sharma, Luis Guerrero, Maverick Tudisco, Chintan Patel
*@version Final Version: May 6th, 2015
*/

import java.awt.event.MouseEvent;

import Server.PaintServer;

public class DraggedCommand extends PaintCommand<PaintServer>{
	private static final long serialVersionUID = 4253765450318264621L;
	private MouseEvent evt;
	
	public DraggedCommand(MouseEvent evt){
		this.evt = evt;
	}
	
	public void execute(PaintServer executeOn) {
		executeOn.draggedMouse(evt);
	}

}