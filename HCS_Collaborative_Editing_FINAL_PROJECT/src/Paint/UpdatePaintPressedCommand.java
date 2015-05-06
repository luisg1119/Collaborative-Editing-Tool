package Paint;

import java.awt.event.MouseEvent;

/** Description of UpdatePaintPressedCommand:
* This class called UpdatePaintPressedCommand extends extends PaintCommand<PaintClient>.
* It creates a unique serialVersionUID that creates a thread for this given paint command.
* It has an execute method that will update the pressed command.
*@author HCS Group: Siddharth Sharma, Luis Guerrero, Maverick Tudisco, Chintan Patel
*@version Final Version: May 6th, 2015
*/

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
