/*
A program that allows a user to manage their bookmarks.
Copyright (C) 2025  Richard Varela

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

package org.bookmarkdb.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.bookmarkdb.view.GuiForm;
import org.bookmarkdb.view.ListMenuItem;
import org.bookmarkdb.view.FormDialog;
import org.bookmarkdb.view.TagsDialog;

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
	private JButton tagsButton;
	private JPanel buttonPanel;

	// List area
	private JList<ListMenuItem> itemList;
	private DefaultListModel<ListMenuItem> listModel;

	// Description box
	private JTextArea descriptionBox;
	private JPanel descriptionPanel;

	private final static int WIDTH = 1000;
	private final static int HEIGHT = 500;
	private final static String frameTitle = "Bookmark DB";
	private final static String frameTitleDirtyIndication = "Bookmark DB *";

	public MainGui() {
		System.out.println("Viewer constructor");
		// Create main frame
		this.mainFrame = new JFrame(this.frameTitle);
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
		this.tagsButton = new JButton("Tags");

		this.buttonPanel = new JPanel();
		this.buttonPanel.add(this.homeButton);
		this.buttonPanel.add(this.newButton);
		this.buttonPanel.add(this.editButton);
		this.buttonPanel.add(this.deleteButton);
		this.buttonPanel.add(this.copyButton);
		this.buttonPanel.add(this.searchField);
		this.buttonPanel.add(this.searchButton);
		this.buttonPanel.add(this.tagsButton);

		this.mainFrame.getContentPane().add(BorderLayout.NORTH, buttonPanel);

		// TODO: Clean up for better reading
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

	public void determineAndChangeDirtyIndication(boolean dirtyFlag) {
		if (dirtyFlag == true) {
			this.mainFrame.setTitle(frameTitleDirtyIndication);
		}
		else {
			this.mainFrame.setTitle(frameTitle);
		}
	}

	// Add Listeners
	public void addTagsButtonListener(ActionListener tagsButtonListener) {
		this.tagsButton.addActionListener(tagsButtonListener);
	}

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

	public FormDialog createFormDialog() {
		FormDialog formDialog = new FormDialog();
		formDialog.setVisible(true);
		return formDialog;
	}

	public FormDialog createFormDialog(final String url, final String title, final String description, ArrayList<String> tags) {
		FormDialog formDialog = new FormDialog();
		formDialog.setFormDialog(url, title, description, tags);
		formDialog.setVisible(true);
		return formDialog;
	}

	public void refreshListModel(final LinkedList<ListMenuItem> inOrderList) {
		System.out.println("refreshListModel");
		DefaultListModel<ListMenuItem> listModel = this.getListModel();
		listModel.removeAllElements();
		listModel.clear();

		for (ListMenuItem item : inOrderList) {
			listModel.addElement(item);
		}
	}

	public void showSearchResult(ListMenuItem searchResult) {
		DefaultListModel<ListMenuItem> listModel = this.getListModel();
		listModel.removeAllElements();
		listModel.clear();
		listModel.addElement(searchResult);
	}

	public JFileChooser createFileChooserWindow(String choice, boolean existsFlag) {
		JFileChooser fileSaver = new JFileChooser();
		
		if ((choice.equals("save") && !existsFlag) || choice.equals("save as")) {
			fileSaver.showSaveDialog(getMainFrame());
		}
		else {
			fileSaver.showOpenDialog(getMainFrame());
		}
		
		return fileSaver;
	}

	public JFileChooser createExportChooserWindow() {
		JFileChooser fileExporter = new JFileChooser();
		fileExporter.setAcceptAllFileFilterUsed(false);

		FileNameExtensionFilter xmlFilter = new FileNameExtensionFilter("XML", "xml");
		fileExporter.addChoosableFileFilter(xmlFilter);

		FileNameExtensionFilter htmlFilter = new FileNameExtensionFilter("HTML", "html");
		fileExporter.addChoosableFileFilter(htmlFilter);

		FileNameExtensionFilter mdFilter = new FileNameExtensionFilter("Markdown", "md");
		fileExporter.addChoosableFileFilter(mdFilter);
			
		FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Text", "txt");
		fileExporter.addChoosableFileFilter(txtFilter);

		// Future feature
		// FileNameExtensionFilter opmlFilter = new FileNameExtensionFilter("OPML", "opml");
		// fileExporter.addChoosableFileFilter(opmlFilter);

		fileExporter.showSaveDialog(getMainFrame());

		return fileExporter;
	}

	public void displayAboutDialogWindow() {
		AboutDialog aboutDialog = new AboutDialog();
		aboutDialog.setVisible(true);
	}

	public TagsDialog createTagsDialog(String[] tagsList) {
		TagsDialog tagsDialog = new TagsDialog(tagsList);
		tagsDialog.setVisible(true);
		return tagsDialog;
	}

	public void displayDocumentationDialogWindow() {
		DocumentationDialog docDialog = new DocumentationDialog();
		docDialog.setVisible(true);
	}

	public void createErrorWindow(String message) {
		JOptionPane.showMessageDialog(mainFrame, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
} // End of MainGui
