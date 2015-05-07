package Editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.html.HTMLEditorKit;


/** Description of MainTextPane:
* This class called MainTextPane extends JPanel that contain many GUI functions such as scroll, timer, output stream, etc.
* This class contains serialVersionUID that creates the thread as a unique identifier.
* It contains a constructor that takes in a clientName and also an output.
* This class basically gives functionality to the TextPane so a user can edit things.
*@author HCS Group: Siddharth Sharma, Luis Guerrero, Maverick Tudisco, Chintan Patel
*@version Final Version: May 6th, 2015
*/

public class MainTextPane extends JPanel{
	private static final long serialVersionUID = -4224781683533607946L;
	private JScrollPane mainScroll;
	public ObjectOutputStream output; // output stream to server
	public static JTextPane edit; 
	private String name;
	private String newText;
	
	public MainTextPane(String clientName, ObjectOutputStream output){
		this.edit = new JTextPane();
		this.output = output;
		this.name = clientName;
		newText = "";
		
		setLayout(new BorderLayout(0, 0));
		
		
		mainScroll = new JScrollPane(edit);
		mainScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		mainScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(mainScroll, BorderLayout.CENTER);
		
		edit.setContentType("text/html");
		edit.setEditorKit(new HTMLEditorKit());
		
		
		edit.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void insertUpdate(DocumentEvent e) {
				if(newText.equals(edit.getText())){
					return;
				}
				else{
					textListener();
				}
			}

			@Override
			//set the text directly.... rather than do this... 
			public void removeUpdate(DocumentEvent e) {
				//setText();
//				edit.setText(edit.getText());
//				repaint();
//				System.out.println("New Text : " +newText);
//				if(newText.equals(edit.getText())){
//					return;
//				}
//				else{
//					System.out.println(edit.getText());
//					textListener();
//				}		

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				//setText();
//				edit.setText(edit.getText());
				//repaint();
//				if(newText.equals(edit.getText())){
//					return;
//				}
//				else{
//					textListener();
//				}
			
			} 
		});
	}
	
	public void updateDocument(String text, String name){
		if(text.equals(edit.getText())){
			//MainGUI.lastUpdatedLabel.setText(name +" made changes");
			//MainGUI.lastUpdatedLabel.repaint();

			System.out.println("I am the same as the other text string so we do not need to pass!");
			return;
		}
		else{
//			MainGUI.lastUpdatedLabel.setText(name + " made changes...");
//			MainGUI.lastUpdatedLabel.repaint();

			this.newText = text;
			int temp = edit.getCaretPosition();

			edit.setText(text);
			edit.setCaretPosition(temp);//edit.getDocument().getLength()); 
			
			repaint();
			}
	}
	
	//used for the other document listeners
	private void setText(){
		Runnable doSet = new Runnable(){

			@Override
			public void run() {
				newText = edit.getText();
				repaint();	
			}
		};
		SwingUtilities.invokeLater(doSet);
	}
	
	//used for sending commands over the server so all may see the text in the main text gui
	private void textListener(){
		try{
			if(newText.equals(edit.getText())){
				return;
			}
			else{
				System.out.println("I am in textListener");
				output.writeObject(new AddTextCommand(name, edit.getText()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public Component getScroll() {
		return mainScroll;
	}
	
}
