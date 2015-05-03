package Editor;

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

import GUI.MainGUI;

public class EditorClientStart {
	
	private String clientName; // user name of the client
	private String host;
	private int editorPort;
	
	private MainTextPane edit;
	//private MainGUI chat;

	private Socket server; // connection to server
	private ObjectOutputStream output; // output stream
	private ObjectInputStream input; // input stream
	
	public EditorClientStart(int port1, String host1, String name){
		
		this.editorPort = port1;
		this.host = host1;
		this.clientName = name;
		
		try{
			// Open a connection to the server
			server = new Socket(host, editorPort);
			output = new ObjectOutputStream(server.getOutputStream());
			input = new ObjectInputStream(server.getInputStream());
			
			// write out the name of this client
			output.writeObject(clientName);

			setGUI();
			new Thread(new ServerHandler()).start();
			
//			// add a listener that sends a disconnect command to when closing
//			this.addWindowListener(new WindowAdapter(){
//				public void windowClosing(WindowEvent arg0) {
//					try {
//						output.writeObject(new DisconnectChat(clientName));
//						output.close();
//						input.close();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//			});	
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	private class ServerHandler implements Runnable{
		public void run() {
			try{
				while(true){
					// read a command from server and execute it
					EditorCommand<EditorClientStart> c = (EditorCommand<EditorClientStart>)input.readObject();
					c.execute(EditorClientStart.this);
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
		edit = new MainTextPane();//(output); //should pass in??
//		this.setLayout(new BorderLayout());
//		this.add(chat, BorderLayout.CENTER);
//		this.setVisible(true);
//		this.setSize(200, 600);
		
	}
	
	public void update(List<String> text) {
		edit.update(text);
	}
	
//	public ObjectOutputStream returnOutput(){
//		return output;	
//	}
//	
//	public ObjectInputStream returnInput(){
//		return input;	
//	}
			

}
