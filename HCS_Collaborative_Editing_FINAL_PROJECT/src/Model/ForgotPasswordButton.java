package Model;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ForgotPasswordButton extends JFrame {
	private JTextField textField;

	public ForgotPasswordButton() {
		setResizable(false);
		getContentPane().setLayout(null);

		setBounds(100, 100, 400, 227);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		textField = new JTextField();
		textField.setBounds(123, 23, 200, 28);
		getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(36, 26, 109, 22);
		getContentPane().add(lblUsername);

		final JLabel mainLabel = new JLabel("Enter username and press submit");
		mainLabel.setBounds(55, 100, 265, 28);
		getContentPane().add(mainLabel);

		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField.equals("")  || (!LoginServer.loginMap.containsKey(textField.getText()))) {
					mainLabel.setText("Please enter a valid username!");
				}

				else {
					mainLabel.setText("Password: " + LoginServer.loginMap.get(textField.getText()));
				}
			}
		});
		btnNewButton.setBounds(68, 162, 117, 29);
		getContentPane().add(btnNewButton);

		JButton btnDone = new JButton("Done");
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnDone.setBounds(212, 162, 117, 29);
		getContentPane().add(btnDone);
	}
}
