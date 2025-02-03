package org.bookmarkdb.controller;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.LinkedList;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

import org.bookmarkdb.model.*;
import org.bookmarkdb.view.*;

public class Controller {
	private final MainGui view;
	private final Model model;

	public Controller(final Model model, final MainGui view) {
		System.out.println("Controller constructor");
		// Model setup
		this.model = model;

		// View setup
		this.view = view;

		// Menu bar listeners
		this.view.addMenuNewListener(new menuNewListener());
		this.view.addMenuOpenListener(new menuOpenListener());
		this.view.addMenuSaveListener(new menuSaveListener());
		this.view.addMenuSaveAsListener(new menuSaveAsListener());
		this.view.addMenuExportListener(new menuExportListener());
		this.view.addMenuCopyListener(new menuCopyListener());
		this.view.addMenuNewItemListener(new menuNewItemListener());
		this.view.addMenuEditItemListener(new menuEditItemListener());
		this.view.addMenuDocumentationItemListener(new menuDocumentationItemListener());
		this.view.addMenuAboutItemListener(new menuAboutItemListener());

		// Button listeners
		this.view.addNewButtonListener(new newButtonListener());
		this.view.addEditButtonListener(new editButtonListener());
		this.view.addDeleteButtonListener(new deleteButtonListener());
		this.view.addCopyButtonListener(new copyButtonListener());
		this.view.addSearchButtonListener(new searchButtonListener());

		// List listener
		this.view.addListSelectionListenerToList(new listListener());
	}

	// Inner classes
	// Button listeners
	class newButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("new fired");
			GuiForm guiForm = new GuiForm();
			guiForm.setVisible(true);
			System.out.println("Does this run after window shows?");
		}
	} // End of newListener

	class editButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("edit fired");
			GuiForm guiForm = new GuiForm();
			guiForm.setVisible(true);
		}
	} // End of editListener

	class copyButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("copy fired");
			// Add actions
			ListMenuItem item = view.getItemList().getSelectedValue();
			try {
				Bookmark bookmark = model.getBookmarkByTitle(item.getItemName()); // Throws exception
				StringSelection urlString = new StringSelection(bookmark.getURL());
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(urlString, urlString);
			}
			catch(BookmarkException bkException) {
				System.out.println(bkException.getMessage());
			}
		}
	} // End of copyListener

	class deleteButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("delete fired");
			// Add actions
		}
	} // End of deleteListener

	class searchButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("search fired");
			// Add actions
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
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.showOpenDialog(view.getMainFrame());

			// TODO: Change to handle errors properly
			System.out.println(fileChooser.getSelectedFile().getAbsolutePath());
			try {
				model.inputDataFile(fileChooser.getSelectedFile().getAbsolutePath());

				LinkedList<Bookmark> bookmarkQueue = model.getQueueFromAVL();
				assert bookmarkQueue != null : "bookmarkQueue is null";

				JList<ListMenuItem> listMenu = view.getItemList();
				assert listMenu != null : "listMenu is null";

				DefaultListModel<ListMenuItem> listModel = view.getListModel();
				assert listModel != null : "listModel is null";

				for (Bookmark b : bookmarkQueue) {
					listModel.addElement(new ListMenuItem(b.getTitle(), b.getDescription()));
				}
			}
			catch(IOException e) {
				System.out.println(e.getMessage());
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
				System.out.println(e.getStackTrace());
			}
		} // End of actionPerformed
	} // End of menuOpenListener

	class menuSaveListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("save listener fired");
			JFileChooser fileSaver = new JFileChooser();
			fileSaver.showSaveDialog(view.getMainFrame());
			// saveFile using fileSaver
		}
	}

	class menuSaveAsListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("save as listener fired");
			JFileChooser fileSaver = new JFileChooser();
			fileSaver.showOpenDialog(view.getMainFrame());
			// saveFile using fileSaver
		}
	}

	class menuExportListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("export as listener fired");
			JFileChooser fileExporter = new JFileChooser();
			fileExporter.showSaveDialog(view.getMainFrame());
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
				ListMenuItem item = view.getItemList().getSelectedValue();
				view.getDescrptionBox().setText(item.getDescription());
			}
		}
	} // End of listListener
} // End of Controller
