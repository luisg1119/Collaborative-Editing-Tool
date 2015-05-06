package Server;

import javax.swing.JOptionPane;

import GUI.MainGUI;

/** Description of Run_To_Start_Servers:
* This is what runs the servers and starts their commands.
* This class contains a String that represents the host followed by a String that represents a userName
* It also has an int that represents the port.
*@author HCS Group: Siddharth Sharma, Luis Guerrero, Maverick Tudisco, Chintan Patel
*@version Final Version: May 6th, 2015
*/

public class Run_To_Start_Servers {
	private String host;
	private String userName;
	private int port;
	
	//create something to save the port last used on the computer, and read in here to not use same ports
	public Run_To_Start_Servers(String newHost, String portString, String newUserName){
		host = newHost;
		userName = newUserName;
		port = Integer.parseInt(portString);
			
		new EditorServer(host, port, userName);
		new ChatServer(host,port, userName);
		new PaintServer(host,port,userName);
		MainGUI frame = new MainGUI();
		frame.setVisible(true);
	
		
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
