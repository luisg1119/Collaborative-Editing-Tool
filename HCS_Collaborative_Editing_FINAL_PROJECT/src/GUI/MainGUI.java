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
import javax.swing.text.DefaultEditorKit;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.UIManager;

import java.awt.Font;

import javax.swing.JMenu;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.font.TextAttribute;
import java.util.Map;

import javax.swing.JPopupMenu;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;

import java.awt.ScrollPane;

public class MainGUI extends JFrame {

	private JPanel mainPanel;
	private static final String defaultComboBoxText = "Fonts";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI frame = new MainGUI();
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
	public MainGUI() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 700);
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainPanel);
		mainPanel.setLayout(null);
		
		JPanel revisionPanel = new JPanel();
		revisionPanel.setForeground(Color.LIGHT_GRAY);
		revisionPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		revisionPanel.setBounds(952, 23, 232, 632);
		mainPanel.add(revisionPanel);
		
		JPanel chatPanel = new JPanel();
		chatPanel.setBounds(0, 23, 137, 632);
		mainPanel.add(chatPanel);
		chatPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		chatPanel.setForeground(Color.LIGHT_GRAY);
		
		JPanel docPanel = new JPanel();
		docPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		docPanel.setBounds(137, 23, 805, 632);
		mainPanel.add(docPanel);
		
		
		String [] fonts = {"Arial","Times New Roman","Impact","Cambria"};
		docPanel.setLayout(null);
		
		
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(27, 7, 324, 28);
		toolBar.setFloatable(false);
		docPanel.add(toolBar);
		
		final JEditorPane editorPane = new JEditorPane();
		JScrollPane scrollPane = new JScrollPane(editorPane);
		scrollPane.setBounds(10, 41, 785, 580);
		
		editorPane.setContentType("text/html");
		editorPane.setFont(new Font("Arial",0,10));
		docPanel.add(scrollPane);
		
		final JComboBox fontComboBox = new JComboBox(fonts);
		
		
		
		toolBar.add(fontComboBox);
		fontComboBox.setToolTipText("Fonts");
		
		
		final JComboBox sizeComboBox = new JComboBox();
		for(int i = 10; i < 51; i+=2){
			sizeComboBox.addItem(i);
			
		}
		toolBar.add(sizeComboBox);
		sizeComboBox.setToolTipText("Font Size");
		
		fontComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fontStr = (String) fontComboBox.getSelectedItem();
				int fontSize = (int) sizeComboBox.getSelectedItem();
				if (fontStr.equals("Times New Roman")){
					editorPane.setFont(new Font("Times New Roman", Font.PLAIN, fontSize));
				}
			}
		});
		
		JToggleButton boldToggleButton = new JToggleButton("B");
		toolBar.add(boldToggleButton);
		boldToggleButton.setToolTipText("Bold");
		boldToggleButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JToggleButton italicToggleButton = new JToggleButton("I");
		italicToggleButton.setToolTipText("Bold");
		italicToggleButton.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		toolBar.add(italicToggleButton);
		
		
		String underlineLabel = "<html><a style='text-decoration:underline'>U</a></html>";
		JToggleButton underlineToggleButton = new JToggleButton(underlineLabel);
		
		underlineToggleButton.setToolTipText("Bold");
		underlineToggleButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		toolBar.add(underlineToggleButton);
		
		String strikeLabel = "<html><a style='text-decoration:line-through'>abc</a></html>";
		JToggleButton strikethroughToggleButton = new JToggleButton(strikeLabel);
		strikethroughToggleButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		toolBar.add(strikethroughToggleButton);
		
		JButton paintButton = new JButton("Paint");
		paintButton.setBounds(701, 7, 94, 28);
		docPanel.add(paintButton);
		
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);
		menuBar.setBounds(0, 0, 1184, 24);
		mainPanel.add(menuBar);
		
		JMenu file = new JMenu("File");
		menuBar.add(file);
		
		JMenuItem newDoc = new JMenuItem("New");
		file.add(newDoc);
		
		JMenuItem openDoc = new JMenuItem("Open");
		file.add(openDoc);
		
		JMenuItem saveDoc = new JMenuItem("Save");
		file.add(saveDoc);
		
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
				editorPane.selectAll();
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
				JOptionPane.showMessageDialog(null, "HCS: Collaborative Editing Tool"
						+ "\nMade By: Siddharth Sharma, Maverick Tudisco-Guntert, "
						+ "Chintan Patel, Luis Guerrero\n", "About", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		help.add(aboutDoc);
	}
}
