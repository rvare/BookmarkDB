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

import org.json.*;

import org.bookmarkdb.model.*;
import org.bookmarkdb.view.*;

public class Controller {
	private final MainGui view;
	private final Model model;

	public Controller(final Model model, final MainGui view) {
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
			view.createErrorWindow("Can not edit bookmark. Bookmark may have already change or is missing in file. ");
			// System.out.println(bkException.getMessage());
		} // End try-catch
	} // End editOperation

	private void copyOperation() {
		ListMenuItem item = view.getItemList().getSelectedValue();
		try {
			this.model.copyToClipboard(item.getItemName());
		}
		catch(BookmarkException bkException) {
			view.createErrorWindow("Can not copy bookmark. Bookmark may have changed or is missing in file.");
			// System.out.println(bkException.getMessage());
		}
	}

	private void deleteOperation() {
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
			view.createErrorWindow("Can not delete bookmark. Bookmark may have changed, is missing from file, or is has already been deleted.");
			// System.out.println(bkException.getMessage());
		}
		catch(Exception ex) {
			view.createErrorWindow("An unknown error has occured. Please check the integrity of the file.");
			// System.out.println(ex.getMessage());
		} // End try-catch
	}

	// Inner classes
	// Button listeners
	class newButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			newOperation();
		}
	}

	class editButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			editOperation();
		}
	}

	class copyButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			copyOperation();
		}
	}

	class deleteButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			deleteOperation();
		}
	}

	class searchButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			String searchQuery = view.getSearchFieldText();

			try {
				Bookmark bookmark = model.getBookmarkByTitle(searchQuery); // Throws BookmarkException
				view.showSearchResult(new ListMenuItem(bookmark.getTitle(), bookmark.getDescription()));
			}
			catch(BookmarkException bkException) {
				view.createErrorWindow(String.format("Bookmark with title \"%s\" not found", searchQuery));
				// System.out.println(bkException.getMessage());
			} // End try-catch
		}
	}

	class homeButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			refreshViewListModel();
		}
	}

	class tagsButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
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
				view.createErrorWindow("The tag selected does not exists. Please close and reopen the program to reset its state.");
				// System.out.println(ntException.getMessage());
			}
		}
	}

	// Menu bar listeners
	class menuNewListener implements ActionListener { // New JSON file is what this is
		@Override
		public void actionPerformed(ActionEvent event) {
			// System.out.println("new listener fired");
		}
	}

	class menuOpenListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			JFileChooser fileChooser = view.createFileChooserWindow("open", true);

			if (fileChooser.getSelectedFile() == null) {
				return;
			}

			try {
				model.setCurrentFilePath(fileChooser.getSelectedFile().getAbsolutePath());
				model.inputDataFile(fileChooser.getSelectedFile().getAbsolutePath()); // Throws IOException
				refreshViewListModel();
			}
			catch(IOException ioEx) {
				// System.out.println(ioEx.getMessage());
				view.createErrorWindow(String.format("Could not open file \"%s\"", fileChooser.getSelectedFile().getAbsolutePath()));
			}
			catch(Exception ex) {
				view.createErrorWindow("An unknown error occurred while opeing the file. Please check the integrity of the file.");
				// System.out.println(ex.getMessage());
				// System.out.println(ex.getStackTrace());
			}
		} // End of actionPerformed
	} // End of menuOpenListener

	class menuSaveListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			File filePath;
			if (!model.getFileExistsFlag()) {
				JFileChooser fileSaver = view.createFileChooserWindow("save", model.getFileExistsFlag());
				filePath = fileSaver.getSelectedFile();
			}
			else {
				filePath = new File(model.getCurrentFilePath());
			}

			try {
				model.saveContentsToFile(filePath); // Throws IOException
			}
			catch (IOException ioEx) {
				view.createErrorWindow(String.format("Could not save file \"%s\"", filePath));
				// System.out.println(ioEx.getMessage());
			}
			catch (Exception ex) {
				view.createErrorWindow("An unknown error has occured while saving. Please check the integrity of the file.");
				// System.out.println(ex.getMessage());
			}

			view.determineAndChangeDirtyIndication(model.getDirtyFlag());
		} // End of actionPerformed
	} // End of menuSaveListener

	class menuSaveAsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			JFileChooser fileSaver = view.createFileChooserWindow("save as", true);

			File filePath = fileSaver.getSelectedFile();

			StringBuilder jsonContents = model.createJsonArray();

			try {
				model.saveContentsToFile(filePath); // Throws IOException
			}
			catch (IOException ioEx) {
				view.createErrorWindow(String.format("Could not save file as \"%s\"", filePath));
				// System.out.println(ioEx.getMessage());
			}
			catch (Exception ex) {
				view.createErrorWindow("An unknown error has occured while saving. Please check the integrity of the file.");
				// System.out.println(ex.getMessage());
			}

			view.determineAndChangeDirtyIndication(model.getDirtyFlag());
		} // End of actionPerformed
	} // End menuSaveAsListener

	// This listener will be skipped for as it'll be used for a specific feature of the application
	class menuExportListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			JFileChooser fileExporter = view.createExportChooserWindow();
			File filePath = fileExporter.getSelectedFile();
			if (filePath == null) {
				return;
			}

			try {
				model.exportBookmarks(fileExporter);
			}
			catch(IOException ioEx) {
				view.createErrorWindow(String.format("Could not export file as \"%s\"", filePath));
				// System.out.println(ioEx.getMessage());
			}
			catch(JSONException jsonEx) {
				view.createErrorWindow("Could not use the original JSON file. Please check if the file still exists and the integrity of the file.");
				// System.out.println(jsonEx.getMessage());
			}
			catch(Exception ex) {
				view.createErrorWindow("An unknown error has occured while exporting. Please check the integrity of the file.");
				// System.out.println(ex.getMessage());
			}
		}
	}

	class menuCopyListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			copyOperation();
		}
	}

	class menuNewItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			newOperation();
		}
	}

	class menuEditItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			editOperation();
		}
	}

	class menuDeleteItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			deleteOperation();
		}
	}

	class menuDocumentationItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			view.displayDocumentationDialogWindow();
		}
	}

	class menuAboutItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			view.displayAboutDialogWindow();
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
				if (item == null) {
					return;
				}
				view.getDescrptionBox().setText(item.getDescription());
			}
		}
	}
} // End of Controller
