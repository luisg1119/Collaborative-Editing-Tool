package Server;

import javax.swing.JOptionPane;

public class Run_To_Start_Servers {
	public static void main (String[] args){
		//Get user to import port Numbers and then start the Servers. 
		String portString = JOptionPane.showInputDialog("Enter a Port Address: ");
		int port = Integer.parseInt(portString);

		// new CollaborativeEditingServer(port)		
		new ChatServer(port);
		new PaintServer(port);
		
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
