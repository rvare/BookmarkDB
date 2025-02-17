package org.bookmarkdb.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;

import org.bookmarkdb.view.GuiForm;
import org.bookmarkdb.view.ListMenuItem;

// Should make most of the attributes have `final` so they don't get accidentally changed

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
	private JMenuItem deleteItemMenuItem;
	private JMenuItem docMenuItem;
	private JMenuItem aboutMenuItem;
	
	// Buttons
	private JButton homeButton;
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
		this.mainFrame = new JFrame("Bookmark DB");
		this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		// Create menu bar
		this.menuBar = new JMenuBar();
	
		// Create File menu and its items
		JMenu fileMenu = new JMenu("File");

		this.newMenuItem = new JMenuItem("New");
		fileMenu.add(newMenuItem);

		this.openMenuItem = new JMenuItem("Open");
		fileMenu.add(openMenuItem);

		this.saveMenuItem = new JMenuItem("Save");
		fileMenu.add(saveMenuItem);

		this.saveAsMenuItem = new JMenuItem("Save As");
		fileMenu.add(saveAsMenuItem);

		this.exportMenuItem = new JMenuItem("Export");
		fileMenu.add(exportMenuItem);

		// Create Edit menu and its items
		JMenu editMenu = new JMenu("Edit");

		this.copyMenuItem = new JMenuItem("Copy");
		editMenu.add(copyMenuItem);

		this.newItemMenuItem = new JMenuItem("New Item");
		editMenu.add(newItemMenuItem);

		this.editItemMenuItem = new JMenuItem("Edit Item");
		editMenu.add(editItemMenuItem);

		this.deleteItemMenuItem = new JMenuItem("Delete Item");
		editMenu.add(deleteItemMenuItem);

		// Create Help menu and its items
		JMenu helpMenu = new JMenu("Help");

		this.docMenuItem = new JMenuItem("Documentation");
		helpMenu.add(docMenuItem);

		this.aboutMenuItem = new JMenuItem("About");
		helpMenu.add(aboutMenuItem);

		// Add all menu items to the menu bar
		this.menuBar.add(fileMenu);
		this.menuBar.add(editMenu);
		this.menuBar.add(helpMenu);
		this.mainFrame.setJMenuBar(menuBar);


		// Create Buttons
		this.homeButton = new JButton("Home");
		this.newButton = new JButton("New");
		this.editButton = new JButton("Edit");
		this.copyButton = new JButton("Copy");
		this.deleteButton = new JButton("Delete");
		this.searchField = new JTextField(30);
		this.searchButton = new JButton("Search");

		this.buttonPanel = new JPanel();
		this.buttonPanel.add(homeButton);
		this.buttonPanel.add(newButton);
		this.buttonPanel.add(editButton);
		this.buttonPanel.add(deleteButton);
		this.buttonPanel.add(copyButton);
		this.buttonPanel.add(searchField);
		this.buttonPanel.add(searchButton);

		this.mainFrame.getContentPane().add(BorderLayout.NORTH, buttonPanel);

		// TODO: Clean up for better reading
		// TODO: Get rid of test items and add to the list instead
		// ListMenuItem[] item = {new ListMenuItem("Test1", "Description"), new ListMenuItem("Test2", "Description 2")};
		this.listModel = new DefaultListModel<ListMenuItem>();

		this.itemList = new JList<ListMenuItem>();
		this.itemList.setModel(listModel);
		this.mainFrame.getContentPane().add(BorderLayout.CENTER, itemList);

		// Create description area
		// TODO: Clean this up for better reading
		this.descriptionBox = new JTextArea(10, 10); // Sets how many characters by height and width, not by pixels
		this.descriptionPanel = new JPanel();
		JScrollPane descriptionBoxScroller = new JScrollPane(descriptionBox);

		descriptionBoxScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		descriptionBoxScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.descriptionPanel.add(descriptionBoxScroller);
		this.mainFrame.getContentPane().add(BorderLayout.SOUTH, descriptionBoxScroller);

		this.mainFrame.setSize(WIDTH, HEIGHT); // Set mainFrame's width and height
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

	public String getSearchFieldText() {
		return searchField.getText();
	}

	// Add Listeners
	public void addHomeButtonListener(ActionListener homeButtonListener) {
		this.homeButton.addActionListener(homeButtonListener);
	}

	public void addNewButtonListener(ActionListener newButtonListener) {
		this.newButton.addActionListener(newButtonListener);
	}

	public void addEditButtonListener(ActionListener editButtonListener) {
		this.editButton.addActionListener(editButtonListener);
	}

	public void addDeleteButtonListener(ActionListener deleteButtonListener) {
		this.deleteButton.addActionListener(deleteButtonListener);
	}

	public void addCopyButtonListener(ActionListener copyButtonListener) {
		this.copyButton.addActionListener(copyButtonListener);
	}

	public void addSearchButtonListener(ActionListener searchButtonListener) {
		this.searchButton.addActionListener(searchButtonListener);
	}

	public void addMenuNewListener(ActionListener menuNewListener) {
		this.newMenuItem.addActionListener(menuNewListener);
	}

	public void addMenuOpenListener(ActionListener menuOpenListener) {
		this.openMenuItem.addActionListener(menuOpenListener);
	}

	public void addMenuSaveListener(ActionListener menuSaveListener) {
		this.saveMenuItem.addActionListener(menuSaveListener);
	}

	public void addMenuSaveAsListener(ActionListener menuSaveAsListener) {
		this.saveAsMenuItem.addActionListener(menuSaveAsListener);
	}

	public void addMenuExportListener(ActionListener menuExportListener) {
		this.exportMenuItem.addActionListener(menuExportListener);
	}

	public void addMenuCopyListener(ActionListener menuCopyListener) {
		this.copyMenuItem.addActionListener(menuCopyListener);
	}

	public void addMenuNewItemListener(ActionListener menuNewItemListener) {
		this.newItemMenuItem.addActionListener(menuNewItemListener);
	}

	public void addMenuEditItemListener(ActionListener menuEditListener) {
		this.editItemMenuItem.addActionListener(menuEditListener);
	}

	public void addMenuDeleteItemListener(ActionListener menuDeleteItemListener) {
		this.deleteItemMenuItem.addActionListener(menuDeleteItemListener);
	}

	public void addMenuDocumentationItemListener(ActionListener menuDocumentationItemListener) {
		this.docMenuItem.addActionListener(menuDocumentationItemListener);
	}

	public void addMenuAboutItemListener(ActionListener menuAboutItemListener) {
		this.aboutMenuItem.addActionListener(menuAboutItemListener);
	}

	public void addListSelectionListenerToList(ListSelectionListener ll) {
		this.itemList.addListSelectionListener(ll);
	}

	// Methods
	public void showMainFrame() {
		this.mainFrame.setVisible(true);
	}

	public DefaultListModel<ListMenuItem> getListModel() {
		return this.listModel;
	}
} // End of MainGui
