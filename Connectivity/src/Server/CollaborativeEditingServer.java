package Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;

public class CollaborativeEditingServer {
	private ServerSocket CollaborativeSocket;	
	private ArrayList<RevisionList> revisions;
	private HashMap<String, ObjectOutputStream> output;
	private HashMap<String, ObjectInputStream> input;
	private int collaborativePort;
	
	
	
	
//Note to self: DO NOT START WITHOUT EVERYONE, SO THEY CAN LEARN. 	
	public CollaborativeEditingServer(int port){
		this.collaborativePort = port;
		
		try{
			this.revisions = new ArrayList<RevisionList>;
			this.output = new HashMap<String, ObjectOutputStream>();
			this.input = new HashMap<String, ObjectInputStream>();
			
			//Start Collaborative Editing tools Sockets
			this.CollaborativeSocket = new ServerSocket(collaborativePort);
			System.out.println("The Collaborative Editing Server was started on port: " + port);
			
			// Begin Accepting Chat Clients
			new Thread(new ClientAccepterEditing()).start();
		}	
		catch(Exception e){
			System.err.println("Error creating Collaborative Server:");
			e.printStackTrace();
		}
	}
	
	//ClientAccepterEditing(
	
}
