package Chat;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class ChatPanel extends JFrame {
	private static final long serialVersionUID = 8585781522652428182L;
	private JTextArea textArea; // chat log displayed here
	private JTextField textField; // field where user enters text	
	private ObjectOutputStream output; // output stream to server
	private String clientName;


		private class EnterListener implements ActionListener {
			public void actionPerformed(ActionEvent arg0) {
				String s = textField.getText();
				try{
					output.writeObject(new AddMessageChat(clientName + ":  " + s));
				}catch(Exception e){
					e.printStackTrace();
				}
				textField.setText("");
			}
		}
		
		public ChatPanel(String clientName, ObjectOutputStream output){
			this.output = output;
			this.clientName = clientName;
			
			textArea = new JTextArea();
			textArea.setEditable(false);
			
			this.setLayout(new BorderLayout());
			this.setPreferredSize(new Dimension(800, 600));
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
