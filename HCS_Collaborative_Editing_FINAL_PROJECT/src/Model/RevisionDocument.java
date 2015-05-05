package Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.text.Document;

/** Description of RevisionDocument:
* This class is called RevisionDocument and it creates 3 things that are included in revisions, Calendar, Document, and a username.
*@author HCS Group: Siddharth Sharma, Luis Guerrero, Maverick Tudisco, Chintan Patel
*@version Final Version: May 6th, 2015
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
