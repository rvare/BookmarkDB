package org.bookmarkdb.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class Bookmark {
	private String url;
	private String title;
	private String description;
	private ArrayList<String> tags;
	private LocalDateTime dateCreated;
	private LocalDateTime dateModified;

	public Bookmark() {
		System.out.println("    Defualt Bookmark constructor");
		this.tags = new ArrayList<String>();
	}

	public Bookmark(final String url, final String title, final String description, String[] tags) {
		System.out.println("    Non-Defualt Bookmark constructor");
		this.url = url;
		this.title = title;
		this.description = description;
		this.tags = new ArrayList<String>(Arrays.asList(tags));
		this.dateCreated = LocalDateTime.now();
		this.dateModified = LocalDateTime.now();
	}

	// Getters
	public String getURL() {
		return this.url;
	}

	public String getTitle() {
		return this.title;
	}

	public String getDescription() {
		return this.description;
	}

	public ArrayList<String> getTags() {
		return this.tags;
	}

	public LocalDateTime getDateCreated() {
		return this.dateCreated;
	}

	public LocalDateTime getDateModified() {
		return this.dateModified;
	}
	
	// Setters
	public void setURL(final String url) {
		this.url = url;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setTag() {

	}

	public void setTagList() {

	}

	public void setDateModified(final LocalDateTime dateModified) {
		this.dateModified = dateModified;
	}

	// Operations
	public void addNewTag(final String newTag) {
		this.tags.add(newTag);
	}

	@Override
	public String toString() {
		return String.format("(Title: %s,\nURL: %s,\nDescription: %s,\nTags: %s,\nDate Created: %s,\nDate Modified: %s)",
								this.title, this.url, this.description, this.tags, this.dateCreated, this.dateModified);
	}
} // End of Bookmark
