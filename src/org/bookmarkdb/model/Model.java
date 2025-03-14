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

package org.bookmarkdb.model;

import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.*;

import org.bookmarkdb.model.Bookmark;
import org.bookmarkdb.model.BookmarkException;
import org.bookmarkdb.model.NoTagException;
import org.bookmarkdb.model.AVL_Tree;

public class Model {
	private final HashMap<String, LinkedList<Bookmark>> tagsIndex;
	private final AVL_Tree avl_tree;

	private boolean dirtyFlag; // Flag used to indicate the contents have been modified. True for dirty, false for clean.
	private boolean fileExists; // Flag used for if the file being used is brand new or loaded in.

	private String filePath;

	public Model() {
		tagsIndex = new HashMap<String, LinkedList<Bookmark>>();
		avl_tree = new AVL_Tree();
		dirtyFlag = false;
		fileExists = false;
		filePath = "";
	}

	// Getters
	public LinkedList<Bookmark> getBookmarksByTag(final String tag) throws NoTagException {
		LinkedList<Bookmark> bucket = tagsIndex.get(tag);

		if (bucket == null) {
			throw new NoTagException("Could not find bookmark by tag");
		}

		return bucket;
	}

	public Bookmark getBookmarkByTitle(final String title) throws BookmarkException {
		AVL_Node node = avl_tree.searchBookmark(avl_tree.getRoot(), title);

		if (node == null) {
			throw new BookmarkException("Bookmark by title not found.");
		}

		return node.getBookmark();
	}

	public void getAllBookmarks() {
		avl_tree.inOrderTraversal(avl_tree.getRoot());
	}

	public String[] getTags() {
		Object[] keyObjects = tagsIndex.keySet().toArray();
		// String stringTags = Arrays.toString(keyObjects);
		Arrays.sort(keyObjects);

		String[] stringTags = new String[keyObjects.length];

		for (int i = 0; i < keyObjects.length; i++) {
			stringTags[i] = keyObjects[i].toString();
		}

		return stringTags;
	}

	public boolean getDirtyFlag() {
		return this.dirtyFlag;
	}

	public boolean getFileExistsFlag() {
		return this.fileExists;
	}

	public String getCurrentFilePath() {
		return this.filePath;
	}

	public void setCurrentFilePath(String cur) {
		this.filePath = cur;
	}

	// Setters
	public void setBookmarkTitle(final String oldTitle, final String newTitle) throws BookmarkException {
		Bookmark bookmark = getBookmarkByTitle(oldTitle); // This line throws a BookmarkException

		this.deleteBookmark(bookmark);
		bookmark.setTitle(newTitle);
		bookmark.setDateModified(LocalDate.now());
		addNewBookmark(newTitle, bookmark);
		dirtyFlag = true;
	}

	public void setBookmarkDescription(final String title, final String newDescription) throws BookmarkException {
		Bookmark bookmark = getBookmarkByTitle(title); // This line throws a BookmarkException
		bookmark.setDescription(newDescription);
		bookmark.setDateModified(LocalDate.now());
		dirtyFlag = true;
	}

	public void setBookmarkURL(final String title, final String newUrl) throws BookmarkException {
		Bookmark bookmark = getBookmarkByTitle(title); // This line throws a BookmarkException
		bookmark.setURL(newUrl);
		bookmark.setDateModified(LocalDate.now());
		dirtyFlag = true;
	}

	public void setBookmarkDateModified(final String title, final Date date) { // Not sure if this is needed due to how the other methods are made

	}

	// Operations
	// TODO: Rewrite this so that it uses two threads, one for AVL_Tree insertion and one for tagIndex insertrion
	public void addNewBookmark(final String key, final Bookmark bookmark) { // TODO: Handle the case when a node has the same key
		avl_tree.setRoot(avl_tree.insert(avl_tree.getRoot(), key, bookmark));
		ArrayList<String> bookmarkTags = bookmark.getTags();

		for (String tag : bookmarkTags) {
			if (tagsIndex.containsKey(tag) == false) {
				tagsIndex.put(tag, new LinkedList<Bookmark>());
			}

			LinkedList<Bookmark> bucket = tagsIndex.get(tag);
			bucket.add(bookmark);
		}

		dirtyFlag = true;
	}

