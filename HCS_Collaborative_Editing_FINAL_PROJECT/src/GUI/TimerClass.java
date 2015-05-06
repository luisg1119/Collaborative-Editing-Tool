package GUI;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JTextPane;

import Editor.UpdateEditorCommand;


public class TimerClass {
	private Timer timer;
	
	public TimerClass(){
		timer = new Timer();
		timer.schedule(new saveTask(),0); //By default save the file at start
		
		//addEventListners on something and then add the following
		timer.cancel();  //Cancel the first timer you had
		timer.purge();   //Garbage Collector: Clean up timer queue (any cancels and completed)
		timer = new Timer(); 
		timer.schedule(new saveTask(), 10000);  //Call saveTask every 10 seconds
	}
	
	//An Inner class that extends TimerTask to be used in the timer
	private class saveTask extends TimerTask{
		private JTextPane edit;
		private ObjectOutputStream output;
		
	    public saveTask() {  //add parameters depending on what you need
	    					 //Note: You cannot use any private variables, why we need the parameters

		}

		public void run(){
	    	try {
	    		System.out.println("I have waited 10 seconds! On to the server!");
	    		
	    		//Temp so you wont get error
	    		//need to change to a different command(ObJecctOutput...) Ask Luis
				output.writeObject(new UpdateEditorCommand(edit.getText(), ""));
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	}
}
