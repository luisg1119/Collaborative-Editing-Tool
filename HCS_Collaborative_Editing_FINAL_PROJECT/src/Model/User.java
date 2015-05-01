/**
 * 
 */
package Model;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.AbstractListModel;

/**
 * Description of User: This is the class where a user profile exists. This
 * contains a username, password, and all documents associated with the user
 * account. It interacts with Document, ChatServer and LoginWindow.
 *
 * @author Group HCS
 * @version Initial Wednesday April 15, 2015
 */
public class User extends JFrame {
	public User(String username, String password) {
		getContentPane().setLayout(null);
		setBounds(100, 100, 500, 300);
		setResizable(false);
		JLabel lblsAccount = new JLabel(username + "'s account");
		lblsAccount.setBounds(19, 47, 171, 37);
		getContentPane().add(lblsAccount);

	}

}
