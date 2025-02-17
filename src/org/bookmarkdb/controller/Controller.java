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
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.bookmarkdb.model.*;
import org.bookmarkdb.view.*;

// TODO: Refactor to add comments so that everyone knows where an exception maybe thrown and indicate what is thrown

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
	private void newOperation() { // TODO CLEAN: Remove GUI and logic operations to GUI class and Model class respectively
		System.out.println("new operation");
		FormDialog formDialog = new FormDialog(); // TODO CLEAN: DONE Rename dl to a more descriptive name
		formDialog.setVisible(true);

		if (formDialog.canceledHit()) {
			return;
		}

		// This should be fine as it's preparing the data to be sent to the Model object
		String title = formDialog.getTitleText();
		String url = formDialog.getUrlText();
		String desc = formDialog.getDescriptionText();
		String[] tags = formDialog.getTagsText().split(", ");
		Bookmark newBookmark = new Bookmark(url, title, desc, tags);
		model.addNewBookmark(title, newBookmark);

		// TODO REFACTOR: Move to the MainGui class since it's related to it
		DefaultListModel<ListMenuItem> listModel = view.getListModel();
		listModel.removeAllElements();
		listModel.clear();
		model.clearAVLQueue();
		LinkedList<Bookmark> bookmarkQueue = model.getQueueFromAVL();

		for (Bookmark bookmark : bookmarkQueue) {
			listModel.addElement(new ListMenuItem(bookmark.getTitle(), bookmark.getDescription()));
		}
	} // End newOperation

	private void editOperation() {
		System.out.println("edit operation");
		try {
			ListMenuItem item = view.getItemList().getSelectedValue();
			Bookmark bookmark = model.getBookmarkByTitle(item.getItemName()); // Throws exception
			String oldTitle = bookmark.getTitle();
			// TODO REFACTOR: Move to the MainGui class
			FormDialog formDialog = new FormDialog();
			formDialog.setFormDialog(bookmark.getURL(), bookmark.getTitle(), bookmark.getDescription(), bookmark.getTags());
			formDialog.setVisible(true);

			String title = formDialog.getTitleText();
			String url = formDialog.getUrlText();
			String desc = formDialog.getDescriptionText();
			String[] tags = formDialog.getTagsText().split(", ");

			// If statement that'll take care of cancel
			if (formDialog.canceledHit()) {
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
				// TODO REFACTOR: Move to MainGui class
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
		} // End try-catch
	} // End editOperation

	private void copyOperation() {
		System.out.println("copy operation");
		ListMenuItem item = view.getItemList().getSelectedValue();
		try {
			// TODO REFACTOR: Move to the Model class
			Bookmark bookmark = model.getBookmarkByTitle(item.getItemName()); // Throws BookmarkException
			StringSelection urlString = new StringSelection(bookmark.getURL());
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(urlString, urlString);
		}
		catch(BookmarkException bkException) {
			System.out.println(bkException.getMessage());
		} // End try-catch
	} // End copyOperation

	private void deleteOperation() {
		System.out.println("delete operation");
		ListMenuItem item = view.getItemList().getSelectedValue();
		try {
			JList viewList = view.getItemList();
			viewList.clearSelection();
			viewList.revalidate();
			Bookmark bookmark = model.getBookmarkByTitle(item.getItemName()); // Throws BookmarkException
			assert bookmark.getTitle() == item.getItemName() : String.format("Doesn't match - %s : %s", bookmark.getTitle(), item.getItemName());
			model.deleteBookmark(bookmark);

			// TODO REFACTOR: Move to MainGui class
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
		} // End try-catch
	} // End deleteOperation

	// Inner classes
	// Button listeners
	class newButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("new fired");
			newOperation();
		}
	} // End of newListener

	class editButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("edit fired");
			editOperation();
		}
	} // End of editListener

	class copyButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("copy fired");
			copyOperation();
		}
	} // End of copyListener

	class deleteButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("delete fired");
			deleteOperation();
		}
	} // End of deleteListener

	class searchButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("search fired");
			String searchQuery = view.getSearchFieldText();

			try {
				// TODO REFACTOR: Move some of this to MainGui
				Bookmark bookmark = model.getBookmarkByTitle(searchQuery); // Throws BookmarkException
				System.out.println(bookmark);
				DefaultListModel<ListMenuItem> listModel = view.getListModel();
				listModel.removeAllElements();
				listModel.clear();
				listModel.addElement(new ListMenuItem(bookmark.getTitle(), bookmark.getDescription()));
			} // End try
			catch(BookmarkException bkException) {
				System.out.println(bkException.getMessage());
			} // End try-catch
		}
	} // End of searchListener

	class homeButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("home fired");
			// TODO REFACTOR: Move to MainGui as it's pertains to it
			DefaultListModel<ListMenuItem> listModel = view.getListModel();
			listModel.removeAllElements();
			listModel.clear();
			model.clearAVLQueue();
			LinkedList<Bookmark> bookmarkQueue = model.getQueueFromAVL();

			for (Bookmark b : bookmarkQueue) {
				listModel.addElement(new ListMenuItem(b.getTitle(), b.getDescription()));
			}
		} // End of actionPerformed
	} // End of homeButtonListener

	// Menu bar listeners
	// TODO Implement their functionality
	class menuNewListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("new listener fired");
		}
	}

	class menuOpenListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("open listener fired");
			// TODO REFACTOR: Move to MainGui and then return reference to get data from it
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.showOpenDialog(view.getMainFrame());

			// TODO: Change to handle errors properly
			System.out.println(fileChooser.getSelectedFile().getAbsolutePath());
			try {
				model.inputDataFile(fileChooser.getSelectedFile().getAbsolutePath()); // Throws IOException

				// TODO CLEAN: Some of this pretains to the MainGui class and should be moved there
				LinkedList<Bookmark> bookmarkQueue = model.getQueueFromAVL();
				assert bookmarkQueue != null : "bookmarkQueue is null";

				JList<ListMenuItem> listMenu = view.getItemList();
				assert listMenu != null : "listMenu is null";

				DefaultListModel<ListMenuItem> listModel = view.getListModel();
				assert listModel != null : "listModel is null";

				for (Bookmark bkm : bookmarkQueue) {
					listModel.addElement(new ListMenuItem(bkm.getTitle(), bkm.getDescription()));
				}
			} // End try
			catch(IOException ioEx) {
				System.out.println(ioEx.getMessage());
			}
			catch(Exception ex) {
				System.out.println(ex.getMessage());
				System.out.println(ex.getStackTrace());
			} // End try-catch
		} // End of actionPerformed
	} // End of menuOpenListener

	class menuSaveListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("save listener fired");
			// TODO REFACTOR: Move to MainGui
			JFileChooser fileSaver = new JFileChooser();
			fileSaver.showSaveDialog(view.getMainFrame());

			File filePath = fileSaver.getSelectedFile();
			System.out.println(filePath);

			StringBuilder jsonContents = model.createJsonArray();

			try {
				model.saveContentsToFile(filePath); // Throws IOException
			}
			catch (IOException ioEx) {
				System.out.println(ioEx.getMessage());
			}
			catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		} // End of actionPerformed
	} // End of menuSaveListener

	class menuSaveAsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("save as listener fired");
			// TODO REFACTOR: Move to MainGui
			JFileChooser fileSaver = new JFileChooser();
			fileSaver.showSaveDialog(view.getMainFrame());

			File filePath = fileSaver.getSelectedFile();
			System.out.println(filePath);

			StringBuilder jsonContents = model.createJsonArray();

			try {
				model.saveContentsToFile(filePath); // Throws IOException
			}
			catch (IOException ioEx) {
				System.out.println(ioEx.getMessage());
			}
			catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		} // End of actionPerformed
	} // End menuSaveAsListener

	// This listener will be skipped for as it'll be used for a specific feature of the application
	// TODO REFACTOR: Move to the view class (MainGui)
	class menuExportListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			// TODO CLEAN: Refactor this so it's in the MainGui class and makes sense
			System.out.println("export as listener fired");
			JFileChooser fileExporter = new JFileChooser();
			fileExporter.setAcceptAllFileFilterUsed(false);

			FileNameExtensionFilter htmlFilter = new FileNameExtensionFilter("HTML file", "html");
			fileExporter.addChoosableFileFilter(htmlFilter);

			FileNameExtensionFilter mdFilter = new FileNameExtensionFilter("Markdown", "md");
			fileExporter.addChoosableFileFilter(mdFilter);
			
			FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Text", "txt");
			fileExporter.addChoosableFileFilter(txtFilter);

			FileNameExtensionFilter xmlFilter = new FileNameExtensionFilter("XML", "xml");
			fileExporter.addChoosableFileFilter(xmlFilter);

			fileExporter.showSaveDialog(view.getMainFrame());
			// saveFile using fileSaver
		} // End of actionPerformed
	} // End of menuExportListener

	class menuCopyListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("copy listener fired");
			copyOperation();
		}
	}

	class menuNewItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("new item listener fired");
			newOperation();
		}
	}

	class menuEditItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("edit item listener fired");
			editOperation();
		}
	}

	class menuDeleteItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("delete item listener fired");
			deleteOperation();
		}
	}

	class menuDocumentationItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("documentation item listener fired");
		}
	}

	class menuAboutItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("about item listener fired");
			// TODO CLEAN: Refactor to have a better name
			// TODO REFACTOR: Should be moved to the MainGui class
			AboutDialog aboutDialog = new AboutDialog();
			aboutDialog.setVisible(true);
		}
	}

	// JList listener
	// Note: The following code was made using this reference
	// https://stackoverflow.com/questions/13800775/find-selected-item-of-a-jlist-and-display-it-in-real-time
	class listListener implements ListSelectionListener {
		// TODO CLEAN: Refactor for better reading
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
		} // End of valueChanged
	} // End of listListener
} // End of Controller
