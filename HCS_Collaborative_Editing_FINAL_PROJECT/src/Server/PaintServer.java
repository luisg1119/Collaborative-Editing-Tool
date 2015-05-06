package Server;

import java.awt.event.MouseEvent;
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

import Editor.DisconnectEditorCommand;
import Editor.EditorCommand;
import Paint.DisconnectPaintCommand;
import Paint.PaintCommand;
import Paint.UpdatePaintDraggedCommand;
import Paint.UpdatePaintPressedCommand;
import Paint.UpdatePaintReleasedCommand;


public class PaintServer {
		private int paintPort;
		private String host;
		private String clientName;
		private ServerSocket paintSocket;
		private HashMap<String, ObjectOutputStream> paintOutput;
		private MouseEvent eventPressed;
		private MouseEvent eventDragged;
		private MouseEvent eventReleased;
			
	public PaintServer(String host, int port, String name){
		this.paintPort = port +2;
		this.host = host;
		this.clientName = name;
		
		eventPressed = null;
		eventDragged = null;
		eventReleased = null;
		// setup hashmaps to store future information
		this.paintOutput =  new HashMap<String, ObjectOutputStream>();
		
		try{
			//Start Paint Editing Sockets on its own port
			this.paintSocket = new ServerSocket(paintPort);
			System.out.println("The Paint Server was started on port: " + paintPort);
			
			//begin accepting editor clients
			new Thread(new ClientAccepterPaint()).start();

		}
		catch(Exception e){
			//new ClientAccepterEditor();
			System.err.println("Error creating Paint Server:");
			e.printStackTrace();
		}
	}
	
	public void updateClientsPressedPaint() {
		// make an UpdateClientCommmand, write to all connected users
		UpdatePaintPressedCommand update = new UpdatePaintPressedCommand(eventPressed); //this is a new class in model 
		try{
			for (ObjectOutputStream out : paintOutput.values())
				out.writeObject(update);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void updateClientsReleasedPaint() {
		// make an UpdateClientCommmand, write to all connected users
		UpdatePaintReleasedCommand update = new UpdatePaintReleasedCommand(eventReleased); //this is a new class in model 
		try{
			for (ObjectOutputStream out : paintOutput.values())
				out.writeObject(update);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void updateClientsDraggedPaint() {
		// make an UpdateClientCommmand, write to all connected users
		UpdatePaintDraggedCommand update = new UpdatePaintDraggedCommand(eventDragged); //this is a new class in model 
		try{
			for (ObjectOutputStream out : paintOutput.values())
				out.writeObject(update);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void pressedMouse(MouseEvent evt){
		this.eventPressed = evt;
		updateClientsPressedPaint();
	}
	
	public void releasedMouse(MouseEvent evt){
		this.eventReleased = evt;
		updateClientsReleasedPaint();
	}
	
	public void draggedMouse(MouseEvent evt){
		this.eventDragged = evt;
		updateClientsDraggedPaint();
	}
	
	public void disconnectPaint(String clientName) {
		try{
			paintOutput.get(clientName).close(); // close output stream
			paintOutput.remove(clientName); // remove from map
			
		} 
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private class ClientAccepterPaint implements Runnable{
		public void run() {
			try{
				while(true){
					// accept a new client, get output & input streams
					Socket s = paintSocket.accept();
					
					// grab the output and input streams for the new client
					ObjectOutputStream output = new ObjectOutputStream(s.getOutputStream());
					ObjectInputStream input = new ObjectInputStream(s.getInputStream());
					

					String name = (String)input.readObject();

					// map client name to output stream
					paintOutput.put(name, output);
					
					// spawn a thread to handle communication with this client
					new Thread(new ClientHandlerPaint(input)).start();
					//Put default image on newly opened window if there is any
					if (((eventPressed != null) || (eventDragged != null) ||(eventReleased != null))){
						pressedMouse(eventPressed);
						draggedMouse(eventDragged);
						releasedMouse(eventReleased);
					}

				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	private class ClientHandlerPaint implements Runnable{
		private ObjectInputStream input; // the input stream from the client

		public ClientHandlerPaint(ObjectInputStream input){
			this.input = input;
		}

		public void run() {
			try{
				while(true){
					// read a command from the client, execute on the server
					PaintCommand<PaintServer> command = (PaintCommand<PaintServer>)input.readObject();
					command.execute(PaintServer.this);
					
					if (command instanceof DisconnectPaintCommand){
						input.close();
						return;
					}
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	
	
}
