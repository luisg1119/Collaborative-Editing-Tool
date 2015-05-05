package Editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.html.HTMLEditorKit;

import Chat.AddMessageCommand;
import Chat.UserTextStatusCommand;
import GUI.MainGUI;

/** Description of MainTextPane:
* This class called MainTextPane extends JPanel that contain many GUI functions such as scroll, timer, output stream, etc.
*@author HCS Group: Siddharth Sharma, Luis Guerrero, Maverick Tudisco, Chintan Patel
*@version Final Version: May 6th, 2015
*/

public class MainTextPane extends JTextPane{//JTextPane{
	private JScrollPane mainScroll;
	//private JTextPane textPane;
	private Timer timer;
	public ObjectOutputStream output; // output stream to server
	public static JTextPane edit; 
	private String name;
	private String newText;
	
	public MainTextPane(String clientName, ObjectOutputStream output){
		//textPane = new JTextPane();
		this.edit = new JTextPane();
		this.output = output;
		this.name = clientName;
		newText = "";//Server.EditorServer.updatedText;
		//System.out.println("new window : "+ Server.EditorServer.updatedText);
		//this.edit = this;
		timer = new Timer();
		//timer.schedule(new saveTask(edit,output),0); //By default save the file at start
		
		setLayout(new BorderLayout(0, 0));
		
		
		mainScroll = new JScrollPane(edit);
		mainScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		mainScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(mainScroll, BorderLayout.CENTER);
		
		edit.setContentType("text/html");
		edit.setEditorKit(new HTMLEditorKit());
		
//*********************READ ME*********************
		//The functionality has to be limited to just insert Update, 
		//as i keep getting errors whenever i open a second window if the
		//other event listeners are enabled. Not too sure what it could be
		// anymore, it just seems as if it is calling itself too many
		// times and causing the server to crash?
		edit.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void insertUpdate(DocumentEvent e) {
				System.out.println("insertUpdate");
				if(newText.equals(edit.getText())){
					return;
				}
				else{
					//System.out.println(edit.getText());
//					timer.cancel();  //Cancel the first timer you had
//					timer.purge();   //Garbage Collector: Clean up timer queue
//					timer = new Timer(); 
//					timer.schedule(new saveTask(edit,output), 10000);  //Call saveTask every 9 seconds
					textListener();
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				System.out.println("RemoveUpdate");
//				System.out.println("New Text : " +newText);
//				if(newText.equals(edit.getText())){
//					return;
//				}
//				else{
//					System.out.println(edit.getText());
//					textListener();
//				}		
				//textListener();
//				timer.cancel();  //Cancel the first timer you had
//				timer.purge();   //Garbage Collector: Clean up timer queue
//				timer = new Timer(); 
//				timer.schedule(new saveTask(edit,MainTextPane.output), 10000);  //Call saveTask every 9 seconds
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				System.out.println("ChangedUpdate");
//				if(newText.equals(edit.getText())){
//					return;
//				}
//				else{
//					textListener();
//				}
				
//				timer.cancel();  //Cancel the first timer you had
//				timer.purge();   //Garbage Collector: Clean up timer queue
//				timer = new Timer(); 
//				timer.schedule(new saveTask(edit,MainTextPane.output), 10000);  //Call saveTask every 9 seconds
			} 
		});
	}
	
//***************Caret POsition keeps breaking??????!!!!!
	public void updateDocument(String text, String name){
		if(text.equals(edit.getText())){
			if(!name.isEmpty()){
				MainGUI.lastUpdatedLabel.setText(name+" last made changes");
				MainGUI.lastUpdatedLabel.repaint();
			}
			System.out.println("I am the same as the other text string so we do not need to pass!");
			return;
		}
		else{
			this.newText = text;
			int temp = edit.getCaretPosition();
			//System.out.println("My caret Position is: " + edit.getCaretPosition());
			edit.setText(text);
			edit.setCaretPosition(temp);
			repaint();
			System.out.println("Name" +name);
//			MainGUI.lastUpdatedLabel.setText(name +" has just made changes");
//			MainGUI.lastUpdatedLabel.repaint();
		}

	}
	
	//What will handle the update of all users text panels
	public void updateSave(List<String> text){
			int size = text.size(); //make sure to get last iteration from list
			edit.setText("New Text WOOOOOOh!");//text.get(size-1));
			repaint();
			
			//save here 
	}
	
	private void textListener(){
		try{
			if(newText == edit.getText()){
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
	
	//An Inner class that extends TimerTask to be used in the timer
	private class saveTask extends TimerTask{
		private JTextPane edit;
		private ObjectOutputStream output;
		
	    public saveTask(JTextPane edit, ObjectOutputStream out) {
	    	this.edit = new JTextPane();
	    	this.edit = edit;
	    	this.output = out;
		}

		public void run(){
	    	try {
	    		System.out.println("I have waited 10 seconds! On to the server!");
	    		//output does not exist so you will get an error here, 
	    		//need to change to MainTextPaneConstructor(ObJecctOutput...)
				output.writeObject(new SaveEditorCommand(edit.getText()));
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	}

	public Component getScroll() {
		return mainScroll;
	}
//	public static ObjectOutputStream getOutput(){
//		return output;
//	}
	
}
