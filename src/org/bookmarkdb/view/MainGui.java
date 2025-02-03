package org.bookmarkdb.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;

import org.bookmarkdb.view.GuiForm;
import org.bookmarkdb.view.ListMenuItem;

public class MainGui {
	private JFrame mainFrame;

	// Menu bar
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem newMenuItem;
	private JMenuItem openMenuItem;
	private JMenuItem saveMenuItem;
	private JMenuItem saveAsMenuItem;
	private JMenuItem exportMenuItem;
	private JMenuItem copyMenuItem;
	private JMenuItem newItemMenuItem;
	private JMenuItem editItemMenuItem;
	private JMenuItem docMenuItem;
	private JMenuItem aboutMenuItem;
	
	// Buttons
	private JButton newButton;
	private JButton editButton;
	private JButton copyButton;
	private JButton deleteButton;
	private JButton searchButton;
	private JTextField searchField;
	private JPanel buttonPanel;

	// List area
	private JList<ListMenuItem> itemList;
	private DefaultListModel<ListMenuItem> listModel;

	// Description box
	private JTextArea descriptionBox;
	private JPanel descriptionPanel;

	private final static int WIDTH = 800;
	private final static int HEIGHT = 500;

	public MainGui() {
		System.out.println("Viewer constructor");
		// Create main frame
		mainFrame = new JFrame("Bookmark DB");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create menu bar
		menuBar = new JMenuBar();
	
		// Create File menu and its items
		JMenu fileMenu = new JMenu("File");

		newMenuItem = new JMenuItem("New");
		fileMenu.add(newMenuItem);

		openMenuItem = new JMenuItem("Open");
		fileMenu.add(openMenuItem);

		saveMenuItem = new JMenuItem("Save");
		fileMenu.add(saveMenuItem);

		saveAsMenuItem = new JMenuItem("Save As");
		fileMenu.add(saveAsMenuItem);

		exportMenuItem = new JMenuItem("Export");
		fileMenu.add(exportMenuItem);


		// Create Edit menu and its items
		JMenu editMenu = new JMenu("Edit");

		copyMenuItem = new JMenuItem("Copy");
		editMenu.add(copyMenuItem);

		newItemMenuItem = new JMenuItem("New Item");
		editMenu.add(newItemMenuItem);

		editItemMenuItem = new JMenuItem("Edit Item");
		editMenu.add(editItemMenuItem);

		// Create Help menu and its items
		JMenu helpMenu = new JMenu("Help");

		docMenuItem = new JMenuItem("Documentation");
		helpMenu.add(docMenuItem);

		aboutMenuItem = new JMenuItem("About");
		helpMenu.add(aboutMenuItem);

		// Add all menu items to the menu bar
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(helpMenu);
		mainFrame.setJMenuBar(menuBar);

		// Create Buttons
		newButton = new JButton("New");
		editButton = new JButton("Edit");
		copyButton = new JButton("Copy");
		deleteButton = new JButton("Delete");
		searchField = new JTextField(30);
		searchButton = new JButton("Search");

		buttonPanel = new JPanel();
		buttonPanel.add(newButton);
		buttonPanel.add(editButton);
		buttonPanel.add(deleteButton);
		buttonPanel.add(copyButton);
		buttonPanel.add(searchField);
		buttonPanel.add(searchButton);

		mainFrame.getContentPane().add(BorderLayout.NORTH, buttonPanel);

		// TODO: Clean up for better reading
		// TODO: Get rid of test items and add to the list instead
		// ListMenuItem[] item = {new ListMenuItem("Test1", "Description"), new ListMenuItem("Test2", "Description 2")};
		listModel = new DefaultListModel<ListMenuItem>();
		// listModel.addElement(new ListMenuItem("Test1", "Description"));
		// listModel.addElement(new ListMenuItem("Test2", "Description 2"));

		itemList = new JList<ListMenuItem>();
		itemList.setModel(listModel);
		mainFrame.getContentPane().add(BorderLayout.CENTER, itemList);

		// Create description area
		// TODO: Clean this up for better reading
		descriptionBox = new JTextArea(10, 10); // Sets how many characters by height and width, not by pixels
		descriptionPanel = new JPanel();
		JScrollPane descriptionBoxScroller = new JScrollPane(descriptionBox);

		descriptionBoxScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		descriptionBoxScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		descriptionPanel.add(descriptionBoxScroller);
		mainFrame.getContentPane().add(BorderLayout.SOUTH, descriptionBoxScroller);

		// Set mainFrame's width and height
		mainFrame.setSize(WIDTH, HEIGHT);
	} // End Default constructor

	// Getters
	public JFrame getMainFrame() {
		return this.mainFrame;
	}

	public JList<ListMenuItem> getItemList() {
		return this.itemList;
	}

	public JTextArea getDescrptionBox() {
		return this.descriptionBox;
	}

	// Setters
	public void addNewButtonListener(ActionListener newButtonListener) {
		newButton.addActionListener(newButtonListener);
	}

	public void addEditButtonListener(ActionListener editButtonListener) {
		editButton.addActionListener(editButtonListener);
	}

	public void addDeleteButtonListener(ActionListener deleteButtonListener) {
		deleteButton.addActionListener(deleteButtonListener);
	}

	public void addCopyButtonListener(ActionListener copyButtonListener) {
		copyButton.addActionListener(copyButtonListener);
	}

	public void addSearchButtonListener(ActionListener searchButtonListener) {
		searchButton.addActionListener(searchButtonListener);
	}

	public void addMenuNewListener(ActionListener menuNewListener) {
		newMenuItem.addActionListener(menuNewListener);
	}

	public void addMenuOpenListener(ActionListener menuOpenListener) {
		openMenuItem.addActionListener(menuOpenListener);
	}

	public void addMenuSaveListener(ActionListener menuSaveListener) {
		saveMenuItem.addActionListener(menuSaveListener);
	}

	public void addMenuSaveAsListener(ActionListener menuSaveAsListener) {
		saveAsMenuItem.addActionListener(menuSaveAsListener);
	}

	public void addMenuExportListener(ActionListener menuExportListener) {
		exportMenuItem.addActionListener(menuExportListener);
	}

	public void addMenuCopyListener(ActionListener menuCopyListener) {
		copyMenuItem.addActionListener(menuCopyListener);
	}

	public void addMenuNewItemListener(ActionListener menuNewItemListener) {
		newItemMenuItem.addActionListener(menuNewItemListener);
	}

	public void addMenuEditItemListener(ActionListener menuEditListener) {
		editItemMenuItem.addActionListener(menuEditListener);
	}

	public void addMenuDocumentationItemListener(ActionListener menuDocumentationItemListener) {
		docMenuItem.addActionListener(menuDocumentationItemListener);
	}

	public void addMenuAboutItemListener(ActionListener menuAboutItemListener) {
		aboutMenuItem.addActionListener(menuAboutItemListener);
	}

	public void addListSelectionListenerToList(ListSelectionListener ll) {
		itemList.addListSelectionListener(ll);
	}

	// Methods
	public void showMainFrame() {
		mainFrame.setVisible(true);
	}

	public DefaultListModel<ListMenuItem> getListModel() {
		return this.listModel;
	}
} // End of MainGui

class TestItem {
	private String itemName;
	private String itemDescription;

	public TestItem(final String itemName, final String desc) {
		this.itemName = itemName;
		this.itemDescription = desc;
	}
	
	@Override
	public String toString() {
		return this.itemName;
	}
	
	public String getDescription() {
		return this.itemDescription;
	}
}