	public void addNewTag(final String title, final String newTag) throws BookmarkException {
		Bookmark bookmark = getBookmarkByTitle(title); // This line throws a BookmarkException
		bookmark.addNewTag(newTag);
		bookmark.setDateModified(LocalDate.now());
		dirtyFlag = true;
	}

	public void copyToClipboard(final String title) throws BookmarkException {
		Bookmark bookmark = this.getBookmarkByTitle(title); // Throws BookmarkException
		StringSelection urlString = new StringSelection(bookmark.getURL());
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(urlString, urlString);
	}

	public void deleteBookmark(final Bookmark bookmark) {
		avl_tree.deleteNode(avl_tree.getRoot(), bookmark.getTitle());

		ArrayList<String> bookmarkTags = bookmark.getTags();

		for (String tag : bookmarkTags) { 
			LinkedList<Bookmark> bucket = tagsIndex.get(tag);
			bucket.remove(bookmark);
		}

		dirtyFlag = true;
	}

	public JSONArray openFile(final String filePath) throws IOException {
		this.filePath = new String(filePath);
		String jsonFileContents = Files.readString(Paths.get(filePath));
		JSONArray bookmarkJSONArray = new JSONArray(jsonFileContents);
		dirtyFlag = false;

		return bookmarkJSONArray;
	}

	public Bookmark processJson(final String jsonLine) {
		JSONObject jsonObject = new JSONObject(jsonLine);

		String url = jsonObject.getString("url");
		String title = jsonObject.getString("title");
		String description = jsonObject.getString("description");

		JSONArray jsonTags = jsonObject.getJSONArray("tags");
		Object[] objArr = jsonTags.toList().toArray();

		String[] strArr = Arrays.copyOf(objArr, objArr.length, String[].class);

		String dateCreated = jsonObject.getString("dateCreated");
		String dateModified = jsonObject.getString("dateModified");

		return new Bookmark(url, title, description, strArr, dateCreated, dateModified);
	}

	public Bookmark processJson(final JSONObject jsonObj) {
		String url = jsonObj.getString("url");
		String title = jsonObj.getString("title");
		String description = jsonObj.getString("description");

		JSONArray jsonTags = jsonObj.getJSONArray("tags");
		Object[] objArr = jsonTags.toList().toArray();

		String [] strArr = Arrays.copyOf(objArr, objArr.length, String[].class);

		String dateCreated = jsonObj.getString("dateCreated");
		String dateModified = jsonObj.getString("dateModified");	

		return new Bookmark(url, title, description, strArr, dateCreated, dateModified);
	}

	public void inputDataFile(final String filePath) throws IOException {
		this.filePath = new String(filePath);
		JSONArray jsonFileContents = openFile(filePath); // This line throws an IOException

		Iterator iter = jsonFileContents.iterator();
		while (iter.hasNext()) {
			Bookmark bkm = processJson(iter.next().toString());
			addNewBookmark(bkm.getTitle(), bkm);
		}

		this.fileExists = true;
	}

	public LinkedList<Bookmark> getQueueFromAVL() {
		avl_tree.inOrderTraversal(avl_tree.getRoot());
		return avl_tree.getQueue();
	}

	public void clearAVLQueue() {
		avl_tree.getQueue().clear();
	}

	public void saveContentsToFile(final File filePath) throws IOException {
		StringBuilder jsonContents = createJsonArray();
		// System.out.println(jsonContents.toString());

		FileWriter fileWriter = new FileWriter(filePath);
		fileWriter.write(jsonContents.toString());
		fileWriter.close();
		dirtyFlag = false;
		fileExists = true;
	}

