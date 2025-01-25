package org.bookmarkdb.model;

import java.util.*;
import java.io.*;
import org.json.*;

import org.bookmarkdb.model.Bookmark;
import org.bookmarkdb.model.AVL_Tree;

public class Model {
	private HashMap<String, Bookmark> tagsIndex; // Change to use a list of some kind
	private AVL_Tree avl_tree;

	public Model() {
		System.out.println("Model constructor");
		tagsIndex = new HashMap<String, Bookmark>();
		avl_tree = new AVL_Tree();
	}

	// Getters
	public Bookmark getBookmarksByTag(final String tag) {
		// System.out.println(String.format("    Getting bookmark by %s", tag));
		Bookmark bookmark = tagsIndex.get(tag);
		return bookmark;
	}

	public Bookmark getBookmarkByTitle(final String title) {
		// System.out.println("    In getBookmarkByTitle");
		// System.out.println("    Is root null?");
		// System.out.println(avl_tree.getRoot());
		AVL_Node node = avl_tree.searchBookmark(avl_tree.getRoot(), title);

		// TODO Handle the case in which the bookmark does not exists. Might need to throw an error.
		if (node == null) {
			// System.out.println("    Not found");
			Bookmark bookmark = new Bookmark();
			bookmark.setTitle("DNE");
			bookmark.setDescription("DNE");
			return bookmark;
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
	public void setBookmarkTitle(final String oldTitle, final String newTitle) {
		// System.out.println("    setBookmarkTitle");
		Bookmark bookmark = getBookmarkByTitle(oldTitle);
		deleteBookmark(bookmark.getTitle());
		bookmark.setTitle(newTitle);
		addNewBookmark(newTitle, bookmark);
	}

	public void setBookmarkTags(final String title, final String[] tags) {

	}

	public void setBookmarkDescription(final String title, final String newDescription) {
		// System.out.println("    setBookmarkDescription");
		Bookmark bookmark = getBookmarkByTitle(title);
		// System.out.println(bookmark.getDescription());
		bookmark.setDescription(newDescription);
		// System.out.println(bookmark.getDescription());
	}

	public void setBookmarkURL(final String title, final String newUrl) {
		// System.out.println("    setBookmarkURL");
		Bookmark bookmark = getBookmarkByTitle(title);
		bookmark.setURL(newUrl);
	}

	public void setBookmarkDateModified(final String title, final Date date) {

	}

	// Operations
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

	public void addNewTag(final String title, final String newTag) {
		// System.out.println("    addNewTag");
		Bookmark bookmark = getBookmarkByTitle(title);
		bookmark.addNewTag(newTag);
	}

	public void deleteBookmark(final String title) { 
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

	public void openFile() {

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
} // End of Model class
