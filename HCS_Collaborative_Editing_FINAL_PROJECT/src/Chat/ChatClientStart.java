package Chat;

import java.awt.BorderLayout;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

import javax.swing.JPanel;

/** Description of ChatClientStart:
* This is where the ChatClientStart is launched using the information of the client name, host, and port number. 
* It is launched in a JPanel. We then create a server sock with an output and input stream.
* It then writes the clientName to the output. This class will update messages and much more features that the ChatClient does.
*@author HCS Group: Siddharth Sharma, Luis Guerrero, Maverick Tudisco, Chintan Patel
*@version Final Version: May 6th, 2015
*/

public class ChatClientStart extends JPanel {
	//private static final long serialVersionUID = 2577057661457057225L;
	private String clientName; // user name of the client
	private String host;
	private int port;
	
	public ChatPanelDesigner chat;
	
	private Socket server; // connection to server
	private ObjectOutputStream output; // output stream
	private ObjectInputStream input; // input stream

	
	
	public ChatClientStart(String host1, int port1, String name) {
	    this.clientName = name;
		this.host = host1;
		this.port = port1+1;
		
		try{
			// Open a connection to the server
			server = new Socket(host, port);
			output = new ObjectOutputStream(server.getOutputStream());
			input = new ObjectInputStream(server.getInputStream());
			
			// write out the name of this client
			output.writeObject(clientName);
						
		// start a thread for handling server events
		setGUI();
		new Thread(new ServerHandler()).start();
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private class ServerHandler implements Runnable{
		public void run() {
			try{
				while(true){
					// read a command from server and execute it
					ChatCommand<ChatClientStart> c = (ChatCommand<ChatClientStart>)input.readObject();
					c.execute(ChatClientStart.this);
				}
			}
			catch(SocketException e){
				return; // "gracefully" terminate after disconnect
			}
			catch (EOFException e) {
				return; // "gracefully" terminate
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void setGUI(){
		chat = new ChatPanelDesigner(clientName, output);
		this.setLayout(new BorderLayout());
		this.add(chat, BorderLayout.CENTER);
		this.setVisible(true);
		this.setSize(200, 600);
	}
	
	public void update(List<String> messages) {
		chat.update(messages);
	}
	
	public void updateActive(String message){
		chat.updateActiveUser(message);
	}
	
	public ObjectOutputStream returnOutput(){
		return output;	
	}
	
	public ObjectInputStream returnInput(){
		return input;	
	}
	
	
}
