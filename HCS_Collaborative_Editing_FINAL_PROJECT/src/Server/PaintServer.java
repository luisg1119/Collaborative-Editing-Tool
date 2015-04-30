package Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

import javax.swing.JOptionPane;


public class PaintServer {
		private int paintPort;
		private ServerSocket paintSocket;
		private List<PaintObject> paintObjects;
		//private Map<String, Deque<CommandPaint<Server>>> paintHistories;
		private Map<String, ObjectInputStream> paintInputs;
		private Map<String, ObjectOutputStream> paintOutputs;
			
	public PaintServer(int port){
		// setup hashmaps to store future information
		//paintHistories = new ConcurrentHashMap<String, Deque<CommandPaint<Server>>>();
		paintInputs = new ConcurrentHashMap<String, ObjectInputStream>();
		paintOutputs = new ConcurrentHashMap<String, ObjectOutputStream>();
				
		// create the list for paint objects
		paintObjects = Collections.synchronizedList(new LinkedList<PaintObject>());
				
		this.paintPort = port + 2;
				
		try{					
			//Start Server
			this.paintSocket = new ServerSocket(paintPort); // create a new server
				
			System.out.println("Paint Server started on port: " + paintPort);

			// begin accepting paint clients
			new Thread(new ClientAccepterPaint()).start();
		}			
		catch(Exception e){
			System.err.println("Error creating paint server:");
			e.printStackTrace();
		}
	}
			
	private class ClientHandlerPaint implements Runnable{
		private String clientId; // name of the client
		//private Deque<CommandPaint<PaintServer>> paintHistory; // history of executed commands
		private ObjectInputStream paintInput; // input stream to read command from
			
		public ClientHandlerPaint(String id){ //Deque<CommandPaint<Server>> history){
			this.clientId = id;
			//this.paintHistory=history;
			this.paintInput = paintInputs.get(id);
			
			//Change this once we implement methods. 
			System.out.println("New Client " + id + " connected");
			
			updateClientsPaint();
		}
			
		public void run() {
			try{
				while(true){
						// read a command from the client, execute on the server
						//Object ob = paintInput.readObject();
						//if (ob instanceof CommandPaint<?>){
					CommandPaint<PaintServer> command = (CommandPaint<PaintServer>)ob; // cast the object // grab a command off the queue
					command.execute(PaintServer.this); // execute the command on the server
						
//						if (!(command instanceof UndoLastCommand)) // undo commands can't be undone
//							paintHistory.push(command);
						
					// terminate if client is disconnecting
					if (command instanceof DisconnectPaint){ //change disconnectCommand, to disconnect paint once class is made
						paintInput.close();
						return;
					}
				}
			}
			catch(Exception e){
				//System.err.println("In Client Handler:");
				e.printStackTrace();
				break;
			}
		}
	}
		
	private class ClientAccepterPaint implements Runnable{
		public void run() {
			try{
				while (true){
					// accept a new client, get output & input streams
					Socket s = paintSocket.accept(); // wait for a new client
						
					// grab the output and input streams for the new client
					ObjectOutputStream output = new ObjectOutputStream(s.getOutputStream());
					ObjectInputStream input = new ObjectInputStream(s.getInputStream());
					
					String clientName;
					//Do while, to reject if already connect, not allow multiple logins.
					do{
						// read the client's name
						clientName = (String)input.readObject();	
						// if that name is already connected, reject
						if (paintOutputs.containsKey(clientName)){
							output.writeObject("reject"); //change to no Later on
						}
					}while (paintOutputs.containsKey(clientName));
							
					// tell the client their name is accepted
					output.writeObject("accept");
							
					// add the output, input streams to the correct maps
					paintOutputs.put(clientName, output);
					paintInputs.put(clientName, input);
							
							// create a command history queue for the new client
							//paintHistories.put(name, new LinkedBlockingDeque<CommandPaint<Server>>());
							
					// start a new ClientHandler for this new client
					new Thread(new ClientHandlerPaint(clientName)).start();//, paintHistories.get(name))).start();
				}
			}
			catch(Exception e){
				//System.err.println("In Client Accepter:");
				e.printStackTrace();
				break;
			}
		}
	}
	
	public void updateClientsPaint(){
		UpdateCommand update = new UpdateCommand("server", paintObjects.toArray(new PaintObject[paintObjects.size()]));
		for (ObjectOutputStream out: paintOutputs.values())
			try{
				out.writeObject(update);
			}
			catch(Exception e){
				e.printStackTrace();
				//paintOutputs.remove(out); //maybe do not need? opposite commented out?
			}
	}
	
	//Move to bottom if i add more functionality
	public void disconnectPaint(String source) {
		System.out.printf("Client \'%s\' disconnecting\n", source);
		try{
			paintInputs.remove(source).close();
			paintOutputs.remove(source).close();
			//paintHistories.remove(source);
		} 
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	//Maybe Need? need code--------------------------------------------------
	
	
//	public void addObject(PaintObject object) {
//		System.out.println(paintObjects.size());
//		System.out.println("Adding new Object" + object.getClass().toString());
//		paintObjects.add(object);
//		System.out.println(paintObjects.size());
//		updateClientsPaint();
//	}
//		
//	public void removeObject(PaintObject object) {
//		paintObjects.remove(object);
//		updateClientsPaint();
//	}	
//	
//	public List<PaintObject> getObjects() {
//		return paintObjects;
//	}

			
			
			//Paint not use if no undo button
//			public void undoLast(String clientName) {
//				Deque<CommandPaint<Server>> commands = paintHistories.get(clientName);
//				if (commands.isEmpty())
//					return;
//				commands.pop().undo(Server.this);
//			}
			
//----------------------------------------------------------------------------------------------------


}
