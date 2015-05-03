package Editor;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.html.HTMLEditorKit;

import Chat.AddMessageCommand;

public class MainTextPane extends JTextPane{
	private JScrollPane mainScroll;
	private Timer timer;
	public ObjectOutputStream output; // output stream to server
	private JTextPane edit; 
	
	public MainTextPane(){//(ObjectOutputStream output){
		//this.output = output;
		this.edit = new JTextPane();
		this.edit = this;
		timer = new Timer();
		timer.schedule(new saveTask(edit,output),0); //By default save the file at start
		
		mainScroll = new JScrollPane(this);
		mainScroll.setBounds(10, 41, 785, 580);

		setContentType("text/html");
		setEditorKit(new HTMLEditorKit());
		
		this.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void insertUpdate(DocumentEvent e) {
				timer.cancel();  //Cancel the first timer you had
				timer.purge();   //Garbage Collector: Clean up timer queue
				timer = new Timer(); 
				timer.schedule(new saveTask(edit,output), 9000);  //Call saveTask every 9 seconds
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				timer.cancel();  //Cancel the first timer you had
				timer.purge();   //Garbage Collector: Clean up timer queue
				timer = new Timer(); 
				timer.schedule(new saveTask(edit,output), 9000);  //Call saveTask every 9 seconds
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				timer.cancel();  //Cancel the first timer you had
				timer.purge();   //Garbage Collector: Clean up timer queue
				timer = new Timer(); 
				timer.schedule(new saveTask(edit,output), 9000);  //Call saveTask every 9 seconds
			} 
		});
	}
	
	public JScrollPane getScroll(){
		return mainScroll;
	}
	
	//What will handle the update of all users text panels
	public void update(List<String> text){
			int size = text.size();
			this.setText(text.get(size-1));
			repaint();
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
