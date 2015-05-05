package Editor;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Calendar;
import java.util.List;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;
import javax.swing.text.Document;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;

import GUI.MainGUI;
import Model.RevisionDocument;
import Model.User;

public class EditorClient extends JPanel{
	
	private String clientName; // user name of the client
	private String host;
	private int editorPort;
	
	public static MainTextPane edit;
	//private MainGUI chat;

	private Socket server; // connection to server
	public ObjectOutputStream output; // output stream
	public ObjectInputStream input; // input stream
	private JButton italicToggleButton_1;
	private JButton underlineToggleButton_1;
	private JToolBar toolBar;
	
	public EditorClient(int port1, String host1, String name){
		
		this.editorPort = port1;
		this.host = host1;
		this.clientName = name;
		
		toolBar = new JToolBar();
		toolBar.setBounds(27, 7, 568, 28);
		toolBar.setFloatable(false);
		//docPanel.add(toolBar);

		//final MainTextPane textPane = new MainTextPane();
		//docPanel.add(textPane.getScroll());

		/*
		 * Sets the default fonts for user to use
		 */
		final String[] font = { "Arial", "Calibri", "Century", "Courrier New",
				"Georgia", "Impact", "Serif", "Times New Roman", "Trebuchet MS" };
		final JComboBox fontComboBox = new JComboBox(font);

		final Action[] fontAction = new Action[font.length];
		for (int i = 0; i < fontAction.length; i++) {
			fontAction[i] = new StyledEditorKit.FontFamilyAction(font[i],
					font[i]);

		}
		fontComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				for (int i = 0; i < fontAction.length; i++) {
					if (font[i].equals((String) fontComboBox.getSelectedItem())) {
						fontAction[i].actionPerformed(event);
						break;
					}
				}
			}
		});

		toolBar.add(fontComboBox);
		fontComboBox.setToolTipText("Fonts");

		/*
		 * Sets the default sizes for user to use
		 */
		final String[] sizes = new String[51];

		for (int i = 0; i < sizes.length - 1; i++) {
			sizes[i] = "" + i;

		}

		int[] sizesInt = new int[51];

		for (int i = 0; i < sizesInt.length - 1; i++) {
			sizesInt[i] = i;

		}

		final JComboBox sizeComboBox = new JComboBox(sizes);

		final Action[] fontSizeAction = new Action[sizes.length];
		for (int i = 0; i < fontSizeAction.length; i++) {
			fontSizeAction[i] = new StyledEditorKit.FontSizeAction(sizes[i],
					sizesInt[i]);

		}
		sizeComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				for (int i = 0; i < fontSizeAction.length; i++) {
					if (sizes[i].equals((String) sizeComboBox.getSelectedItem())) {
						fontSizeAction[i].actionPerformed(event);
						break;
					}
				}
			}
		});

		toolBar.add(sizeComboBox);
		sizeComboBox.setToolTipText("Font Size");

		/*
		 * Bold Button
		 */
		JButton btnB = toolBar.add(new StyledEditorKit.BoldAction());
		btnB.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnB.setToolTipText("Bold");
		btnB.setText("B");

		/*
		 * Italic Button
		 */
		italicToggleButton_1 = toolBar.add(new StyledEditorKit.ItalicAction());
		italicToggleButton_1.setText("I");
		italicToggleButton_1.setToolTipText("Italic");
		italicToggleButton_1.setFont(new Font("Tahoma",
				Font.BOLD | Font.ITALIC, 12));

		/*
		 * Underline Button
		 */
		JButton underlineToggleButton = new JButton("Underline");

		underlineToggleButton.setToolTipText("Underline");
		underlineToggleButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		underlineToggleButton_1 = toolBar
				.add(new StyledEditorKit.UnderlineAction());
		underlineToggleButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});

		underlineToggleButton_1.setText("U");
		underlineToggleButton_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		underlineToggleButton_1.setToolTipText("Underline");
		toolBar.add(underlineToggleButton_1);

		/*
		 * Left-Alignment Button
		 */
		JButton leftAlign = new JButton(new StyledEditorKit.AlignmentAction(
				"Align Left", StyleConstants.ALIGN_LEFT));
		toolBar.add(leftAlign);

		/*
		 * Center-Alignment Button
		 */
		JButton centerAlign = new JButton(new StyledEditorKit.AlignmentAction(
				"Align Center", StyleConstants.ALIGN_CENTER));
		toolBar.add(centerAlign);

		/*
		 * Right-Alignment Button
		 */
		JButton rightAlignment = new JButton(
				new StyledEditorKit.AlignmentAction("Align Right",
						StyleConstants.ALIGN_RIGHT));
		toolBar.add(rightAlignment);

		/*
		 * Paint Button
		 */
		JButton paintButton = new JButton("Paint");
		paintButton.setBounds(701, 7, 94, 28);
		toolBar.add(paintButton);
		//docPanel.add(paintButton);

		//My Account button
		JButton btnMyAccount = new JButton("My Account");
		btnMyAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User userPanel = new User(MainGUI.username, MainGUI.password);
				userPanel.setVisible(true);
			}
		});
		btnMyAccount.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnMyAccount.setBounds(605, 7, 96, 28);
		toolBar.add(btnMyAccount);
//		docPanel.add(btnMyAccount);
		try{
			// Open a connection to the server
			server = new Socket(host, editorPort);
			output = new ObjectOutputStream(server.getOutputStream());
			input = new ObjectInputStream(server.getInputStream());
			
			// write out the name of this client
			output.writeObject(clientName);

			setGUI();
			new Thread(new ServerHandler()).start();
			
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	private class ServerHandler implements Runnable{
		public void run() {
			try{
				while(true){
					// read a command from server and execute it
					EditorCommand<EditorClient> c = (EditorCommand<EditorClient>)input.readObject();
					c.execute(EditorClient.this);
				}
			}
			catch(SocketException e){
				return; // "gracefully" terminate after disconnect
			}
			catch (EOFException e) {
				return; // "gracefully" terminate
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void setGUI(){
		edit = new MainTextPane(clientName,output); 
		this.setLayout(new BorderLayout());
		this.add(edit, BorderLayout.CENTER);
		this.add(toolBar, BorderLayout.NORTH);
		this.setVisible(true);
		//this.setSize(800,600);
		this.setBorder((new BevelBorder(BevelBorder.LOWERED, null, null,null, null)));		
	}
	
	public void update(String text){//(List<String> text) {
		edit.updateDocument(text);
	}
	
	public ObjectOutputStream returnOutput(){
		return output;	
	}
	
	public ObjectInputStream returnInput(){
		return input;	
	}
	
	public RevisionDocument getRevisionDocument(){
		Document thisDoc =  edit.getDocument();
		Calendar cal = Calendar.getInstance();
		String username = MainGUI.username;
		RevisionDocument newRevisedDocument = new RevisionDocument(cal,thisDoc, username);
		return newRevisedDocument;
	}
	
	public String getTextContent(){
		System.out.println(edit.getText());
		return edit.getText();
	}
	
	public void setNewText(String text){
		edit.setText(text);
	}
	

}
