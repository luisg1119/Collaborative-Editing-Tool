package Server;

import javax.swing.JOptionPane;

import GUI.MainGUI;

public class Run_To_Start_Servers {
	private String host;
	private String userName;
	private int port;
	
	public Run_To_Start_Servers(String newHost, String portString, String newUserName){
		//Get user to import port Numbers and then start the Servers. 
		//String portString = //JOptionPane.showInputDialog("Enter a Port Address: ");
		if (newHost == null || portString == null){
			host = "localhost";
			port = 9001;
		}
		else{
			host = newHost;//JOptionPane.showInputDialog("Enter a Host Address: ");
			userName = newUserName;//JOptionPane.showInputDialog("Enter a User Name: ");
			port = Integer.parseInt(portString);
		}
		
		new ChatServer(host,port, userName);
		MainGUI frame = new MainGUI();
		frame.setVisible(true);
		
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
