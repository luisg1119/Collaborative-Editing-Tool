package Paint;

import java.awt.event.MouseEvent;

import Server.PaintServer;

/** Description of ReleasedCommand:
* This class called ReleasedCommand extends PaintCommand<PaintServer> and creates 2 different instance variables.
* It creates a static final long serialVersionUID that creates a unique thread for the ReleasedCommand.
* It then sets it copy to be itself and it also contains a execute method that will command the ReleasedCommand.
*@author HCS Group: Siddharth Sharma, Luis Guerrero, Maverick Tudisco, Chintan Patel
*@version Final Version: May 6th, 2015
*/

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