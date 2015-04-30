package Chat;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ChatClientStart extends JFrame {
	//private static final long serialVersionUID = 2577057661457057225L;
	private String clientName; // user name of the client
	private String host;
	private int port;
	
	private ChatPanelDesigner chat;
	
	private Socket server; // connection to server
	private ObjectOutputStream output; // output stream
	private ObjectInputStream input; // input stream

	private JPanel chatPane;
	
	
	public ChatClientStart(String host, int port, String name) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 200, 800);

		//this.host = JOptionPane.showInputDialog("Host address:");
		//this.clientName = JOptionPane.showInputDialog("Please Enter a Username:");
	    this.clientName = name;
		this.host = host;
		this.port = port;
		//We need to make the database for users
		//this.clientName = name;
		
		//Most likely wont need these checks since they will be in the beginning
		if (host == null || port == 0 || clientName == null)
			return;
		
		try{
			// Open a connection to the server
			server = new Socket(host, port);
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
					Command<ChatClientStart> c = (Command<ChatClientStart>)input.readObject();
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
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 400, 400);
		chat = new ChatPanelDesigner(clientName, output);
		this.add(chat);
		this.setVisible(true);
	}
	
	public void update(List<String> messages) {
		chat.update(messages);
	}
	
	public ObjectOutputStream returnOutput(){
		return output;	
	}
	
	public ObjectInputStream returnInput(){
		return input;	
	}
	
	
}
