package Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import Editor.DisconnectEditorCommand;
import Editor.EditorClient;
import Editor.EditorCommand;
import Editor.UpdateEditorCommand;

public class EditorServer {
	
	private ServerSocket CollaborativeSocket;	
	private HashMap<String, ObjectOutputStream> editorOutput;
	private int collaborativePort;
	private String host;
	private String clientName;
	public  String updatedText; //static?
	private ArrayList<String> texts;

	
	public EditorServer(String host, int collaborativePort, String clientName){
		
		this.texts = new ArrayList<String>();
		this.collaborativePort = collaborativePort;
		this.host = host;
		this.clientName = clientName;
		this.updatedText = "";
		
		this.editorOutput = new HashMap<String, ObjectOutputStream>();
		
		try{
			//this.revisions = new ArrayList<RevisionList>;
			
			//Start Collaborative Editing Sockets on its own port
			this.CollaborativeSocket = new ServerSocket(collaborativePort);
			System.out.println("The Collaborative Editing Server was started on port: " + collaborativePort);
			
			//begin accepting editor clients
			new Thread(new ClientAccepterEditor()).start();

		}
		catch(Exception e){
			//new ClientAccepterEditor();
			System.err.println("Error creating Collaborative Server:");
			e.printStackTrace();
		}
	}
		
		private class ClientAccepterEditor implements Runnable{
			public void run() {
				try{
					while(true){
						//System.out.println("We are in client acetor editor ");
						// accept a new client, get output & input streams
						Socket s = CollaborativeSocket.accept();
						
						// grab the output and input streams for the new client
						ObjectOutputStream output = new ObjectOutputStream(s.getOutputStream());
						ObjectInputStream input = new ObjectInputStream(s.getInputStream());
						

						String name = (String)input.readObject();

						// map client name to output stream
						editorOutput.put(name, output);
						
						// spawn a thread to handle communication with this client
						new Thread(new ClientHandlerEditor(input)).start();
						//Put default text on newly opened window if there is any
						addText(updatedText);

					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		
		private class ClientHandlerEditor implements Runnable{
			private ObjectInputStream input; // the input stream from the client

			public ClientHandlerEditor(ObjectInputStream input){
				this.input = input;
			}

			public void run() {
				try{
					while(true){
						// read a command from the client, execute on the server
						EditorCommand<EditorServer> command = (EditorCommand<EditorServer>)input.readObject();
						command.execute(EditorServer.this);
						
						// terminate if client is disconnecting
						if (command instanceof DisconnectEditorCommand){
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
		
		public void addText(String text){
			//texts.add(text); //put this when we save
			updatedText = text;
			updateClientsEditor();
		}
		
//		public static String getText(){
//			return updatedText;
//		}
		
		public void updateClientsEditor() {
			// make an UpdateClientCommmand, write to all connected users
			UpdateEditorCommand update = new UpdateEditorCommand(updatedText); //this is a new class in model 
			try{
				for (ObjectOutputStream out : editorOutput.values())
					out.writeObject(update);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public void disconnectEditor(String clientName) {
			try{
				editorOutput.get(clientName).close(); // close output stream
				editorOutput.remove(clientName); // remove from map
				
			} 
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		
}

