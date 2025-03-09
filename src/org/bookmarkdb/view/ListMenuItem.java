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

package org.bookmarkdb.view;

public class ListMenuItem {
	private String itemName;
	private String itemDescription;

	public ListMenuItem(final String itemName, final String desc) {
		this.itemName = itemName;
		this.itemDescription = desc;
	}

	public String getItemName() {
		return this.itemName;
	}
	
	public String getDescription() {
		return this.itemDescription;
	}

	public void setDescription(final String newDesc) {
		this.itemDescription = newDesc;
	}

	@Override
	public String toString() {
		return this.itemName;
	}
}
