package Server;

import javax.swing.JOptionPane;

import GUI.MainGUI;

public class Run_To_Start_Servers {
	private String host;
	private String userName;
	private int port;
	
	//create something to save the port last used on the computer, and read in here to not use same ports
	public Run_To_Start_Servers(String newHost, String portString, String newUserName){
		//Check if port has not been initiated already
		System.out.println("Global Port is: "+port);
		System.out.println("Parameter Port is : "+ portString);
		if (port == Integer.parseInt(portString)){
			System.out.println("Same Ports");
			MainGUI frame = new MainGUI(portString);
			frame.setVisible(true);
		}
		//check if there is not a host and there is a port
		else if ((newHost == null) && (!portString.isEmpty())){ 
			host = "localhost";
			int tempPort = Integer.parseInt(portString);
			
			if(tempPort == port){
				
			}
			else{
				port = tempPort;
				//Start Servers and GUI
				new ChatServer(host,port, userName);
				MainGUI frame = new MainGUI();
			}
		}
		//Check if there is a host and there is not a port
		else if ((!newHost.isEmpty()) && (portString == null)){
			host = newHost;
			port = 9001;
			
			//Start Servers and GUI
			new ChatServer(host,port, userName);
			MainGUI frame = new MainGUI();
		}
		
		else if (newHost.isEmpty() && portString.isEmpty()){
			host = "localhost";
			port = 9001;
			
			new EditorServer(host, port, userName);
			new ChatServer(host,port, userName);
			MainGUI frame = new MainGUI();
			frame.setVisible(true);
		}
		
		else{
			System.out.println("First Run of ports");
			host = newHost;//JOptionPane.showInputDialog("Enter a Host Address: ");
			userName = newUserName;//JOptionPane.showInputDialog("Enter a User Name: ");
			port = Integer.parseInt(portString);
			
			new EditorServer(host, port, userName);
			new ChatServer(host,port, userName);
			MainGUI frame = new MainGUI();
			frame.setVisible(true);
		}
		
//		new ChatServer(host,port, userName);
//		MainGUI frame = new MainGUI();
//		frame.setVisible(true);
		
		// new CollaborativeEditingServer(port)		

		//new PaintServer(port);
		

		
		//Print out Friendly User Warning on console. There are checks that will not allow, just a friendly reminder
		System.out.println("------------------------------------------------¡WARNING!------------------------------------------------");
		System.out.println("DO NOT RE-USE THE FOLLOWING PORTS UNTIL THEY ARE FULLY TURNED OFF: (You will not be able to use them) ");
		System.out.println("Collaborative Editing Server  : " + port);
		port += 1;
		System.out.println("Chat Server                   : " + port);
		port += 1;
		System.out.println("Paint Server                  : " + port);
		System.out.println("---------------------------------------------------------------------------------------------------------");
		

	}
}
