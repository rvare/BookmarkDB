package org.bookmarkdb.model;

import java.util.*;
import java.io.*;

import org.bookmarkdb.model.Bookmark;

public class Model {
	private HashMap<String, Bookmark> tags;
	// private BinarySearchTree binaryTree;

	public Model() {
		System.out.println("Model constructor");
		tags = new HashMap<String, Bookmark>();
	}

	public Bookmark getBookmarkByTag(String tag) {
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

	public void addNewBookmark() {

	}

	public void addNewTag(String title, String new_tag) {

	}

	public void deleteBookmark(String title) {

	}

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

	public void openFile() {

	}

	public void processJson() {

	}
}
