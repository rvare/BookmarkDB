/*
A program that allows a user to manage their bookmarks.
Copyright (C) 2025  Richard Varela

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

package org.bookmarkdb.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

// TODO CLEAN: Remove print statements

public class Bookmark {
	private String url;
	private String title;
	private String description;
	private ArrayList<String> tags;
	private final LocalDate dateCreated;
	private LocalDate dateModified;

	public Bookmark() {
		this.tags = new ArrayList<String>();
		this.dateCreated = LocalDate.now();
	}

	public Bookmark(final String url, final String title, final String description,
					String[] tags, final String dateCreated, final String dateModified) {
		this.url = url;
		this.title = title;
		this.description = description;
		this.tags = new ArrayList<String>(Arrays.asList(tags));
		this.dateCreated = LocalDate.parse(dateCreated);
		this.dateModified = LocalDate.parse(dateModified);
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

	public LocalDate getDateCreated() {
		return this.dateCreated;
	}

	public LocalDate getDateModified() {
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

	public void setTagList(String[] tags) {
		this.tags = new ArrayList<String>(Arrays.asList(tags));
	}

	public void setDateModified(final LocalDate dateModified) {
		this.dateModified = dateModified;
	}

	// Operations
	public void addNewTag(final String newTag) {
		this.tags.add(newTag);
	}

	@Override
	public String toString() {
		return String.format("Title: %s\nURL: %s\nDescription: %s\nTags: %s\nDate Created: %s\nDate Modified: %s",
								this.title, this.url, this.description, this.tags, this.dateCreated, this.dateModified);
	}
} // End of Bookmark
