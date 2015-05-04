package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.StyledEditorKit.FontSizeAction;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.UIManager;

import java.awt.Font;

import javax.swing.JMenu;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.TextAttribute;
import java.util.Calendar;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JPopupMenu;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;

import Chat.ChatClientStart;
import Chat.ChatPanelDesigner;
import Chat.DisconnectChatCommand;
import Editor.DisconnectEditorCommand;
import Editor.EditorClientStart;
import Editor.MainTextPane;
import Model.DisconnectChat;
import Model.LoginWindow;
import Model.RevisionDocument;
import Model.User;
import Server.ChatServer;

import java.awt.ScrollPane;

import javax.swing.JList;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.awt.Button;
import java.awt.FlowLayout;

public class MainGUI extends JFrame {
	private JPanel mainPanel;
	private static final String defaultComboBoxText = "Fonts";
	private JButton italicToggleButton_1;
	private JButton underlineToggleButton_1;
	static String username;
	static String password;
	public int filename = 0;
	private ChatClientStart chatPane;
	private EditorClientStart docPanel;

	// public JPanel chatPane;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Model.LoginWindow loginWindow = new LoginWindow();
					loginWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public MainGUI(String userName){
		username = userName;
		MainGUI window = new MainGUI();
	}
	
	//window event listener for closing chat window effectively
	private void closeChat(){
		// add a listener that sends a disconnect command to everything when closing
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent arg0) {
				try {
				//Make sure to add disconnect Chat and editor 
					ObjectOutputStream tempOutput = chatPane.returnOutput();
					ObjectInputStream tempInput = chatPane.returnInput();
					//if the Person Logging out has text that they did not send
					//erase the text before the window closes to get
					//rid of the "(x) is currently typing" message
					if(!chatPane.chat.textSend.getText().isEmpty()){
						chatPane.chat.textSend.setText("");
					}
					tempOutput.writeObject(new DisconnectChatCommand(username));
					tempOutput.close();
					tempInput.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	//window event listener for closing Editor window effectively
	private void closeEditor(){
		// add a listener that sends a disconnect command to everything when closing
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent arg0) {
				try {
				//Make sure to add disconnect Chat and editor 
					ObjectOutputStream tempOutput = docPanel.returnOutput();
					ObjectInputStream tempInput = docPanel.returnInput();
					tempOutput.writeObject(new DisconnectEditorCommand(username));
					tempOutput.close();
					tempInput.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	/**
	 * Create the frame.
	 */
	public MainGUI() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 700);
		getContentPane().setLayout(new BorderLayout());
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		//setContentPane(mainPanel);
		mainPanel.setLayout(null);

		 //Instantiate a new ChatClient 
		 chatPane = new ChatClientStart(LoginWindow.getHost(), Integer.parseInt(LoginWindow.getPort()), username);
		 //Instantiate the bounds
		 chatPane.setBounds(954, 23, 230, 632);
		 
		 chatPane.setForeground(Color.LIGHT_GRAY);
		 //Put color on the gui to know what is the Chat Windows
		 chatPane.setBackground(Color.RED);
		 //Position the Chat Client in the correct location in the GUI 
		 getContentPane().add(chatPane, BorderLayout.CENTER);
		 getContentPane().add(mainPanel, BorderLayout.CENTER);
		 closeChat();

		JPanel revisionPane = new JPanel();
		revisionPane.setBounds(0, 23, 137, 632);
		mainPanel.add(revisionPane);
		revisionPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
				null, null));
		revisionPane.setForeground(Color.LIGHT_GRAY);

		
		
		//Started adding from here
		
		 //Instantiate a new EditorClient
		 docPanel = new EditorClientStart(Integer.parseInt(LoginWindow.getPort()), LoginWindow.getHost(), username);
		 docPanel.setBounds(137, 23, 805, 632);
		 docPanel.setForeground(Color.LIGHT_GRAY);
		 //Blue is the middle panel
		 //docPanel.setBackground(Color.BLUE);
		 getContentPane().add(docPanel, BorderLayout.CENTER);
		 getContentPane().add(mainPanel,BorderLayout.CENTER);
		 closeEditor();
				 
		//Old Stuff		 
//		JPanel docPanel = new JPanel();
//		docPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
//				null, null));
//		docPanel.setBounds(137, 23, 805, 632);
//		mainPanel.add(docPanel);

//		docPanel.setLayout(null);

		JToolBar toolBar = new JToolBar();
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
				User userPanel = new User(username, password);
				userPanel.setVisible(true);
			}
		});
		btnMyAccount.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnMyAccount.setBounds(605, 7, 96, 28);
		toolBar.add(btnMyAccount);
