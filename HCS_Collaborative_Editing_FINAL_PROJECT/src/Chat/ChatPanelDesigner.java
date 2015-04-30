package Chat;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;

import java.awt.ScrollPane;

public class ChatPanelDesigner extends JPanel {
	private static final long serialVersionUID = -8673566206025843968L;
	private JTextField textSend;
	private JTextArea textArea; // chat log displayed here
	private JTextField textField; // field where user enters text
	
	private ObjectOutputStream output; // output stream to server
	private String clientName;


	private class EnterListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String s = textSend.getText();
			try{
				if (s.isEmpty()){
					return;
				}
				output.writeObject(new AddMessageChat(clientName + ":  " + s));

			}
			catch(Exception e){
				e.printStackTrace();
			}
				textSend.setText("");
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
		this.add(scroll);
		
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JButton sendButton = new JButton("Send");
		panel.add(sendButton, BorderLayout.EAST);
		
		textSend = new JTextField();
		panel.add(textSend, BorderLayout.CENTER);
		
		// create a listener for writing messages to server
		ActionListener listener = new EnterListener();
		
		// attach listener to field & button
		textSend.addActionListener(listener);
		sendButton.addActionListener(listener);
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
