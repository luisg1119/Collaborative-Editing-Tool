package GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import Chat.ChatClientStart;
import Chat.ChatPanelDesigner;
import Chat.DisconnectChatCommand;
import Editor.AddTextCommand;
import Editor.DisconnectEditorCommand;
import Editor.EditorClient;
import Editor.MainTextPane;
import Editor.RevisionCommand;
import Login.LoginServer;
import Model.RevisionDocument;
import Model.User;
import Paint.DisconnectPaintCommand;
import Server.ChatServer;

import java.awt.ScrollPane;

import javax.swing.JList;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
//import java.swing.filechooser.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.awt.Button;
import java.awt.FlowLayout;

import javax.swing.JLabel;

/**
 * Description of MainGUI: The MainGUI is what creates a mainPanel and the
 * comboBox with different fonts, as well as a lot of the functions of bold,
 * italics, etc. This class creates the two JButtons which have the functionality
 * of Italic and Bold. They also contain a static username and password. It has
 * a built in chatPane as well and it has different menu items and one of those
 * is save. There is also a String that is the docName.
 *
 * @author HCS Group: Siddharth Sharma, Luis Guerrero, Maverick Tudisco, Chintan
 *         Patel
 * @version Final Version: May 6th, 2015
 */

public class MainGUI extends JFrame {
	private JPanel mainPanel;
	private static final String defaultComboBoxText = "Fonts";
	private JButton italicToggleButton_1;
	private JButton underlineToggleButton_1;
	public static String username;
	public static String password;
	private ChatClientStart chatPane;
	private static int counter;
	public static EditorClient docPanel;
	public static JMenuItem saveDoc;
	public static JLabel lastUpdatedLabel;
	static JPanel revisionPane;
	private JTextField filename = new JTextField(), dir = new JTextField();
	private JMenu jMenuFont;
	private User thisUser;
	static String docName;
	public static int paintGuiCount;
	public static int lengthOfList = 1;
	public static DefaultListModel<RevisionDocument> model;
	private static ArrayList<String> stringHolder;

	// public JPanel chatPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// getLengthOfList();

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

	// window event listener for closing chat window effectively
	private void closeChat() {
		// add a listener that sends a disconnect command to everything when
		// closing
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				try {
					// Make sure to add disconnect Chat and editor
					ObjectOutputStream tempOutput = chatPane.returnOutput();
					ObjectInputStream tempInput = chatPane.returnInput();
					// if the Person Logging out has text that they did not send
					// erase the text before the window closes to get
					// rid of the "(x) is currently typing" message
					if (!chatPane.chat.textSend.getText().isEmpty()) {
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

	// window event listener for closing Editor window effectively
	private void closeEditor() {
		// add a listener that sends a disconnect command to everything when
		// closing
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				try {
					// Make sure to add disconnect Chat and editor
					ObjectOutputStream tempOutput = docPanel.returnOutput();
					ObjectInputStream tempInput = docPanel.returnInput();
					tempOutput
							.writeObject(new DisconnectEditorCommand(username));
					tempOutput.close();
					tempInput.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void closePaint() {
		// add a listener that sends a disconnect command to everything when
		// closing
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				try {
					// Make sure to add disconnect Chat and editor
					ObjectOutputStream tempOutput = docPanel.returnOutput();
					ObjectInputStream tempInput = docPanel.returnInput();
					tempOutput
							.writeObject(new DisconnectPaintCommand(username));
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
		stringHolder = new ArrayList<String>();
		final JFrame thisFrame = this;
		paintGuiCount = 0;
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 700);
		getContentPane().setLayout(new BorderLayout());
		final User thisUser = new User(username, password);
		setDocumentName(this, thisUser);
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPanel.setLayout(null);
		// Instantiate a new ChatClient
		chatPane = new ChatClientStart(Login.LoginWindow.getHost(),
				Integer.parseInt(Login.LoginWindow.getPort()), username);
		// Instantiate the bounds
		chatPane.setBounds(954, 23, 230, 632);

		chatPane.setForeground(Color.LIGHT_GRAY);
		// Put color on the gui to know what is the Chat Windows
		chatPane.setBackground(Color.RED);
		// Position the Chat Client in the correct location in the GUI
		getContentPane().add(chatPane, BorderLayout.CENTER);
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		closeChat();

		revisionPane = new JPanel();
		mainPanel.add(revisionPane);

		// Started adding from here
		// Instantiate a new EditorClient
		docPanel = new EditorClient(Integer.parseInt(Login.LoginWindow
				.getPort()), Login.LoginWindow.getHost(), username);
		docPanel.setBounds(137, 23, 805, 632);
		docPanel.setForeground(Color.LIGHT_GRAY);
		getContentPane().add(docPanel);
		getContentPane().add(mainPanel);
		closeEditor();
		closePaint();
		// scroll enabled
		docPanel.add(EditorClient.edit.getScroll());

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);
		menuBar.setBounds(0, 0, 1200, 22);
		mainPanel.add(menuBar);

		JMenu file = new JMenu("File");
		menuBar.add(file);

		JMenuItem newDoc = new JMenuItem("New");
		file.add(newDoc);

		JMenuItem openDoc = new JMenuItem("Open");
		openDoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Document doc = MainTextPane.edit.getDocument();
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(null); // replace null
																// with your
																// swing
																// container
				File file = null;
				if (returnVal == JFileChooser.APPROVE_OPTION)
					file = chooser.getSelectedFile();
				BufferedReader in;
				try {
					in = new BufferedReader(new FileReader(file));
					String line = in.readLine();
					MainTextPane.edit.setText("");
					while (line != null) {
						doc.insertString(doc.getLength(), (line + "\n"), null);
						line = in.readLine();
					}
				} catch (BadLocationException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		file.add(openDoc);

		JMenu jMenuFont = new JMenu("Colors");

		model = new DefaultListModel();

		revisionPane.setLayout(null);
		final JList list = new JList(model);
		counter = 0;

		/*
		 * Update the initial revisionHistory
		 */

		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					RevisionDocument revisionPaneRevision = (RevisionDocument) list
							.getSelectedValue();

				}
			}

		});

		list.setBounds(0, 0, 137, 632);
		revisionPane.add(list);

		revisionPane.setBounds(0, 23, 137, 632);
		revisionPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
				null, null));
		revisionPane.setForeground(Color.LIGHT_GRAY);

