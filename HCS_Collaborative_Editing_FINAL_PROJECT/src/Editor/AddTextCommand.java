package Editor;

import Server.EditorServer;



public class AddTextCommand extends EditorCommand<EditorServer>{
		private static final long serialVersionUID = -4786261013473386911L;
		private String text; // message from client
		
		/**
		 * Creates an AddMessageCommand with the given message
		 * 
		 * @param message	message to add to log
		 */
		public AddTextCommand(String text){
			this.text = text;
		}
		
		public void execute(EditorServer executeOn) {
			// add message to server's chat log
			executeOn.addText(text);
		}
		
}
