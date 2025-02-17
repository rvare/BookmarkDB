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