		/*
		 * Save Button - saves a document to revision history and updates length
		 * of list to file lengthOfList.txt
		 */
		saveDoc = new JMenuItem("Save");

		saveDoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					System.out.println("I am in textListener");
					docPanel.returnOutput().writeObject(
							new RevisionCommand(Editor.MainTextPane.edit
									.getText()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		// TOok THe document revsison from here
		file.add(saveDoc);

		/*
		 * Click Event for JList items
		 */
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseEvent) {
				JList theList = (JList) mouseEvent.getSource();
				if (mouseEvent.getClickCount() == 2) {
					int index = theList.locationToIndex(mouseEvent.getPoint());

					if (index >= 0) {
						Object o = theList.getModel().getElementAt(index);

						try {

							FileReader fr = new FileReader(
									System.getProperty("user.dir")
											+ "/SavedDocuments"
											+ "/"
											+ docName
											+ (model.getSize()
													- (theList
															.locationToIndex(mouseEvent
																	.getPoint())) - 1)
											+ ".html");

							BufferedReader br = new BufferedReader(fr);
							StringBuilder content = new StringBuilder(1024);
							String str = br.readLine();
							while ((str = br.readLine()) != null) {
								content.append(str);
							}
							Editor.MainTextPane.edit.setText("" + content);
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

		/*
		 * Save As... Button
		 */
		JMenuItem saveAsDoc = new JMenuItem("Save As...");
		saveAsDoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					saveMap();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		file.add(saveAsDoc);

		JMenuItem quitDoc = new JMenuItem("Quit");
		quitDoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		file.add(quitDoc);

		JMenu edit = new JMenu("Edit");
		menuBar.add(edit);

		JMenuItem bulletItem = new JMenuItem("Insert Bullet");
		edit.add(bulletItem);
		bulletItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Document doc = MainTextPane.edit.getDocument();
				try {
					doc.insertString(doc.getLength(), "\n-   ", null);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});

		JMenuItem selectAllDoc = new JMenuItem("Select All");
		selectAllDoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainTextPane.edit.selectAll();

			}
		});

		edit.add(selectAllDoc);

		newDoc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setDocumentName(thisFrame, thisUser);
				MainTextPane.edit.setText("");
				model.clear();
			}

		});
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

		JMenu jMenuFont1 = new JMenu("Font Color");
		menuBar.add(jMenuFont1);

		lastUpdatedLabel = new JLabel("");
		lastUpdatedLabel.setBounds(151, 663, 289, 15);
		mainPanel.add(lastUpdatedLabel);

		jMenuFont1.add(new JMenuItem(new StyledEditorKit.ForegroundAction(
				("Aqua"), new Color(0, 255, 255))));
		jMenuFont1.add(new JMenuItem(new StyledEditorKit.ForegroundAction(
				("Black"), new Color(0, 0, 0))));
		jMenuFont1.add(new JMenuItem(new StyledEditorKit.ForegroundAction(
				("Gray"), new Color(128, 128, 128))));
		jMenuFont1.add(new JMenuItem(new StyledEditorKit.ForegroundAction(
				("Green"), new Color(0, 128, 0))));
		jMenuFont1.add(new JMenuItem(new StyledEditorKit.ForegroundAction(
				("Lime"), new Color(0, 255, 0))));
		jMenuFont1.add(new JMenuItem(new StyledEditorKit.ForegroundAction(
				("Dark Red"), new Color(128, 0, 0))));
		jMenuFont1.add(new JMenuItem(new StyledEditorKit.ForegroundAction(
				("Red"), new Color(255, 0, 0))));
		JMenuItem menuItem = new JMenuItem(
				new StyledEditorKit.ForegroundAction(("Silver"), new Color(192,
						192, 192)));
		jMenuFont1.add(menuItem);
		jMenuFont1.add(new JMenuItem(new StyledEditorKit.ForegroundAction(
				("Teal"), new Color(0, 128, 128))));
		jMenuFont1.add(new JMenuItem(new StyledEditorKit.ForegroundAction(
				("Yellow"), new Color(255, 255, 0))));

		JMenu mnPermissions = new JMenu("Permissions");
		menuBar.add(mnPermissions);

		JMenuItem addPermissions = new JMenuItem("Add permissions");
		mnPermissions.add(addPermissions);

		addPermissions.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String tempUser = JOptionPane
						.showInputDialog("Please enter username to grant permissions to a user to edit");
				if (LoginServer.loginMap.containsKey(tempUser)) {
					JOptionPane.showMessageDialog(null, tempUser
							+ " now has permissions to edit your document");
					thisUser.addToShareList(tempUser);
					MainTextPane.edit.setEditable(true);
				} else {
					JOptionPane.showMessageDialog(null, "Error " + tempUser
							+ " not found");
					thisUser.addToShareList(tempUser);
				}
			}

		});

		JMenuItem removePermissions = new JMenuItem("Remove permissions");
		mnPermissions.add(removePermissions);
		removePermissions.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainTextPane.edit.setEditable(false);
			}
		});

	}

	public static void usernameRetrieval(String name, String pword) {
		username = name;
		password = pword;
	}

	public void saveMap() throws IOException {
		String sb = Editor.MainTextPane.edit.getText();
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("/home/me/Documents"));
		int retrival = chooser.showSaveDialog(null);
		if (retrival == JFileChooser.APPROVE_OPTION) {
			try (FileWriter fw = new FileWriter(chooser.getSelectedFile())) {
				fw.write(sb.toString());
			}
		}
	}

	public static void setDocumentName(JFrame thisFrame, User thisUser) {
		docName = JOptionPane.showInputDialog("Please enter a Document Name");
		thisFrame.setTitle(docName);
	}

	public static void addToRevisionDoc(String text) {
		if (stringHolder.contains(text)) {
			return;
		} else {
			stringHolder.add(text);
			model.addElement(MainGUI.docPanel.getRevisionDocument());

			try {
				FileWriter out2 = new FileWriter(new File(
						System.getProperty("user.dir") + "/SavedDocuments",
						"lengthOfList.txt"));
				FileWriter out = new FileWriter(new File(
						System.getProperty("user.dir") + "/SavedDocuments",
						docName + counter + ".html"));
				counter++;
				out.write(Editor.MainTextPane.edit.getText());
				System.out.println(lengthOfList);
				out2.write("" + lengthOfList);
				out2.close();
				lengthOfList++;
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}// end of class

