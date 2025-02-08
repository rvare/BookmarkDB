package org.bookmarkdb.model;

import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import org.json.*;

import org.bookmarkdb.model.Bookmark;
import org.bookmarkdb.model.BookmarkException;
import org.bookmarkdb.model.NoTagException;
import org.bookmarkdb.model.AVL_Tree;

public class Model {
	private final HashMap<String, LinkedList<Bookmark>> tagsIndex;
	private final AVL_Tree avl_tree;

	public Model() {
		System.out.println("Model constructor");
		tagsIndex = new HashMap<String, LinkedList<Bookmark>>();
		avl_tree = new AVL_Tree();
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

	public String getTags() {
		Object[] keyObjects = tagsIndex.keySet().toArray();
		String stringTags = Arrays.toString(keyObjects);

		return stringTags;
	}

	// Setters
	public void setBookmarkTitle(final String oldTitle, final String newTitle) throws BookmarkException {
		Bookmark bookmark = getBookmarkByTitle(oldTitle); // This line throws a BookmarkException

		deleteBookmark(bookmark);
		bookmark.setTitle(newTitle);
		bookmark.setDateModified(LocalDateTime.now());
		addNewBookmark(newTitle, bookmark);
	}

	public void setBookmarkTags(final String title, final String[] tags) { // Not sure if this is needed

	}

	public void setBookmarkDescription(final String title, final String newDescription) throws BookmarkException {
		Bookmark bookmark = getBookmarkByTitle(title); // This line throws a BookmarkException
		bookmark.setDescription(newDescription);
		bookmark.setDateModified(LocalDateTime.now());
	}

	public void setBookmarkURL(final String title, final String newUrl) throws BookmarkException {
		Bookmark bookmark = getBookmarkByTitle(title); // This line throws a BookmarkException
		bookmark.setURL(newUrl);
		bookmark.setDateModified(LocalDateTime.now());
	}

	public void setBookmarkDateModified(final String title, final Date date) { // Not sure if this is needed due to how the other methods are made

	}

	// Operations
	// TODO: Rewrite this so that it uses two threads, one for AVL_Tree insertion and one for tagIndex insertrion
	public void addNewBookmark(final String key, final Bookmark bookmark) { // TODO: Handle the case when a node has the same key
		avl_tree.setRoot(avl_tree.insert(avl_tree.getRoot(), key, bookmark));
		ArrayList<String> tags = bookmark.getTags();

		for (String tag : tags) {
			if (tagsIndex.containsKey(tag) == false) {
				tagsIndex.put(tag, new LinkedList<Bookmark>());
			}

			LinkedList<Bookmark> bucket = tagsIndex.get(tag);
			bucket.add(bookmark);
		}
	}

	public void addNewTag(final String title, final String newTag) throws BookmarkException {
		Bookmark bookmark = getBookmarkByTitle(title); // This line throws a BookmarkException
		bookmark.addNewTag(newTag);
		bookmark.setDateModified(LocalDateTime.now());
	}

	// TODO: Rewrite to use the Bookmark object in the method argument because it'll be easier to use remove() with linked list
	public void deleteBookmark(final Bookmark bookmark) {
		// Bookmark bookmark = getBookmarkByTitle(title); // This line throws a BookmarkException
		avl_tree.deleteNode(avl_tree.getRoot(), bookmark.getTitle());

		ArrayList<String> bookmarkTags = bookmark.getTags();

		// TODO: Fix this so it doesn't remove a tag when there's a list there. Only remove when null.
		for (String i : bookmarkTags) { 
			LinkedList<Bookmark> bucket = tagsIndex.get(i);
			bucket.remove(bookmark);
		}
	}

	public JSONArray openFile(final String filePath) throws IOException {
		String jsonFileContents = Files.readString(Paths.get(filePath));
		JSONArray bookmarkJSONArray = new JSONArray(jsonFileContents);
		// System.out.println(bookmarkJSONArray);

		return bookmarkJSONArray;
	} // End of openFile

	public Bookmark processJson(final String jsonLine) {
		JSONObject jo = new JSONObject(jsonLine);

		String url = jo.getString("url");
		String title = jo.getString("title");
		String description = jo.getString("description");

		JSONArray jsonTags = jo.getJSONArray("tags");
		Object[] objArr = jsonTags.toList().toArray();

		String[] strArr = Arrays.copyOf(objArr, objArr.length, String[].class);

		return new Bookmark(url, title, description, strArr);
	} // End of processJson

	public Bookmark processJson(final JSONObject jsonObj) {
		String url = jsonObj.getString("url");
		String title = jsonObj.getString("title");
		String description = jsonObj.getString("description");

		JSONArray jsonTags = jsonObj.getJSONArray("tags");
		Object[] objArr = jsonTags.toList().toArray();

		String [] strArr = Arrays.copyOf(objArr, objArr.length, String[].class);

		return new Bookmark(url, title, description, strArr);
	} // End of processJson

	public void inputDataFile(final String filePath) throws IOException {
		JSONArray jsonFileContents = openFile(filePath); // This line throws an IOException

		Bookmark bk;
		Iterator iter = jsonFileContents.iterator();
		while (iter.hasNext()) {
			bk = processJson(iter.next().toString());
			addNewBookmark(bk.getTitle(), bk);
		}
	}

	public LinkedList<Bookmark> getQueueFromAVL() {
		avl_tree.inOrderTraversal(avl_tree.getRoot());
		return avl_tree.getQueue();
	}

	public void clearAVLQueue() {
		avl_tree.getQueue().clear();
	}
} // End of Model class
