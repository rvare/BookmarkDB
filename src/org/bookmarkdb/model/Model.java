package org.bookmarkdb.model;

import java.util.*;
import java.io.*;

import org.bookmarkdb.model.Bookmark;
import org.bookmarkdb.model.AVL_Tree;

public class Model {
	private HashMap<String, Bookmark> tags;
	private AVL_Tree avl_tree;

	public Model() {
		System.out.println("Model constructor");
		tags = new HashMap<String, Bookmark>();
		avl_tree = new AVL_Tree();
	}

	// Getters
	public Bookmark getBookmarksByTag(String tag) {
		return new Bookmark();
	}

	public Bookmark getBookmarkByTitle(String title) {
		return new Bookmark();
	}

	public void getAllBookmarks() {

	}

	public String[] getTags() {
		String[] str = {""};
		return str;
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
	public void addNewBookmark() {

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
