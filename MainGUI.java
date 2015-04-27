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
import javax.swing.text.Highlighter;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.StyledEditorKit.FontSizeAction;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.Action;
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.font.TextAttribute;
import java.util.Map;
import java.util.Scanner;

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
	private JButton italicToggleButton_1;
	private JButton underlineToggleButton_1;

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
		
	
		docPanel.setLayout(null);
		
		
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(27, 7, 568, 28);
		toolBar.setFloatable(false);
		docPanel.add(toolBar);
		
		final JEditorPane editorPane = new JEditorPane();
		JScrollPane scrollPane = new JScrollPane(editorPane);
		scrollPane.setBounds(10, 41, 785, 580);
		
		editorPane.setContentType("text/html");
		
		editorPane.setEditorKit(new HTMLEditorKit());
		
		docPanel.add(scrollPane);
		
		
		/* 
		 * Sets the default fonts for user to use
		 */
		final String [] font = {"Arial", "Calibri", "Century", "Courrier New", "Georgia", "Impact", "Serif", "Times New Roman", "Trebuchet MS"};
		final JComboBox fontComboBox = new JComboBox(font);
		
		final Action [] fontAction = new Action[font.length];
		for(int i = 0; i< fontAction.length; i++){
			fontAction[i] = new StyledEditorKit.FontFamilyAction(font[i], font[i]);
			
		}
		fontComboBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				for(int i = 0; i< fontAction.length; i++){
					if(font[i].equals((String)fontComboBox.getSelectedItem())){
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
		final String [] sizes = new String[51];
		
		for(int i = 0; i < sizes.length-1; i++){
			sizes[i] = ""+i;
			
		}
		
		int [] sizesInt = new int[51];
		
		for(int i = 0; i < sizesInt.length-1; i++){
			sizesInt[i] = i;
			
		}
		
		final JComboBox sizeComboBox = new JComboBox(sizes);
		
		final Action [] fontSizeAction = new Action[sizes.length];
		for(int i = 0; i< fontSizeAction.length; i++){
			fontSizeAction[i] = new StyledEditorKit.FontSizeAction(sizes[i], sizesInt[i]);
			
		}
		sizeComboBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				for(int i = 0; i< fontSizeAction.length; i++){
					if(sizes[i].equals((String)sizeComboBox.getSelectedItem())){
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
		 * */
		JButton btnB = toolBar.add(new StyledEditorKit.BoldAction());
		btnB.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnB.setToolTipText("Bold");
		btnB.setText("B");
		
		/*
		 * Italic Button
		 * */
		JButton italicToggleButton = new JButton("I");
		italicToggleButton_1 = toolBar.add(new StyledEditorKit.ItalicAction());
		italicToggleButton_1.setText("I");
		italicToggleButton_1.setToolTipText("Italic");
		italicToggleButton_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));		
		
		
		/*
		 * Underline Button
		 * */
		JButton underlineToggleButton = new JButton("Underline");
		
		underlineToggleButton.setToolTipText("Underline");
		underlineToggleButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		underlineToggleButton_1 = toolBar.add(new StyledEditorKit.UnderlineAction());
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
		 * */
		JButton leftAlign = new JButton( new StyledEditorKit.AlignmentAction("Align Left", StyleConstants.ALIGN_LEFT) );
		toolBar.add(leftAlign);
		
		/*
		 * Center-Alignment Button
		 * */
		JButton centerAlign = new JButton( new StyledEditorKit.AlignmentAction("Align Center", StyleConstants.ALIGN_CENTER) );
		toolBar.add(centerAlign);
		
		/*
		 * Right-Alignment Button
		 * */
		JButton rightAlignment = new JButton( new StyledEditorKit.AlignmentAction("Align Right", StyleConstants.ALIGN_RIGHT) );
		toolBar.add(rightAlignment);
		
		/*
		 * Paint Button
		 * */
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
