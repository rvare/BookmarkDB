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
		System.out.println("Model constructor");
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

	public void getAllBookmarks() { // TODO Implement such that it returns something that JList can use
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

	// Setters
	public void setBookmarkTitle(final String oldTitle, final String newTitle) throws BookmarkException {
		Bookmark bookmark = getBookmarkByTitle(oldTitle); // This line throws a BookmarkException

		this.deleteBookmark(bookmark);
		bookmark.setTitle(newTitle);
		bookmark.setDateModified(LocalDate.now());
		addNewBookmark(newTitle, bookmark);
		dirtyFlag = true;
	}

	// TODO: Determine if this is still needed
	public void setBookmarkTags(final String title, final String[] tags) { // Not sure if this is needed

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

	// TODO: Rewrite to use the Bookmark object in the method argument because it'll be easier to use remove() with linked list
	public void deleteBookmark(final Bookmark bookmark) {
		avl_tree.deleteNode(avl_tree.getRoot(), bookmark.getTitle());

		ArrayList<String> bookmarkTags = bookmark.getTags();

		// TODO: Fix this so it doesn't remove a tag when there's a list there. Only remove when null.
		for (String tag : bookmarkTags) { 
			LinkedList<Bookmark> bucket = tagsIndex.get(tag);
			bucket.remove(bookmark);
		}

		dirtyFlag = true;
	}

	public JSONArray openFile(final String filePath) throws IOException {
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
		JSONArray jsonFileContents = openFile(filePath); // This line throws an IOException

		Iterator iter = jsonFileContents.iterator();
		while (iter.hasNext()) {
			Bookmark bkm = processJson(iter.next().toString());
			addNewBookmark(bkm.getTitle(), bkm);
		}

		this.fileExists = true;
		this.filePath = filePath;
	}

	public LinkedList<Bookmark> getQueueFromAVL() {
		avl_tree.inOrderTraversal(avl_tree.getRoot());
		return avl_tree.getQueue();
	}

	public void clearAVLQueue() {
		avl_tree.getQueue().clear();
	}

	public void saveContentsToFile(final File filePath) throws IOException {
		System.out.println("saveConentsToFile");
		StringBuilder jsonContents = createJsonArray();
		System.out.println(jsonContents.toString());

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
} // End of Model class