	public StringBuilder createJsonArray() {
		clearAVLQueue();
		LinkedList<Bookmark> bookmarkList = getQueueFromAVL();

		StringBuilder jsonContents = new StringBuilder();
		JSONWriter jsonWriter = new JSONWriter(jsonContents);

		jsonWriter.array();
		for (Bookmark bkm : bookmarkList) {
			jsonWriter.object().key("url").value(bkm.getURL())
							   .key("title").value(bkm.getTitle())
							   .key("description").value(bkm.getDescription())
							   .key("tags").value(bkm.getTags())
							   .key("dateCreated").value(bkm.getDateCreated())
							   .key("dateModified").value(bkm.getDateModified())
					  .endObject();
		}
		jsonWriter.endArray();

		return jsonContents;
	}

	public void exportBookmarks(final JFileChooser fileExporter) throws IOException, JSONException {
		File exportFilePath = fileExporter.getSelectedFile();
		FileWriter fileWriter = new FileWriter(exportFilePath);
		this.clearAVLQueue();
		LinkedList<Bookmark> inOrderList = this.getQueueFromAVL();
		assert inOrderList != null : "inOrderList in exportBookmark is null";

		FileNameExtensionFilter fileFilter = (FileNameExtensionFilter)fileExporter.getFileFilter();

		if (fileFilter.getDescription().equals("XML")) {
			this.exportToXML(fileWriter);
		}
		else if (fileFilter.getDescription().equals("HTML")) {
			exportToHTML(inOrderList, fileWriter);
		}
		else if (fileFilter.getDescription().equals("Markdown")) {
			this.exportToMarkdown(inOrderList, fileWriter);
		}
		else if (fileFilter.getDescription().equals("Text")) {
			this.exportToText(inOrderList, fileWriter);
		}
		// else if (fileFilter.getDescription().equals("OPML")) {
		// }

		fileWriter.close();
	}

	public void exportToXML(FileWriter fileWriter) throws IOException, JSONException {
		assert this.filePath != null : "filePath null";
		if (!this.filePath.equals("") || this.filePath == null) {
			JSONArray jsonArray = openFile(this.filePath);
			fileWriter.write(XML.toString(jsonArray));
		}
		else {
			StringBuilder jsonContents = this.createJsonArray();
			JSONArray jsonArr = new JSONArray(jsonContents.toString());
			fileWriter.write(XML.toString(jsonArr));
		}
	}

	public void exportToHTML(LinkedList<Bookmark> inOrderList, FileWriter fileWriter) throws IOException {
		fileWriter.write("<!DOCTYPE NETSCAPE-Bookmark-file-1>\n");
		fileWriter.write("<TITLE>Bookmarks</TITLE>\n");
		fileWriter.write("<DL>\n");

		for (Bookmark b : inOrderList) {
			fileWriter.write(String.format("<DT><A HREF=\"%s\" ADD_DATE=\"%s\" LAST_MODIFIED=\"%s\">%s</A></DT>\n",
											b.getURL(), b.getDateCreated(), b.getDateModified(), b.getTitle()));
		}

		fileWriter.write("</DL>");
	}

	public void exportToMarkdown(LinkedList<Bookmark> inOrderList, FileWriter fileWriter) throws IOException {
		for (Bookmark bookmark : inOrderList) {
			fileWriter.write(String.format("# %s\n", bookmark.getTitle()));
			fileWriter.write(String.format("- **URL:** %s<br>\n- **Description:** %s<br>\n- **Tags:** %s<br>\n- **Date Created:** %s<br>\n- **Date Modified:** %s<br>\n\n", 
								bookmark.getURL(), bookmark.getDescription(), bookmark.getTags(),
								bookmark.getDateCreated(), bookmark.getDateModified()));
		}
	}

	public void exportToText(LinkedList<Bookmark> inOrderList, FileWriter fileWriter) throws IOException {
		for (Bookmark bookmark : inOrderList) {
			fileWriter.write(bookmark.toString() + "\n\n");
		}
	}

} // End of Model class
