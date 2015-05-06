package Paint;


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

