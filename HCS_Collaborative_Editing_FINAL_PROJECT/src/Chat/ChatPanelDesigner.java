package Chat;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import java.awt.ScrollPane;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JLabel;

/** Description of ChatPanelDesigner:
* This class extend JPanel and it designs the ChatPanel, it contains a serialVersionUID and many components of a GUI.
*@author HCS Group: Siddharth Sharma, Luis Guerrero, Maverick Tudisco, Chintan Patel
*@version Final Version: May 6th, 2015
*/

public class ChatPanelDesigner extends JPanel {
	private static final long serialVersionUID = -8673566206025843968L;
	public JTextField textSend;
	private JTextArea textArea; // chat log displayed here
	private JTextField textField; // field where user enters text
	
	private ObjectOutputStream output; // output stream to server
	private String clientName;
	private String updatedText;
	//private JTextField activeUserText2;
	private JTextField activeUserText;//JLabel activeUserText;


	private class EnterListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String s = textSend.getText();
			try{
				if (s.isEmpty()){
					//activeUserText.setText("");
					return;
				}
				output.writeObject(new AddMessageCommand(clientName + ":  " + s));
			}
			catch(Exception e){
				e.printStackTrace();
			}
				textSend.setText("");
		}
	}
	
	private void textListener(){
		try {
			if (textSend.getText().isEmpty()){
				String s = "";
				output.writeObject(new UserTextStatusCommand(s));
			}
			else{
				output.writeObject(new UserTextStatusCommand(clientName));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
		
	public ChatPanelDesigner(String clientName, ObjectOutputStream output){
		this.output = output;
		this.clientName = clientName;
		
		setLayout(new BorderLayout(0, 0));
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		add(textArea, BorderLayout.NORTH);
		textArea.setEditable(false);
		
		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(scroll, BorderLayout.CENTER);
		
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		//The Send Button for the chat client
		JButton sendButton = new JButton("Send");
		panel.add(sendButton, BorderLayout.EAST);
		
		//Creates JTextField that will be used for user Chat 
		textSend = new JTextField();
		panel.add(textSend, BorderLayout.CENTER);
		
		// Creates  a JText Field that will tell if someone is currently typing
		activeUserText = new JTextField();
		activeUserText.setEditable(false);
		//Take border off JText Field
		activeUserText.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		panel.add(activeUserText, BorderLayout.NORTH);
		//Set The font and size of the default text
		activeUserText.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		activeUserText.setForeground(Color.GRAY);
		activeUserText.setBackground(SystemColor.window);
		activeUserText.setColumns(10);
		
		//Add a document listener to update who is currently typing
		textSend.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				textListener();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				textListener();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				textListener();
			}
		});

		
		// create a listener for writing messages to server
		ActionListener listener = new EnterListener();
		// attach listener to field & button
		textSend.addActionListener(listener);
		sendButton.addActionListener(listener);
	}
	
	public void updateActiveUser(String user){
		if (user.isEmpty()){
			activeUserText.setText("");//textSend.getText());
			if(!textSend.getText().isEmpty()){
				textListener();
			}
			repaint();
		}
		else{
			String s = user + " is currently typing...";
			activeUserText.setText(s);
			repaint();
		}
	}
	
	public void update(List<String> messages) {
		String s = "";
		for (String message: messages)
			s = s + message + "\n";
		
		textArea.setText(s);
		textArea.setCaretPosition(s.length());
		repaint();
	}
}
