package Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Chat.Command;
import Chat.DisconnectChat;
import Chat.UpdateChat;
import Chat.ChatClientStart;

public class ChatServer {
	private ServerSocket chatSocket; // the server socket
	private List<String> messages;	// the chat log
	private HashMap<String, ObjectOutputStream> chatOutputs; // map of all connected users' output streams
	//private ObjectInputStream input; // the input stream from the client
	private String clientName;
	private int chatPort;
	private String host;
	
	
	public ChatServer(String host, int port, String clientName){
		this.clientName = clientName;
		this.host = host;
		//Initialize the Chat Lists
		this.messages = new ArrayList<String>(); // create the chat log
		this.chatOutputs = new HashMap<String, ObjectOutputStream>(); // setup the map
		
		//Create the port we will be using for ChatServer
		chatPort = port; //+ 1;		
		
		try{
			//Start Chat Sockets
			chatSocket = new ServerSocket(chatPort);
			System.out.println("The Chat Server was started on port: " + chatPort);
			
			// Begin Accepting Chat Clients
			new Thread(new ClientAccepterChat()).start();
			ChatClientStart chatWindow = new ChatClientStart(host,chatPort, clientName);
			

		}
		catch(Exception e){
			System.err.println("Error creating Chat server:");
			e.printStackTrace();
		}
	}
	
	
	//The chat clientHandler
	private class ClientHandlerChat implements Runnable{
		private ObjectInputStream input; // the input stream from the client

		public ClientHandlerChat(ObjectInputStream input){
			this.input = input;
		}

		public void run() {
			try{
				while(true){
					// read a command from the client, execute on the server
					Command<ChatServer> command = (Command<ChatServer>)input.readObject();
					command.execute(ChatServer.this);
					
					// terminate if client is disconnecting
					if (command instanceof DisconnectChat){
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
					

					String name = (String)input.readObject();

					// map client name to output stream
					chatOutputs.put(name, output);
					
					// spawn a thread to handle communication with this client
					new Thread(new ClientHandlerChat(input)).start();
					// add a notification message to the chat log
					addMessage(name + " connected");
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
