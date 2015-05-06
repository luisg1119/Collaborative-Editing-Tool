package Login;

import javax.swing.JFrame;

import java.awt.GridBagLayout;
import java.awt.BorderLayout;

import javax.swing.JFormattedTextField;
import javax.swing.JPasswordField;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

import GUI.MainGUI;
import Server.Run_To_Start_Servers;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.Font;
import java.util.HashMap;

/** Description of LoginWindow:
* This class is called LoginWindow and it creates the LoginWindow for a user to type in their information, this connects to the server and lets a user edit etc.
*@author HCS Group: Siddharth Sharma, Luis Guerrero, Maverick Tudisco, Chintan Patel
*@version Final Version: May 6th, 2015
*/

public class LoginWindow extends JFrame {
	public static JTextField txtA;
	private JPasswordField passwordField;
	private boolean isSuccesful = false;
	static LoginServer server = new LoginServer();
	private static JTextField portField;
	private static JTextField hostField;
	private static HashMap<String, String> tempHashMap;

	public LoginWindow() {
		tempHashMap = new HashMap<String, String>();
		setTitle("Login: HCS Document Editor\n");
		setResizable(false);
		getContentPane().setBackground(new Color(238, 238, 238));
		getContentPane().setLayout(null);
		setBounds(100, 100, 400, 227);

		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		
		txtA = new JTextField();
		txtA.setText("a");
		txtA.setBounds(94, 41, 277, 28);
		getContentPane().add(txtA);
		txtA.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(94, 78, 277, 28);
		getContentPane().add(passwordField);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(25, 44, 90, 22);
		getContentPane().add(lblUsername);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(25, 81, 90, 22);
		getContentPane().add(lblPassword);

		final JLabel messageLabel = new JLabel("Please Login with your username and password. OR register\n");
		messageLabel.setBounds(12, 6, 388, 38);
		getContentPane().add(messageLabel);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = txtA.getText();
				String password = String.valueOf(passwordField.getPassword());
				
				if (username.equals("") || password.equals("")){
					JOptionPane.showMessageDialog(null, "Please enter a valid username and password");
					throw new InvalidUsernameException();
//					return; commented out because of exception
				}
				//Calls the server's hashmap and checks if username already exists
				server.populateMap();
				if(server.loginMap.containsKey(username) == false){
					messageLabel.setText("User not found! ");
					throw new InvalidUsernameException();
				}
				
					//System.out.println(server.loginMap.toString());
					//If username doesnt exist, it's created and added to the server
					if(server.login(username, password) == true){
						messageLabel.setText("Succesfully logged in " + username);
						isSuccesful = true;
						//We send username and password details so we can display this info in main gui.
						MainGUI.usernameRetrieval(username,password);
						dispose();
						//We dispose of the Login screen and now we generate the MainGUI
						//MainGUI frame = new MainGUI();
						Run_To_Start_Servers run = new Run_To_Start_Servers(getHost(), getPort(), getUsername());
						//frame.setVisible(true);
					}
				
			}
		});
		btnLogin.setBounds(12, 152, 117, 29);
		getContentPane().add(btnLogin);
		
		JButton registerButton = new JButton("Register");
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterButton newRegButton = new RegisterButton();
				newRegButton.setVisible(true);
			}
		});
		registerButton.setBounds(121, 152, 117, 29);
		getContentPane().add(registerButton);
		
		JLabel hostLabel = new JLabel("Host/IP :");
		hostLabel.setBounds(25, 118, 90, 22);
		getContentPane().add(hostLabel);
		
		JLabel portNumber = new JLabel("Port:");
		portNumber.setBounds(217, 118, 90, 22);
		getContentPane().add(portNumber);
		
		portField = new JTextField();
		portField.setText("9001");
		portField.setColumns(10);
		portField.setBounds(254, 118, 117, 25);
		getContentPane().add(portField);
		
		hostField = new JTextField();
		hostField.setText("localhost");
		hostField.setColumns(10);
		hostField.setBounds(94, 118, 117, 25);
		getContentPane().add(hostField);
		
		JButton btnForgotPassword = new JButton("Forgot Password");
		btnForgotPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ForgotPasswordButton frgtButton = new ForgotPasswordButton();
				frgtButton.setVisible(true);
			}
		});
		btnForgotPassword.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		btnForgotPassword.setBounds(227, 153, 117, 29);
		getContentPane().add(btnForgotPassword);
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResetButton newReset = new ResetButton();
				newReset.setVisible(true);
			}
		});
		btnReset.setBounds(334, 152, 66, 28);
		getContentPane().add(btnReset);
		
		
		
		
	}
	public static LoginServer getLoginServ(){
		return server;
	}

	public boolean isSuccesful() {
		return isSuccesful;
	}

	public static String getUsername() {
		return txtA.getText().trim();
	}
	
	public static String getPort(){
		if(portField.equals("")){
			return null;
		}
		else{
			return portField.getText();
		}
	}
	
	public static String getHost(){
		if(hostField.equals("")){
			return null;
		}
		else{
			return hostField.getText();
		}
		
	}
}