//		docPanel.add(btnMyAccount);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);
		menuBar.setBounds(0, 0, 1200, 22);
		mainPanel.add(menuBar);

		JMenu file = new JMenu("File");
		menuBar.add(file);

		JMenuItem newDoc = new JMenuItem("New");
		file.add(newDoc);

		JMenuItem openDoc = new JMenuItem("Open");
		file.add(openDoc);

		final DefaultListModel<RevisionDocument> model = new DefaultListModel();
		// final Thread updater = new Thread() { // another thread to update the
		// private RevisionDocument currRevListDoc;
		//
		//
		// /*
		// * JList for saved documents
		// */
		// // model
		// /*
		// * (non-Javadoc)
		// *
		// * @see java.lang.Thread#run()
		// */
		// @Override
		// public void run() {
		// model.addElement(currRevListDoc);
		// try {
		// Thread.sleep(0);
		// } catch (InterruptedException e) {
		// throw new RuntimeException(e);
		// }
		// }
		// };
		// updater.start();
		revisionPane.setLayout(null);
		final JList list = new JList(model);

		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					RevisionDocument thisRevision = (RevisionDocument) list
							.getSelectedValue();
					
					//textPane.setDocument(thisRevision.doc);
				}
			}

		});
		list.setBounds(0, 0, 137, 632);
		revisionPane.add(list); // adds list to revisionPane

		/*
		 * Save Button - to save a document to revision history
		 */
		JMenuItem saveDoc = new JMenuItem("Save");
		saveDoc.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent arg0) {
				//Document thisDoc = textPane.getDocument();
				Calendar cal = Calendar.getInstance();
				//RevisionDocument newRevisedDocument = new RevisionDocument(cal,
					//	thisDoc, username);
				//model.addElement(newRevisedDocument);
				
				try {
					FileWriter out = new FileWriter(new File(System
							.getProperty("user.dir") + "/SavedDocuments",
							"edit_" + filename + ".html")); 
					filename++;
					//out.write(textPane.getText()); 
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 

			}
		});
		file.add(saveDoc);
		
		/*
		 * Double-click event on JList element
		 * */
		
//		ListSelectionListener listSelectionListener = new ListSelectionListener(){
//
//			@Override
//			public void valueChanged(ListSelectionEvent listSelectionEvent) {
//				// TODO Auto-generated method stub
//				System.out.println("Test");
//				boolean adjust = listSelectionEvent.getValueIsAdjusting();
//				System.out.println(adjust);
//			
//				if(!adjust){
//					JList list = (JList)listSelectionEvent.getSource();
//					String selection = list.getName();
//					System.out.println(selection);
//				}
//			}
//			
//		};
//		list.addListSelectionListener(listSelectionListener);
		
		MouseListener mouseListener = new MouseAdapter(){
			public void mouseClicked(MouseEvent mouseEvent){
				JList theList = (JList)mouseEvent.getSource();
				if(mouseEvent.getClickCount() == 2){
					int index = theList.locationToIndex(mouseEvent.getPoint());
					
					if(index >= 0){
						Object o = theList.getModel().getElementAt(index);
						
						try {
							FileReader fr = new FileReader(System.getProperty("user.dir") + "/SavedDocuments" + "/edit_" + (model.getSize()-(theList.locationToIndex(mouseEvent.getPoint()))-1) + ".html"); // incase anyone fucks this up - "C:\\Users\\Maverick\\Dropbox\\335 Code\\HCS_Collaborative_Editing_FINAL_PROJECT\\SavedDocuments\\" + "edit_" + (model.getSize()-(theList.locationToIndex(mouseEvent.getPoint()))-1) + ".html"
							BufferedReader br = new BufferedReader(fr);
							StringBuilder content = new StringBuilder(1024);
							String str = br.readLine();
							while((str = br.readLine()) != null){
								content.append(str);
							}
							//textPane.setText("<html> " + content);
							br.close();
							fr.close();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		};
		list.addMouseListener(mouseListener);

		JMenuItem saveAsDoc = new JMenuItem("Save As...");
		file.add(saveAsDoc);

		JMenuItem importDoc = new JMenuItem("Import...");
		file.add(importDoc);

		JMenuItem exportDoc = new JMenuItem("Export...");
		file.add(exportDoc);

		JMenuItem quitDoc = new JMenuItem("Quit");
		quitDoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		file.add(quitDoc);

		JMenu edit = new JMenu("Edit");
		menuBar.add(edit);

		JMenuItem selectAllDoc = new JMenuItem("Select All");
		selectAllDoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			//	textPane.selectAll();
			}
		});
		edit.add(selectAllDoc);

		JMenuItem cutDoc = new JMenuItem(new DefaultEditorKit.CutAction());
		cutDoc.setText("Cut");
		cutDoc.setMnemonic(KeyEvent.VK_T);
		edit.add(cutDoc);

		JMenuItem copyDoc = new JMenuItem(new DefaultEditorKit.CopyAction());
		copyDoc.setText("Copy");
		copyDoc.setMnemonic(KeyEvent.VK_C);
		edit.add(copyDoc);

		JMenuItem pasteDoc = new JMenuItem(new DefaultEditorKit.PasteAction());
		pasteDoc.setText("Paste");
		pasteDoc.setMnemonic(KeyEvent.VK_P);
		edit.add(pasteDoc);

		JMenu help = new JMenu("Help");
		menuBar.add(help);

		JMenuItem aboutDoc = new JMenuItem("About");
		aboutDoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane
						.showMessageDialog(
								null,
								"HCS: Collaborative Editing Tool"
										+ "\nMade By: Siddharth Sharma, Maverick Tudisco-Guntert, "
										+ "Chintan Patel, Luis Guerrero\n",
								"About", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		help.add(aboutDoc);

//		JButton btnNewButton = new JButton("Run Chat Client");
//		btnNewButton.setFont(new Font("Menlo", Font.PLAIN, 24));
//		btnNewButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				ChatClientStart chat = new ChatClientStart(LoginWindow
//						.getHost(), Integer.parseInt(LoginWindow.getPort()),
//						LoginWindow.getUsername());
//				chat.setGUI();
//			}
//		});
//		btnNewButton.setBounds(944, 28, 240, 644);
//		mainPanel.add(btnNewButton);

	}

	public static void usernameRetrieval(String name, String pword) {
		username = name;
		password = pword;
	}
}
