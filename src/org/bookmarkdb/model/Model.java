package org.bookmarkdb.model;

import java.util.*;
import java.io.*;

import org.bookmarkdb.model.Bookmark;
import org.bookmarkdb.model.AVL_Tree;

public class Model {
	private HashMap<String, Bookmark> tagsIndex;
	private AVL_Tree avl_tree;

	public Model() {
		System.out.println("Model constructor");
		tagsIndex = new HashMap<String, Bookmark>();
		avl_tree = new AVL_Tree();
	}

	// Getters
	public Bookmark getBookmarksByTag(String tag) {
		Bookmark bookmark = tagsIndex.get(tag);
		return bookmark;
	}

	public Bookmark getBookmarkByTitle(String title) {
		System.out.println("In getBookmarkByTitle");
		System.out.println("Is root null?");
		System.out.println(avl_tree.getRoot());
		AVL_Node node = avl_tree.searchBookmark(avl_tree.getRoot(), title);

		if (node == null) {
			Bookmark bookmark = new Bookmark();
			bookmark.setTitle("DNE");
			return bookmark;
		}

		return node.getBookmark();
	}

	public void getAllBookmarks() {

	}

	public String getTags() {
		Object[] keyObjects = tagsIndex.keySet().toArray();
		String stringTags = Arrays.toString(keyObjects);
		return stringTags;
	}

	// Setters
	public void setBookmarkTitle(String oldTitle, String newTitle) {

	}

	public void setBookmarkTags(String title, String[] tags) {

	}

	public void setBookmarkDescription(String title, String description) {

	}

	public void setBookmarkURL(String title, String url) {

	}

	public void setBookmarkDateModified(String title, Date date) {

	}

	// Operations
	public void addNewBookmark(String key, Bookmark bookmark) {
		System.out.println("addNewBookmark");
		System.out.println("Is root null?");
		System.out.println(avl_tree.getRoot());
		avl_tree.insert(avl_tree.getRoot(), key, bookmark);
		ArrayList<String> tags = bookmark.getTags();

		for (String i : tags) {
			tagsIndex.put(i, bookmark);
		}
	}

	public void addNewTag(String title, String new_tag) {

	}

	public void deleteBookmark(String title) {

	}
	public void openFile() {

	}

	public void processJson() {

	}
}
