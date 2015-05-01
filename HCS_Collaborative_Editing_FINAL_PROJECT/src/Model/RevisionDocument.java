package Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.text.Document;

/** Description of Document:
* This is a class that interacts with the user and creates an initial document under their profile allowing the user to edit using text editor and let revisions exist.
*@author Group HCS
*@version Initial Wednesday April 15, 2015 
*/
public class RevisionDocument {
	public Calendar cal; //FIXME make all 3 private
	public Document doc;
	public String username;

	public RevisionDocument(Calendar cal, Document doc, String username){
		this.cal = cal;
		this.doc = doc;
		this.username = username;
	}
	@Override
	public String toString(){
		return cal.getTime().toString(); //add username maybe?
		
	}
}
