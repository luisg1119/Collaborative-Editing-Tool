package Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatServer {
	private ServerSocket chatSocket; // the server socket
	private List<String> messages;	// the chat log
	private HashMap<String, ObjectOutputStream> chatOutputs; // map of all connected users' output streams
	//private ObjectInputStream input; // the input stream from the client
	private int chatPort;
	
	
	public ChatServer(int port){
		//Initialize the Chat Lists
		this.messages = new ArrayList<String>(); // create the chat log
		this.chatOutputs = new HashMap<String, ObjectOutputStream>(); // setup the map
		
		//Create the port we will be using for ChatServer
		this.chatPort = port + 1;		
		
		try{
			//Start Chat Sockets
			this.chatSocket = new ServerSocket(chatPort);
			System.out.println("The Chat Server was started on port: " + port);
			
			// Begin Accepting Chat Clients
			new Thread(new ClientAccepterChat()).start();
		}
		catch(Exception e){
			System.err.println("Error creating Chat server:");
			e.printStackTrace();
		}
	}
	
	
	//The chat clientHandler
	private class ClientHandlerChat implements Runnable{
		private ObjectInputStream input; // the input stream from the client
		
		//Where is it being updated?
		public ClientHandlerChat(ObjectInputStream input){
			this.input = input;
		}

		@Override
		public void run() {
			try{
				while(true){
					// read a command from the client, execute on the server
					Command<ChatServer> command = (Command<ChatServer>)input.readObject();
					command.execute(ChatServer.this);
					
					// terminate if client is disconnecting
					if (command instanceof DisconnectCommand){
						input.close();
						return;
					}
				}
			}
			catch(Exception e){
				//System.err.println("In Client Handler:");
				e.printStackTrace();
			}
		}
	}
	
	private class ClientAccepterChat implements Runnable{
		public void run() {
			try{
				while(true){
					// accept a new client, get output & input streams
					Socket s = chatSocket.accept();
					
					// grab the output and input streams for the new client
					ObjectOutputStream output = new ObjectOutputStream(s.getOutputStream());
					ObjectInputStream input = new ObjectInputStream(s.getInputStream());
					
					String clientName;
					//Do while, to reject if already connect, not allow multiple logins.
					do{
						// read the client's name
						clientName = (String)input.readObject();
						//If the Client's Name is already, connected reject
						if (chatOutputs.containsKey(clientName)){
							output.writeObject("reject");
						}
					}while(chatOutputs.containsKey(clientName));
					
					//Notify the client the user name was valid
					output.writeObject("accept");
					
					// map client name to output stream
					chatOutputs.put(clientName, output);
					
					// spawn a thread to handle communication with this client
					new Thread(new ClientHandlerChat(input)).start();
					
					// add a notification message to the chat log
					addMessage(clientName + " connected");
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void addMessage(String message){
		messages.add(message);
		updateClientsChat();
	}
	
	public void updateClientsChat() {
		// make an UpdateClientCommmand, write to all connected users
		UpdateChat update = new UpdateChat(messages); //this is a new class in model 
		try{
			for (ObjectOutputStream out : chatOutputs.values())
				out.writeObject(update);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void disconnectChat(String clientName) {
		try{
			chatOutputs.get(clientName).close(); // close output stream
			chatOutputs.remove(clientName); // remove from map
			
			// add notification message
			addMessage(clientName + " disconnected");
		} 
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
