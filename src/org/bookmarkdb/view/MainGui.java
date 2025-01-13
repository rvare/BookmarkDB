package org.bookmarkdb.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;

public class MainGui {

	public JFrame mainFrame;

	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem fileItem;
	private JMenuItem editItem;
	private JMenuItem helpItem;
	
	private JButton newButton;
	private JButton editButton;
	private JButton copyButton;
	private JButton deleteButton;
	private JButton searchButton;
	private JTextField searchField;

	private JTextArea descriptionBox;

	private JPanel buttonPanel;
	private JPanel descriptionPanel;

	private final static int WIDTH = 800;
	private final static int HEIGHT = 500;

	public MainGui() {
		

		// Create buttons
		mainFrame = new JFrame("Bookmark DB");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(new JMenuItem("New"));
		JMenu editMenu = new JMenu("Edit");
		editMenu.add(new JMenuItem("Copy"));
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		mainFrame.setJMenuBar(menuBar);

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
		String[] items = {"Test1", "Test2", "Test3"};
		JList<String> list = new JList<String>(items);
		// JPanel p = new JPanel();
		// p.add(list);
		mainFrame.getContentPane().add(BorderLayout.CENTER, list);

		// Create description area
		// TODO: Clean this up for better reading
		descriptionBox = new JTextArea(10, 10); // How many characters, not by resolution
		descriptionPanel = new JPanel();
		JScrollPane scroller = new JScrollPane(descriptionBox);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		descriptionPanel.add(scroller);
		mainFrame.getContentPane().add(BorderLayout.SOUTH, scroller);

		mainFrame.setSize(WIDTH, HEIGHT);
	} // Default constructor

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

	// Inner classes
	// Move to the controller
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
} // End of MainGui
