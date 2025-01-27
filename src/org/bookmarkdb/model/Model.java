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
import org.bookmarkdb.model.AVL_Tree;

public class Model {
	// TODO Change to be `final` so we can prevent accidental mutations
	private final HashMap<String, Bookmark> tagsIndex; // Change to use a list of some kind
	private final AVL_Tree avl_tree;

	public Model() {
		System.out.println("Model constructor");
		tagsIndex = new HashMap<String, Bookmark>();
		avl_tree = new AVL_Tree();
	}

	// Getters
	public Bookmark getBookmarksByTag(final String tag) throws BookmarkException { // TODO Modify to use a list in the hashmap
		Bookmark bookmark = tagsIndex.get(tag);

		if (bookmark == null) {
			throw new BookmarkException("Could not find bookmark by tag");
		}

		return bookmark;
	}

	public Bookmark getBookmarkByTitle(final String title) throws BookmarkException {
		AVL_Node node = avl_tree.searchBookmark(avl_tree.getRoot(), title);

		if (node == null) {
			throw new BookmarkException("Bookmark by title not found.");
		}

		return node.getBookmark();
	}

	public void getAllBookmarks() { // TODO Implement

	}

	public String getTags() {
		Object[] keyObjects = tagsIndex.keySet().toArray();
		String stringTags = Arrays.toString(keyObjects);

		return stringTags;
	}

	// Setters
	// TODO Implement all setters
	// TODO Call setDateModified to all setters
	// TODO Modify to handle the exception of bookmark DNE
	public void setBookmarkTitle(final String oldTitle, final String newTitle) throws BookmarkException {
		Bookmark bookmark = getBookmarkByTitle(oldTitle); // This statement throws an exception

		deleteBookmark(bookmark.getTitle());
		bookmark.setTitle(newTitle);
		bookmark.setDateModified(LocalDateTime.now());
		addNewBookmark(newTitle, bookmark);
	}

	public void setBookmarkTags(final String title, final String[] tags) {

	}

	public void setBookmarkDescription(final String title, final String newDescription) throws BookmarkException {
		Bookmark bookmark = getBookmarkByTitle(title); // Throws exception
		bookmark.setDescription(newDescription);
		bookmark.setDateModified(LocalDateTime.now());
	}

	public void setBookmarkURL(final String title, final String newUrl) throws BookmarkException {
		Bookmark bookmark = getBookmarkByTitle(title);
		bookmark.setURL(newUrl);
		bookmark.setDateModified(LocalDateTime.now());
	}

	public void setBookmarkDateModified(final String title, final Date date) {

	}

	// Operations
	// TODO: Rewrite this so that it uses two threads, one for AVL_Tree insertion and one for tagIndex insertrion
	public void addNewBookmark(final String key, final Bookmark bookmark) { // TODO: Handle the case when a node has the same key
		avl_tree.setRoot(avl_tree.insert(avl_tree.getRoot(), key, bookmark));
		ArrayList<String> tags = bookmark.getTags();

		for (String i : tags) {
			tagsIndex.put(i, bookmark);
		}
	}

	public void addNewTag(final String title, final String newTag) throws BookmarkException {
		Bookmark bookmark = getBookmarkByTitle(title);
		bookmark.addNewTag(newTag);
		bookmark.setDateModified(LocalDateTime.now());
	}

	public void deleteBookmark(final String title) throws BookmarkException {
		Bookmark bookmark = getBookmarkByTitle(title);
		avl_tree.deleteNode(avl_tree.getRoot(), title);

		ArrayList<String> bookmarkTags = bookmark.getTags();

		for (String i : bookmarkTags) {
			tagsIndex.remove(i);
		}
	}

	public JSONArray openFile(final String filePath) {
		try {
			String jsonFileContents = Files.readString(Paths.get(filePath));
			JSONArray bookmarkJSONArray = new JSONArray(jsonFileContents);
			System.out.println(bookmarkJSONArray);

			return bookmarkJSONArray;
		}
		catch(IOException e) {
			System.out.println("Failed");
			System.out.println(e.getMessage());
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
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

	public void inputDataFile(final String filePath) {
		JSONArray jsonFileContents = openFile(filePath);

		Bookmark bk;
		Iterator iter = jsonFileContents.iterator();
		while (iter.hasNext()) {
			bk = processJson(iter.next().toString());
			addNewBookmark(bk.getTitle(), bk);
		}
	}
} // End of Model class
