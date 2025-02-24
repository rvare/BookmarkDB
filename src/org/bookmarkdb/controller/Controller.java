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
import java.time.LocalDate;

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
		this.view.addTagsButtonListener(new tagsButtonListener());

		// List listener
		this.view.addListSelectionListenerToList(new listListener());
	}

	// Getters
	public void refreshViewListModel() {
		this.model.clearAVLQueue();
		LinkedList<Bookmark> bookmarkQueue = this.model.getQueueFromAVL();
		LinkedList<ListMenuItem> inOrderList = new LinkedList<ListMenuItem>();

		for (Bookmark bookmark : bookmarkQueue) {
			inOrderList.add(new ListMenuItem(bookmark.getTitle(), bookmark.getDescription()));
		}

		this.view.refreshListModel(inOrderList);
	}

	// Operations
	private void newOperation() {
		System.out.println("new operation");

		FormDialog formDialog = this.view.createFormDialog();

		if (formDialog.canceledHit()) {
			return;
		}

		// This should be fine as it's preparing the data to be sent to the Model object
		String title = formDialog.getTitleText();
		String url = formDialog.getUrlText();
		String desc = formDialog.getDescriptionText();
		String[] tags = formDialog.getTagsText().split(", ");
		// LocalDateTime dateCreated = LocalDateTime.now();
		// LocalDateTime dateModified = LocalDateTime.now();
		String dateCreated = LocalDate.now().toString();
		String dateModified = LocalDate.now().toString();
		Bookmark newBookmark = new Bookmark(url, title, desc, tags, dateCreated, dateModified);
		model.addNewBookmark(title, newBookmark);

		this.refreshViewListModel();
		this.view.determineAndChangeDirtyIndication(this.model.getDirtyFlag());
	}

	private void editOperation() {
		System.out.println("edit operation");
		try {
			ListMenuItem item = view.getItemList().getSelectedValue();
			Bookmark bookmark = model.getBookmarkByTitle(item.getItemName()); // Throws exception
			String oldTitle = bookmark.getTitle();
			FormDialog formDialog = this.view.createFormDialog(bookmark.getURL(),
															   bookmark.getTitle(),
															   bookmark.getDescription(),
															   bookmark.getTags());

			String newTitle = formDialog.getTitleText();
			String newUrl = formDialog.getUrlText();
			String newDesc = formDialog.getDescriptionText();
			String[] newTags = formDialog.getTagsText().split(", ");

			// If statement that'll take care of cancel
			if (formDialog.canceledHit()) {
				return;
			}

			if (!newUrl.equals(bookmark.getURL())) {
				bookmark.setURL(newUrl);
			}

			if (newDesc.equals(bookmark.getDescription())) {
				bookmark.setDescription(newDesc);
				item.setDescription(newDesc);
			}
				
			if (Arrays.equals(newTags, bookmark.getTags().toArray())) {
				bookmark.setTagList(newTags);
			}

			// assert !oldTitle.equals(newTitle) : "Titles match but aren't suppose to.";
			if (!oldTitle.equals(newTitle)) {
				// TODO REFACTOR: Move to the Model class
				this.model.deleteBookmark(bookmark);
				bookmark.setTitle(newTitle);
				this.model.addNewBookmark(newTitle, bookmark);

				this.refreshViewListModel();
			}
			else {
				item.setDescription(newDesc);
				bookmark.setDescription(newDesc);
			}

			this.view.determineAndChangeDirtyIndication(this.model.getDirtyFlag());
		} // End try
		catch(BookmarkException bkException) {
			System.out.println(bkException.getMessage());
		} // End try-catch
	} // End editOperation

	private void copyOperation() {
		System.out.println("copy operation");
		ListMenuItem item = view.getItemList().getSelectedValue();
		try {
			this.model.copyToClipboard(item.getItemName());
		}
		catch(BookmarkException bkException) {
			System.out.println(bkException.getMessage());
		}
	}

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

			this.refreshViewListModel();
			this.view.determineAndChangeDirtyIndication(this.model.getDirtyFlag());
		} // End try
		catch(BookmarkException bkException) {
				System.out.println(bkException.getMessage());
		}
		catch(Exception ex) {
				System.out.println(ex.getMessage());
		} // End try-catch
	}

	// Inner classes
	// Button listeners
	class newButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("new fired");
			newOperation();
		}
	}

	class editButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("edit fired");
			editOperation();
		}
	}

	class copyButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("copy fired");
			copyOperation();
		}
	}

	class deleteButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("delete fired");
			deleteOperation();
		}
	}

	class searchButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("search fired");
			String searchQuery = view.getSearchFieldText();

			try {
				Bookmark bookmark = model.getBookmarkByTitle(searchQuery); // Throws BookmarkException
				view.showSearchResult(new ListMenuItem(bookmark.getTitle(), bookmark.getDescription()));
			}
			catch(BookmarkException bkException) {
				System.out.println(bkException.getMessage());
			} // End try-catch
		}
	}

	class homeButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("home fired");
			refreshViewListModel();
		}
	}

	class tagsButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("tags fired");
			try {
				String[] tagsList = model.getTags();
				TagsDialog tagsDialog = view.createTagsDialog(tagsList);
				String selectedTag = tagsDialog.getSelectedTag();

				assert selectedTag != null : "selectedTag in tagsButtonListener is null";
				if (tagsDialog.canceledHit()) {
					return;
				}

				LinkedList<Bookmark> bucket = model.getBookmarksByTag(selectedTag);
				LinkedList<ListMenuItem> inOrderList = new LinkedList<ListMenuItem>();

				for (Bookmark bookmark : bucket) {
					inOrderList.add(new ListMenuItem(bookmark.getTitle(), bookmark.getDescription()));
				}

				view.refreshListModel(inOrderList);
			}
			catch(NoTagException ntException) {
				System.out.println(ntException.getMessage());
			}
		}
	}

	// Menu bar listeners
	// TODO Implement their functionality
	class menuNewListener implements ActionListener { // New JSON file is what this is
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("new listener fired");
		}
	}

	class menuOpenListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("open listener fired");
			JFileChooser fileChooser = view.createFileChooserWindow("open", true);

			if (fileChooser.getSelectedFile() == null) {
				return;
			}

			System.out.println(fileChooser.getSelectedFile().getAbsolutePath());
			try {
				model.inputDataFile(fileChooser.getSelectedFile().getAbsolutePath()); // Throws IOException
				refreshViewListModel();
			}
			catch(IOException ioEx) {
				System.out.println(ioEx.getMessage());
			}
			catch(Exception ex) {
				System.out.println(ex.getMessage());
				System.out.println(ex.getStackTrace());
			}
		} // End of actionPerformed
	} // End of menuOpenListener

	class menuSaveListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("save listener fired");
			File filePath;
			if (!model.getFileExistsFlag()) {
				JFileChooser fileSaver = view.createFileChooserWindow("save", model.getFileExistsFlag());
				filePath = fileSaver.getSelectedFile();
			}
			else {
				filePath = new File(model.getCurrentFilePath());
			}

			System.out.println(filePath);

			try {
				model.saveContentsToFile(filePath); // Throws IOException
			}
			catch (IOException ioEx) {
				System.out.println(ioEx.getMessage());
			}
			catch (Exception ex) {
				System.out.println(ex.getMessage());
			}

			view.determineAndChangeDirtyIndication(model.getDirtyFlag());
		} // End of actionPerformed
	} // End of menuSaveListener

	class menuSaveAsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("save as listener fired");
			JFileChooser fileSaver = view.createFileChooserWindow("save as", true);

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

			view.determineAndChangeDirtyIndication(model.getDirtyFlag());
		} // End of actionPerformed
	} // End menuSaveAsListener

	// This listener will be skipped for as it'll be used for a specific feature of the application
	// TODO REFACTOR: Move to the view class (MainGui)
	class menuExportListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("export as listener fired");
			JFileChooser fileExporter = view.createExportChooserWindow();
		}
	}

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
			view.displayAboutDialogWindow();
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
				if (item == null) {
					return;
				}
				view.getDescrptionBox().setText(item.getDescription());
			}
		}
	}
} // End of Controller
