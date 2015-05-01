package Editor;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Chat.DisconnectChat;

public class EditorClientStart {
	
	private String clientName; // user name of the client
	private String host;
	private int editorPort;
	
	private MainGUI chat;

	private Socket server; // connection to server
	private ObjectOutputStream output; // output stream
	private ObjectInputStream input; // input stream
	
	public EditorClientStart(int editorPort, String host, String clientName){
		
		this.editorPort = editorPort;
		this.host = host;
		this.clientName = clientName;
		
		try{
			// Open a connection to the server
			server = new Socket(host, editorPort);
			output = new ObjectOutputStream(server.getOutputStream());
			input = new ObjectInputStream(server.getInputStream());
			
			// write out the name of this client
			output.writeObject(clientName);
			
			// add a listener that sends a disconnect command to when closing
			this.addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent arg0) {
					try {
						output.writeObject(new DisconnectChat(clientName));
						output.close();
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});	
	}

}
