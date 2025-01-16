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
	
		// Create File menu and its items
		JMenu fileMenu = new JMenu("File");

		JMenuItem newMenuItem = new JMenuItem("New");
		newMenuItem.addActionListener(new menuNewItemListener());

		JMenuItem openMenuItem = new JMenuItem("Open");
		openMenuItem.addActionListener(new menuOpenListener());

		JMenuItem saveMenuItem = new JMenuItem("Save");
		saveMenuItem.addActionListener(new menuSaveListener());

		JMenuItem saveAsMenuItem = new JMenuItem("Save As");
		saveAsMenuItem.addActionListener(new menuSaveAsListener());

		JMenuItem exportMenuItem = new JMenuItem("Export");
		exportMenuItem.addActionListener(new menuExportListener());

		fileMenu.add(newMenuItem);
		fileMenu.add(openMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.add(saveAsMenuItem);
		fileMenu.add(exportMenuItem);

		// Create Edit menu and its items
		JMenu editMenu = new JMenu("Edit");

		JMenuItem copyMenuItem = new JMenuItem("Copy");
		copyMenuItem.addActionListener(new menuCopyListener());
		editMenu.add(copyMenuItem);

		JMenuItem newItemMenuItem = new JMenuItem("New Item");
		newItemMenuItem.addActionListener(new menuNewItemListener());
		editMenu.add(newItemMenuItem);

		JMenuItem editItemMenuItem = new JMenuItem("Edit Item");
		editItemMenuItem.addActionListener(new menuEditItemListener());
		editMenu.add(editItemMenuItem);

		// Create Help menu and its items
		JMenu helpMenu = new JMenu("Help");

		JMenuItem docMenuItem = new JMenuItem("Documentation");
		docMenuItem.addActionListener(new menuDocumentationItemListener());
		helpMenu.add(docMenuItem);

		JMenuItem aboutMenuItem = new JMenuItem("About");
		aboutMenuItem.addActionListener(new menuAboutItemListener());
		helpMenu.add(aboutMenuItem);

		// Add all menu items to the menu bar
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
	// TODO Implement their functionality
	class menuNewListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("new listener fired");
		}
	}

	class menuOpenListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("open listener fired");
			JFileChooser fileOpener = new JFileChooser();
			fileOpener.showSaveDialog(mainFrame);
		}
	}

	class menuSaveListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("save listener fired");
			JFileChooser fileSaver = new JFileChooser();
			fileSaver.showSaveDialog(mainFrame);
			// saveFile using fileSaver
		}
	}

	class menuSaveAsListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("save as listener fired");
			JFileChooser fileSaver = new JFileChooser();
			fileSaver.showOpenDialog(mainFrame);
			// saveFile using fileSaver
		}
	}

	class menuExportListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("export as listener fired");
			JFileChooser fileExporter = new JFileChooser();
			fileExporter.showSaveDialog(mainFrame);
			// saveFile using fileSaver
		}
	}

	class menuCopyListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("copy listener fired");
		}
	}

	class menuNewItemListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("new item listener fired");
		}
	}

	class menuEditItemListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("edit item listener fired");
		}
	}

	class menuDocumentationItemListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("documentation item listener fired");
		}
	}

	class menuAboutItemListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("about item listener fired");
		}
	}

	// JList listener
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
