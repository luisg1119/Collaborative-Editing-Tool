/**
 * 
 */
package Model;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.AbstractListModel;

/** Description of User:
* 
*@author HCS Group: Siddharth Sharma, Luis Guerrero, Maverick Tudisco, Chintan Patel
*@version Final Version: May 6th, 2015
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
