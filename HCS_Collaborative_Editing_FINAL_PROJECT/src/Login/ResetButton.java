package Login;

/** Description of ResetButton:
* This class will reset the information for the username and password.
* It is opened in a JPanel and extends JFrame.
*@author HCS Group: Siddharth Sharma, Luis Guerrero, Maverick Tudisco, Chintan Patel
*@version Final Version: May 6th, 2015
*/

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ResetButton extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsername;
	private JTextField txtPasswordtoresetto;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ResetButton frame = new ResetButton();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ResetButton() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 348, 199);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(5, 5, 338, 28);
		txtUsername.setText("Username");
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		txtPasswordtoresetto = new JTextField();
		txtPasswordtoresetto.setBounds(5, 52, 338, 28);
		txtPasswordtoresetto.setText("passwordToResetTo");
		contentPane.add(txtPasswordtoresetto);
		txtPasswordtoresetto.setColumns(10);
		
		final JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(5, 103, 61, 16);
		contentPane.add(lblNewLabel);
		
		JButton btnResetPassword = new JButton("Reset Password");
		btnResetPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(LoginServer.loginMap.replace(txtUsername.getText(), txtPasswordtoresetto.getText()) != null){
					lblNewLabel.setText("Succesfully reset password to " + txtPasswordtoresetto.getText());
				}
			}
		});
		btnResetPassword.setBounds(5, 143, 338, 29);
		contentPane.add(btnResetPassword);
		
	}
}
