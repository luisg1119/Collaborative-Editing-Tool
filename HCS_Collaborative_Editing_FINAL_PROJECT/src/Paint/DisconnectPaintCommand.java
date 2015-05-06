package Paint;

/** Description of DisconnectPaintCommand:
* This class called DisconnectPaintCommand creates a final long serialVersionUID that creates a unique thread for this server to disconnect.
* The constructor takes in a String with the name and it copies the name.
* It also has an execute method that disconnects the paint server.
*@author HCS Group: Siddharth Sharma, Luis Guerrero, Maverick Tudisco, Chintan Patel
*@version Final Version: May 6th, 2015
*/

import Server.PaintServer;

public class DisconnectPaintCommand extends PaintCommand<PaintServer>{
		private static final long serialVersionUID = -2758275013668976153L;
		private String clientName; // client who is disconnecting
		
		/**
		 * Creates a disconnect command for the given client
		 * 
		 * @param name	username of client to disconnect
		 */
		public DisconnectPaintCommand(String name){
			clientName = name;
		}
		
		@Override
		public void execute(PaintServer executeOn) {
			// disconnect client
			executeOn.disconnectPaint(clientName);
		}
}

