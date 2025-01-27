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
	private HashMap<String, Bookmark> tagsIndex; // Change to use a list of some kind
	private AVL_Tree avl_tree;

	public Model() {
		System.out.println("Model constructor");
		tagsIndex = new HashMap<String, Bookmark>();
		avl_tree = new AVL_Tree();
	}

	// Getters
	public Bookmark getBookmarksByTag(final String tag) throws BookmarkException { // TODO Modify to use a list in the hashmap
		// System.out.println(String.format("    Getting bookmark by %s", tag));
		Bookmark bookmark = tagsIndex.get(tag);

		if (bookmark == null) {
			throw new BookmarkException("Could not find bookmark by tag");
		}

		return bookmark;
	}

	public Bookmark getBookmarkByTitle(final String title) throws BookmarkException {
		// System.out.println("    In getBookmarkByTitle");
		// System.out.println("    Is root null?");
		// System.out.println(avl_tree.getRoot());
		AVL_Node node = avl_tree.searchBookmark(avl_tree.getRoot(), title);

		if (node == null) {
			throw new BookmarkException("Bookmark by title not found.");
		}

		// System.out.println("        Checking sides");
		// System.out.println(node.getLeftNode());
		// System.out.println(node.getRightNode());

		return node.getBookmark();
	}

	public void getAllBookmarks() { // TODO Implement

	}

	public String getTags() {
		// System.out.println("    Getting tags");
		Object[] keyObjects = tagsIndex.keySet().toArray();
		String stringTags = Arrays.toString(keyObjects);
		return stringTags;
	}

	// Setters
	// TODO Implement all setters
	// TODO Call setDateModified to all setters
	// TODO Modify to handle the exception of bookmark DNE
	public void setBookmarkTitle(final String oldTitle, final String newTitle) throws BookmarkException {
		// System.out.println("    setBookmarkTitle");

		Bookmark bookmark = getBookmarkByTitle(oldTitle); // This statement throws an exception

		deleteBookmark(bookmark.getTitle());
		bookmark.setTitle(newTitle);
		bookmark.setDateModified(LocalDateTime.now());
		addNewBookmark(newTitle, bookmark);
	}

	public void setBookmarkTags(final String title, final String[] tags) {

	}

	public void setBookmarkDescription(final String title, final String newDescription) throws BookmarkException {
		// System.out.println("    setBookmarkDescription");
		Bookmark bookmark = getBookmarkByTitle(title); // Throws exception
		// System.out.println(bookmark.getDescription());
		bookmark.setDescription(newDescription);
		bookmark.setDateModified(LocalDateTime.now());
		// System.out.println(bookmark.getDescription());
	}

	public void setBookmarkURL(final String title, final String newUrl) throws BookmarkException {
		// System.out.println("    setBookmarkURL");
		Bookmark bookmark = getBookmarkByTitle(title);
		bookmark.setURL(newUrl);
		bookmark.setDateModified(LocalDateTime.now());
	}

	public void setBookmarkDateModified(final String title, final Date date) {

	}

	// Operations
	// TODO: Rewrite this so that it uses two threads, one for AVL_Tree insertion and one for tagIndex insertrion
	public void addNewBookmark(final String key, final Bookmark bookmark) {
		// System.out.println("    addNewBookmark");
		// System.out.println("      Is root null?");
		// System.out.println(avl_tree.getRoot());
		avl_tree.setRoot(avl_tree.insert(avl_tree.getRoot(), key, bookmark));
		ArrayList<String> tags = bookmark.getTags();

		for (String i : tags) {
			tagsIndex.put(i, bookmark);
		}
	}

	public void addNewTag(final String title, final String newTag) throws BookmarkException {
		// System.out.println("    addNewTag");
		Bookmark bookmark = getBookmarkByTitle(title);
		bookmark.addNewTag(newTag);
		bookmark.setDateModified(LocalDateTime.now());
	}

	public void deleteBookmark(final String title) throws BookmarkException {
		// System.out.println("    deleteBookmark");
		Bookmark bookmark = getBookmarkByTitle(title);
		// System.out.println("      Delete in AVL tree");
		avl_tree.deleteNode(avl_tree.getRoot(), title);

		// System.out.println("      Delete in index");
		ArrayList<String> bookmarkTags = bookmark.getTags();

		for (String i : bookmarkTags) {
			tagsIndex.remove(i);
		}
	}

	public JSONArray openFile(final String filePath) {
		try {
			// String jsonFileContents = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
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
	}

	public Bookmark processJson(final String jsonLine) {
		JSONObject jo = new JSONObject(jsonLine);

		// System.out.println(String.format("JSON line: %s", jo)); // For debugging

		String url = jo.getString("url");
		String title = jo.getString("title");
		String description = jo.getString("description");

		JSONArray jsonTags = jo.getJSONArray("tags");
		Object[] objArr = jsonTags.toList().toArray();

		String[] strArr = Arrays.copyOf(objArr, objArr.length, String[].class);

		return new Bookmark(url, title, description, strArr);
	}

	public Bookmark processJson(final JSONObject jsonObj) {
		String url = jsonObj.getString("url");
		String title = jsonObj.getString("title");
		String description = jsonObj.getString("description");

		JSONArray jsonTags = jsonObj.getJSONArray("tags");
		Object[] objArr = jsonTags.toList().toArray();

		String [] strArr = Arrays.copyOf(objArr, objArr.length, String[].class);

		return new Bookmark(url, title, description, strArr);
	}

	public void inputDataFile(final String filePath) {
		JSONArray jsonFileContents = openFile(filePath);

		Bookmark bk;
		Iterator iter = jsonFileContents.iterator();
		while (iter.hasNext()) {
			bk = processJson(iter.next().toString());
			// System.out.println(iter.next().toString());
			// System.out.println(bk);
			addNewBookmark(bk.getTitle(), bk);
		}
	}
} // End of Model class
