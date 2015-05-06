package Paint;

import java.awt.event.MouseEvent;

/** Description of UpdatePaintReleasedCommand:
* This class called UpdatePaintReleasedCommand extends PaintCommand<PaintClient>.
* It creates a unique serialVersionUID that creates the thread for this method.
* It creates a mouseEvent called evt and it also has an execute method that fill update the release.
*@author HCS Group: Siddharth Sharma, Luis Guerrero, Maverick Tudisco, Chintan Patel
*@version Final Version: May 6th, 2015
*/

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
