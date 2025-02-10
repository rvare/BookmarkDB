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
import java.util.*;

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
		this.view.addMenuDeleteItemListener(new menuDeleteItemListener());
		this.view.addMenuDocumentationItemListener(new menuDocumentationItemListener());
		this.view.addMenuAboutItemListener(new menuAboutItemListener());

		// Button listeners
		this.view.addHomeButtonListener(new homeButtonListener());
		this.view.addNewButtonListener(new newButtonListener());
		this.view.addEditButtonListener(new editButtonListener());
		this.view.addDeleteButtonListener(new deleteButtonListener());
		this.view.addCopyButtonListener(new copyButtonListener());
		this.view.addSearchButtonListener(new searchButtonListener());

		// List listener
		this.view.addListSelectionListenerToList(new listListener());
	}
	
	// Operations
	private void newOperation() {
		System.out.println("new operation");
		FormDialog dl = new FormDialog();
		dl.setVisible(true);

		if (dl.canceledHit()) {
			return;
		}

		String title = dl.getTitleText();
		String url = dl.getUrlText();
		String desc = dl.getDescriptionText();
		String[] tags = dl.getTagsText().split(", ");
		Bookmark bk = new Bookmark(url, title, desc, tags);
		model.addNewBookmark(title, bk);

		DefaultListModel<ListMenuItem> listModel = view.getListModel();
		listModel.removeAllElements();
		listModel.clear();
		model.clearAVLQueue();
		LinkedList<Bookmark> bookmarkQueue = model.getQueueFromAVL();

		for (Bookmark b : bookmarkQueue) {
			listModel.addElement(new ListMenuItem(b.getTitle(), b.getDescription()));
		}

	} // End newOperation

	private void editOperation() {
		System.out.println("edit operation");
		try {
			ListMenuItem item = view.getItemList().getSelectedValue();
			Bookmark bookmark = model.getBookmarkByTitle(item.getItemName()); // Throws exception
			String oldTitle = bookmark.getTitle();
			FormDialog dl = new FormDialog();
			dl.setFormDialog(bookmark.getURL(), bookmark.getTitle(), bookmark.getDescription(), bookmark.getTags());
			dl.setVisible(true);

			String title = dl.getTitleText();
			String url = dl.getUrlText();
			String desc = dl.getDescriptionText();
			String[] tags = dl.getTagsText().split(", ");

			// If statement that'll take care of cancel
			if (dl.canceledHit()) {
				return;
			}

			if (!url.equals(bookmark.getURL())) {
				bookmark.setURL(url);
			}

			if (desc.equals(bookmark.getDescription())) {
				bookmark.setDescription(desc);
				item.setDescription(desc);
			}
				
			if (Arrays.equals(tags, bookmark.getTags().toArray())) {
				bookmark.setTagList(tags);
			}

			assert !oldTitle.equals(title) : "Titles match but aren't suppose to.";
			if (!oldTitle.equals(title)) {
				System.out.println("Here");
				DefaultListModel<ListMenuItem> listModel = view.getListModel();
				listModel.removeAllElements();
				listModel.clear();
				model.clearAVLQueue();
				LinkedList<Bookmark> bookmarkQueue = model.getQueueFromAVL();

				for (Bookmark b : bookmarkQueue) {
					listModel.addElement(new ListMenuItem(b.getTitle(), b.getDescription()));
				}
			}
			else {
				item.setDescription(desc);
				bookmark.setDescription(desc);
			}
		} // End try
		catch(BookmarkException bkException) {
			System.out.println(bkException.getMessage());
		}
	} // End editOperation

	private void copyOperation() {
		System.out.println("copy operation");
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
	} // End copyOperation

	private void deleteOperation() {
		System.out.println("delete operation");
		ListMenuItem item = view.getItemList().getSelectedValue();
		try {
			JList viewList = view.getItemList();
			viewList.clearSelection();
			viewList.revalidate();
			Bookmark bookmark = model.getBookmarkByTitle(item.getItemName()); // Throws exception
			assert bookmark.getTitle() == item.getItemName() : String.format("Doesn't match - %s : %s", bookmark.getTitle(), item.getItemName());
			model.deleteBookmark(bookmark);

			DefaultListModel<ListMenuItem> listModel = view.getListModel();
			listModel.removeAllElements();
			listModel.clear();
			model.clearAVLQueue();
			LinkedList<Bookmark> bookmarkQueue = model.getQueueFromAVL();

			for (Bookmark b : bookmarkQueue) {
				listModel.addElement(new ListMenuItem(b.getTitle(), b.getDescription()));
			}

		} // End try
		catch(BookmarkException bkException) {
				System.out.println(bkException.getMessage());
		}
		catch(Exception ex) {
				System.out.println(ex.getMessage());
		}
	} // End deleteOperation

	// Inner classes
	// Button listeners
	class newButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("new fired");
			FormDialog dl = new FormDialog();
			dl.setVisible(true);

			if (dl.canceledHit()) {
				return;
			}

			String title = dl.getTitleText();
			String url = dl.getUrlText();
			String desc = dl.getDescriptionText();
			String[] tags = dl.getTagsText().split(", ");
			Bookmark bk = new Bookmark(url, title, desc, tags);
			model.addNewBookmark(title, bk);

			DefaultListModel<ListMenuItem> listModel = view.getListModel();
			listModel.removeAllElements();
			listModel.clear();
			model.clearAVLQueue();
			LinkedList<Bookmark> bookmarkQueue = model.getQueueFromAVL();

			for (Bookmark b : bookmarkQueue) {
				listModel.addElement(new ListMenuItem(b.getTitle(), b.getDescription()));
			}
		}
	} // End of newListener

	class editButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("edit fired");
			try {
				ListMenuItem item = view.getItemList().getSelectedValue();
				Bookmark bookmark = model.getBookmarkByTitle(item.getItemName()); // Throws exception
				String oldTitle = bookmark.getTitle();
				FormDialog dl = new FormDialog();
				dl.setFormDialog(bookmark.getURL(), bookmark.getTitle(), bookmark.getDescription(), bookmark.getTags());
				dl.setVisible(true);

				String title = dl.getTitleText();
				String url = dl.getUrlText();
				String desc = dl.getDescriptionText();
				String[] tags = dl.getTagsText().split(", ");

				// If statement that'll take care of cancel
				if (dl.canceledHit()) {
					return;
				}

				if (!url.equals(bookmark.getURL())) {
					bookmark.setURL(url);
				}

				if (desc.equals(bookmark.getDescription())) {
					bookmark.setDescription(desc);
					item.setDescription(desc);
				}
				
				if (Arrays.equals(tags, bookmark.getTags().toArray())) {
					bookmark.setTagList(tags);
				}

				// assert !oldTitle.equals(title) : "Titles match but aren't suppose to."; // This needs to be fixed, or erease
				if (!oldTitle.equals(title)) {
					System.out.println("Here");
					DefaultListModel<ListMenuItem> listModel = view.getListModel();
					listModel.removeAllElements();
					listModel.clear();
					model.clearAVLQueue();
					LinkedList<Bookmark> bookmarkQueue = model.getQueueFromAVL();

					for (Bookmark b : bookmarkQueue) {
						listModel.addElement(new ListMenuItem(b.getTitle(), b.getDescription()));
					}
				}
				else {
					item.setDescription(desc);
					bookmark.setDescription(desc);
				}
			} catch(BookmarkException bkException) {
				System.out.println(bkException.getMessage());
			}
		} // End actionPerformed
	} // End of editListener

	class copyButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("copy fired");
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
			ListMenuItem item = view.getItemList().getSelectedValue();
			try {
				JList viewList = view.getItemList();
				viewList.clearSelection();
				viewList.revalidate();
				Bookmark bookmark = model.getBookmarkByTitle(item.getItemName()); // Throws exception
				assert bookmark.getTitle() == item.getItemName() : String.format("Doesn't match - %s : %s", bookmark.getTitle(), item.getItemName());
				model.deleteBookmark(bookmark);

				DefaultListModel<ListMenuItem> listModel = view.getListModel();
				listModel.removeAllElements();
				listModel.clear();
				model.clearAVLQueue();
				LinkedList<Bookmark> bookmarkQueue = model.getQueueFromAVL();

				for (Bookmark b : bookmarkQueue) {
					listModel.addElement(new ListMenuItem(b.getTitle(), b.getDescription()));
				}

			}
			catch(BookmarkException bkException) {
				System.out.println(bkException.getMessage());
			}
			catch(Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
	} // End of deleteListener

	class searchButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("search fired");
			String searchQuery = view.getSearchFieldText();

			try {
				Bookmark bookmark = model.getBookmarkByTitle(searchQuery); // Throws exception
				System.out.println(bookmark);
				DefaultListModel<ListMenuItem> listModel = view.getListModel();
				listModel.removeAllElements();
				listModel.clear();
				listModel.addElement(new ListMenuItem(bookmark.getTitle(), bookmark.getDescription()));
			}
			catch(BookmarkException bkException) {
				System.out.println(bkException.getMessage());
			}
		}
	} // End of searchListener

	class homeButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("home fired");
			DefaultListModel<ListMenuItem> listModel = view.getListModel();
			listModel.removeAllElements();
			listModel.clear();
			model.clearAVLQueue();
			LinkedList<Bookmark> bookmarkQueue = model.getQueueFromAVL();

			for (Bookmark b : bookmarkQueue) {
				listModel.addElement(new ListMenuItem(b.getTitle(), b.getDescription()));
			}
		}
	} // End of homeButtonListener

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

	// This listener will be skipped for as it'll be used for a specific feature of the application
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
			copyOperation();
		}
	}

	class menuNewItemListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("new item listener fired");
			newOperation();
		}
	}

	class menuEditItemListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("edit item listener fired");
			editOperation();
		}
	}

	class menuDeleteItemListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("delete item listener fired");
			deleteOperation();
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
			AboutDialog ad = new AboutDialog();
			ad.setVisible(true);
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
				// assert item != null : "Item is null";
				if (item == null) {
					return;
				}
				view.getDescrptionBox().setText(item.getDescription());
			}
		}
	} // End of listListener
} // End of Controller
