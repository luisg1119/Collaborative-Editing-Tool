package Paint;

import java.awt.event.MouseEvent;

import Server.PaintServer;

/** Description of PressedCommand:
* This class called PressedCommand creates a final long serialVersionUID that creates a unique thread for the pressed commands.
* This also has a MouseEvent called evt.
* We then make a copy of it in the constuctor and set it to be equal that.
* We have a void execute that executes the idea that the mouse was pressed.
*@author HCS Group: Siddharth Sharma, Luis Guerrero, Maverick Tudisco, Chintan Patel
*@version Final Version: May 6th, 2015
*/

public class PressedCommand extends PaintCommand<PaintServer>{
	private static final long serialVersionUID = -7729072721500083246L;
	private MouseEvent evt;
	
	public PressedCommand(MouseEvent evt){
		this.evt = evt;
	}
	
	public void execute(PaintServer executeOn) {
		executeOn.pressedMouse(evt);
	}
}
