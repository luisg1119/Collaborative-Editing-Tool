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
import Editor.EditorClient;
import Editor.MainTextPane;
import Model.DisconnectChat;
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
	public static String username;
	public static String password;
	public int filename = 0;
	private ChatClientStart chatPane;
	private EditorClient docPanel;

	// public JPanel chatPane;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login.LoginWindow loginWindow = new Login.LoginWindow();
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
		 chatPane = new ChatClientStart(Login.LoginWindow.getHost(), Integer.parseInt(Login.LoginWindow.getPort()), username);
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
		 docPanel = new EditorClient(Integer.parseInt(Login.LoginWindow.getPort()), Login.LoginWindow.getHost(), username);
		 docPanel.setBounds(137, 23, 805, 632);
		 docPanel.setForeground(Color.LIGHT_GRAY);
		 //Blue is the middle panel
		 //docPanel.setBackground(Color.BLUE);
		 getContentPane().add(docPanel);
		 getContentPane().add(mainPanel);
		 closeEditor();
		 //scroll enabled
		 docPanel.add(EditorClient.edit.getScroll());

				 
		//Old Stuff		 
//		JPanel docPanel = new JPanel();
//		docPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
//				null, null));
//		docPanel.setBounds(137, 23, 805, 632);
//		mainPanel.add(docPanel);

//		docPanel.setLayout(null);


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
				
				model.addElement(docPanel.getRevisionDocument());
				
				try {
					FileWriter out = new FileWriter(new File(System
							.getProperty("user.dir") + "/SavedDocuments",
							"edit_" + filename + ".html")); 
					filename++;
					System.out.println(docPanel.getTextContent());
					out.write(docPanel.getTextContent());
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
							docPanel.setNewText("<html> " + content);
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
