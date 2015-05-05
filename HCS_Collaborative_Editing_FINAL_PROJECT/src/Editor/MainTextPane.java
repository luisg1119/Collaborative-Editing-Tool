package Editor;

import java.awt.BorderLayout;
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

public class MainTextPane extends JPanel{//JTextPane{
	private JScrollPane mainScroll;
	//private JTextPane textPane;
	private Timer timer;
	public ObjectOutputStream output; // output stream to server
	private JTextPane edit; 
	private String name;
	private String newText;
	
	public MainTextPane(String clientName, ObjectOutputStream output){
		//textPane = new JTextPane();
		this.edit = new JTextPane();
		this.output = output;
		this.name = clientName;
		//newText = Server.EditorServer.updatedText;
		//System.out.println("new window : "+ Server.EditorServer.updatedText);
		//this.edit = this;
		timer = new Timer();
		//timer.schedule(new saveTask(edit,output),0); //By default save the file at start
		
		setLayout(new BorderLayout(0, 0));
		
		
		JScrollPane scroll = new JScrollPane(edit);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(scroll, BorderLayout.CENTER);
		
		//mainScroll = new JScrollPane(this);
		//mainScroll.setBounds(10, 41, 785, 580);

		edit.setContentType("text/html");
		edit.setEditorKit(new HTMLEditorKit());
		
		edit.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void insertUpdate(DocumentEvent e) {
				System.out.println("insertUpdate");
				textListener();
				
//				timer.cancel();  //Cancel the first timer you had
//				timer.purge();   //Garbage Collector: Clean up timer queue
//				timer = new Timer(); 
				//timer.schedule(new saveTask(edit,output), 9000);  //Call saveTask every 9 seconds
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
//				System.out.println("RemoveUpdate");
//				textListener();
		
				//textListener();
//				timer.cancel();  //Cancel the first timer you had
//				timer.purge();   //Garbage Collector: Clean up timer queue
//				timer = new Timer(); 
				//timer.schedule(new saveTask(edit,output), 9000);  //Call saveTask every 9 seconds
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
//				System.out.println("ChangedUpdate");
//				textListener();
				
//				timer.cancel();  //Cancel the first timer you had
//				timer.purge();   //Garbage Collector: Clean up timer queue
//				timer = new Timer(); 
				//timer.schedule(new saveTask(edit,output), 9000);  //Call saveTask every 9 seconds
			} 
		});
	}
	
	
	public void updateDocument(String text){
		if(text.equals(edit.getText())){
			System.out.println("I am the same ad the other text string so we do not need to pass!");
			return;
		}
		else{
			this.newText = text;
			int temp = edit.getCaretPosition();
			edit.setText(text);
			//edit.setCaretPosition(temp);
			repaint();
		}
		
	}
	
	//What will handle the update of all users text panels
	public void update(List<String> text){
			int size = text.size();
			edit.setText(text.get(size-1));
			repaint();
	}
	
	private void textListener(){
		try{
			System.out.println("I am in textListener");
			output.writeObject(new AddTextCommand(name, edit.getText()));

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
	    	//try {
	    		System.out.println("Enable the code here in MainTextPane saveTask Method! -- ask Sidd");
	    		//output does not exist so you will get an error here, 
	    		//need to change to MainTextPaneConstructor(ObJecctOutput...)
				//output.writeObject(new AddTextCommand(edit.getText()));
			//} catch (IOException e) {
			//	e.printStackTrace();
			//}
	    }
	}
	
}
