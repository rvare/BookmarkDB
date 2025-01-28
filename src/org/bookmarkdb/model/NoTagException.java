package org.bookmarkdb.model;

public class NoTagException extends Exception {
	public NoTagException(String errorMessage) {
		super(errorMessage);
	}
}
