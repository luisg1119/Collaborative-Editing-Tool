/**
 * 
 */
package Model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JPanel;

import Editor.MainTextPane;
import GUI.MainGUI;

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
	private long userID;
	public JList mainDocList;
	public User(String username, String password) {
		
		getContentPane().setLayout(null);
		setBounds(100, 100, 826, 492);
		setResizable(false);
	
		final long MAX_NUMBER_YOU_WANT_TO_HAVE = 9999999999999999L;
		final long MIN_NUMBER_YOU_WANT_TO_HAVE = 1000000000000000L;
		userID = Long.valueOf(new Random().nextLong() * 
		             (MAX_NUMBER_YOU_WANT_TO_HAVE - MIN_NUMBER_YOU_WANT_TO_HAVE));
		
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 820, 458);
		
		panel.setLayout(null);
		JLabel lblsAccount = new JLabel();
		lblsAccount.setBounds(330, 1, 171, 37);
		panel.add(lblsAccount);
		
		lblsAccount.setText(username + "'s account");
				
//		DefaultListModel<RevisionDocument> myDocModel = MainGUI.model;
//		mainDocList = new JList(myDocModel);
//		mainDocList.setBounds(511, 34, 309, 367);
//		panel.add(mainDocList);
		
		//RevisionDocument temp = (RevisionDocument) panel.;
		//myDocModel.addElement(temp);
				
						
		JList<?> list_1 = new JList<Object>();
		list_1.setBounds(0, 34, 326, 333);
		panel.add(list_1);
						
		JLabel lblDocumentsByMe = new JLabel("Documents By me");
		lblDocumentsByMe.setBounds(56, 11, 161, 16);
		panel.add(lblDocumentsByMe);
						
		JLabel lblDocumentsSharedWith = new JLabel("Documents Shared with me");
		lblDocumentsSharedWith.setBounds(592, 7, 194, 16);
		panel.add(lblDocumentsSharedWith);
		
		getContentPane().add(panel);
					
	}
	
	public boolean addToShareList(String user){
		if(shareList.contains(user) == false){
			shareList.add(user);
			return true;
		}
		return false;
	}
	
}//end of class
