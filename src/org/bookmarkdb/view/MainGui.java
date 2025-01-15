package org.bookmarkdb.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;

public class MainGui {

	private JFrame mainFrame;

	// Menu bar
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem fileItem;
	private JMenuItem editItem;
	private JMenuItem helpItem;
	
	// Buttons
	private JButton newButton;
	private JButton editButton;
	private JButton copyButton;
	private JButton deleteButton;
	private JButton searchButton;
	private JTextField searchField;
	private JPanel buttonPanel;

	// List area
	private JList<TestItem> itemList;

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

		JMenu fileMenu = new JMenu("File");
		fileMenu.add(new JMenuItem("New"));
		fileMenu.add(new JMenuItem("Open"));
		fileMenu.add(new JMenuItem("Save"));
		fileMenu.add(new JMenuItem("Save As"));
		fileMenu.add(new JMenuItem("Export"));

		JMenu editMenu = new JMenu("Edit");
		editMenu.add(new JMenuItem("Copy"));
		editMenu.add(new JMenuItem("New Item"));
		editMenu.add(new JMenuItem("Edit Item"));

		JMenu helpMenu = new JMenu("Help");
		helpMenu.add(new JMenuItem("Documentation"));
		helpMenu.add(new JMenuItem("About"));

		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(helpMenu);
		mainFrame.setJMenuBar(menuBar);

		// Create Buttons
		newButton = new JButton("New");
		newButton.addActionListener(new newListener());

		editButton = new JButton("Edit");
		editButton.addActionListener(new editListener());

		copyButton = new JButton("Copy");
		copyButton.addActionListener(new copyListener());

		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new deleteListener());

		searchField = new JTextField(30);

		searchButton = new JButton("Search");
		searchButton.addActionListener(new searchListener());

		buttonPanel = new JPanel();
		buttonPanel.add(newButton);
		buttonPanel.add(editButton);
		buttonPanel.add(deleteButton);
		buttonPanel.add(copyButton);
		buttonPanel.add(searchField);
		buttonPanel.add(searchButton);

		mainFrame.getContentPane().add(BorderLayout.NORTH, buttonPanel);

		// TODO: Create list
		// TODO: Clean up for better reading
		TestItem[] item = {new TestItem("Test1", "Description"), new TestItem("Test2", "Description 2")};
		itemList = new JList<TestItem>(item);
		itemList.addListSelectionListener(new listListener());
		mainFrame.getContentPane().add(BorderLayout.CENTER, itemList);

		// Create description area
		// TODO: Clean this up for better reading
		descriptionBox = new JTextArea(10, 10); // How many characters, not by resolution
		descriptionPanel = new JPanel();
		JScrollPane scroller = new JScrollPane(descriptionBox);

		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		descriptionPanel.add(scroller);
		mainFrame.getContentPane().add(BorderLayout.SOUTH, scroller);

		// Set mainFrame's width and height
		mainFrame.setSize(WIDTH, HEIGHT);
	} // End Default constructor

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

	// Methods
	public void showMainFrame() {
		mainFrame.setVisible(true);
	}

	// Inner classes
	// TODO: Move to the controller
	// Button listeners
	class newListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("new fire");
		}
	} // End of newListener

	class editListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("edit fire");
		}
	} // End of editListener

	class copyListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("copy fire");
		}
	} // End of copyListener

	class deleteListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("delete fire");
		}
	} // End of deleteListener

	class searchListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("search fire");
		}
	} // End of searchListener

	// Menu bar listeners

	// Note: The following code was made using this reference
	// https://stackoverflow.com/questions/13800775/find-selected-item-of-a-jlist-and-display-it-in-real-time
	class listListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			if (!arg0.getValueIsAdjusting()) {
				TestItem item = itemList.getSelectedValue();
				descriptionBox.setText(item.getDescription());
			}
		}
	}
} // End of MainGui

class TestItem {
	private String itemName;
	private String itemDescription;

	public TestItem(String itemName, String desc) {
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
