/**
 * 
 */
package Model;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JButton;

/** Description of User:
* This class of User extends a JFrame and contains a ArrayList that is the shared list and it also has 2 public attributes.
* These are a String that represents a username followed by a String that is the password.
*@author HCS Group: Siddharth Sharma, Luis Guerrero, Maverick Tudisco, Chintan Patel
*@version Final Version: May 6th, 2015
*/

public class User extends JFrame {
	public ArrayList<String> shareList;
	public String username;
	public String password;
	public ArrayList<String> docList;
	public static DefaultListModel<String> myDocModel;
	private long userID;
	public User(String username, String password) {
		
		getContentPane().setLayout(null);
		setBounds(100, 100, 826, 492);
		setResizable(false);
		JLabel lblsAccount = new JLabel();
		lblsAccount.setBounds(6, 6, 171, 37);
		getContentPane().add(lblsAccount);

		lblsAccount.setText(username + "'s account");
		docList = new ArrayList<String>();
	
		final long MAX_NUMBER_YOU_WANT_TO_HAVE = 9999999999999999L;
		final long MIN_NUMBER_YOU_WANT_TO_HAVE = 1000000000000000L;
		userID = Long.valueOf(new Random().nextLong() * 
		             (MAX_NUMBER_YOU_WANT_TO_HAVE - MIN_NUMBER_YOU_WANT_TO_HAVE));
		
		myDocModel = new DefaultListModel<String>();
		
		JList<String> mainDocList = new JList<String>(myDocModel);
		mainDocList.setBounds(14, 76, 309, 367);
		getContentPane().add(mainDocList);

		//addToDocList();
		
		JList<?> list_1 = new JList<Object>();
		list_1.setBounds(452, 76, 326, 367);
		getContentPane().add(list_1);
		
		JLabel lblDocumentsByMe = new JLabel("Documents By me");
		lblDocumentsByMe.setBounds(30, 55, 161, 16);
		getContentPane().add(lblDocumentsByMe);
		
		JLabel lblDocumentsSharedWith = new JLabel("Documents Shared with me");
		lblDocumentsSharedWith.setBounds(452, 55, 194, 16);
		getContentPane().add(lblDocumentsSharedWith);
	}
	
	public boolean addToShareList(String user){
		if(shareList.contains(user) == false){
			shareList.add(user);
			return true;
		}
		return false;
	}
	
}
