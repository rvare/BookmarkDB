package org.bookmarkdb.model;

import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;

public class Bookmark {
	private String url;
	private String title;
	private String description;
	private ArrayList<String> tags;
	private Date dateCreated;
	private Date dateModified;

	public Bookmark() {
		System.out.println("Defualt Bookmark constructor");
		this.tags = new ArrayList<String>();
	}

	public Bookmark(final String url, final String title, final String description, final String[] tags) {
		System.out.println("Non-Defualt Bookmark constructor");
		this.url = url;
		this.title = title;
		this.description = description;
		this.tags = new ArrayList<String>(Arrays.asList(tags));
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

	public Date getDateCreated() {
		return this.dateCreated;
	}

	public Date getDateModified() {
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

	public void setDateModified(final Date dateModified) {
		this.dateModified = dateModified;
	}

} // End of Bookmark
