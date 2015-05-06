/**
 * 
 */
package Model;

import java.util.ArrayList;

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
	public User(String username, String password) {
		getContentPane().setLayout(null);
		setBounds(100, 100, 826, 492);
		setResizable(false);
		JLabel lblsAccount = new JLabel();
		lblsAccount.setBounds(6, 6, 171, 37);
		getContentPane().add(lblsAccount);

		lblsAccount.setText(username + "s account");
		
		JList list = new JList();
		list.setBounds(14, 76, 309, 367);
		getContentPane().add(list);
		
		JList list_1 = new JList();
		list_1.setBounds(452, 76, 326, 367);
		getContentPane().add(list_1);
		
		JButton btnOpenSelectedDoc = new JButton("Open Selected Doc");
		btnOpenSelectedDoc.setBounds(304, 441, 171, 29);
		getContentPane().add(btnOpenSelectedDoc);
		
		JLabel lblDocumentsByMe = new JLabel("Documents By me");
		lblDocumentsByMe.setBounds(30, 55, 161, 16);
		getContentPane().add(lblDocumentsByMe);
		
		JLabel lblDocumentsSharedWith = new JLabel("Documents Shared with me");
		lblDocumentsSharedWith.setBounds(452, 55, 194, 16);
		getContentPane().add(lblDocumentsSharedWith);
	}
}
