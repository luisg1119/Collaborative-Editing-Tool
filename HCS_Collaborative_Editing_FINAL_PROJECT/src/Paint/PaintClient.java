package Paint;
import java.awt.*;
import java.awt.event.*;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

import javax.swing.*;

import Server.PaintServer;
import Chat.ChatClientStart;
import Chat.ChatCommand;
import GUI.MainGUI;


public class PaintClient  extends JFrame {

	private String clientName; // user name of the client
	private String host;
	private int port;
	
	public PaintPanel paint;
	
	private Socket server; // connection to server
	private ObjectOutputStream output; // output stream
	private ObjectInputStream input; // input stream
	
	public PaintClient(String host2, int port2, String name) {
	    this.clientName = name;
		this.host = host2;
		this.port = port2 + 2;
		
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
	
	private void setDefaultCloseOperation() {
		this.dispose();
	}

	private class ServerHandler implements Runnable{
		public void run() {
			try{
				while(true){
					// read a command from server and execute it
					PaintCommand<PaintClient> c = (PaintCommand<PaintClient>)input.readObject();
					c.execute(PaintClient.this);
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
	
	public void updatePressed(MouseEvent evt) {
		paint.pressedUpdate(evt);
	}
	
	public void updateReleased(MouseEvent evt) {
		paint.releasedUpdate(evt);
	}
	
	public void updateDragged(MouseEvent evt) {
		paint.draggedUpdate(evt);
	}
	
	
	private void setGUI(){
	      JFrame mainWindow = new JFrame("Collaborative Paint");
	      mainWindow.setSize(800,280);
	      mainWindow.setLocation(100,100);
	      mainWindow.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
	      mainWindow.addWindowListener( new WindowAdapter() {
              @Override
              public void windowClosing(WindowEvent we) {
            	  System.out.println(MainGUI.paintGuiCount);
            	  MainGUI.paintGuiCount--;
              }
          } );
	      
	      paint = new PaintPanel(clientName, output);
	      mainWindow.setContentPane(paint);
	      mainWindow.setVisible(true);
	}
}
