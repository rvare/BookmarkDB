package org.bookmarkdb.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;

public class DocumentationDialog extends JDialog {
	public DocumentationDialog() {
		System.out.println("Doc Dialog");
		this.setTitle("Documentation");
							// This is a ruler for the window to keep things consistant. Each pipe is 5 characters.
							//--|----|----|----|----|----|----|----|----|----|----|----|----|----|----|----|----|
		String docContent = """
							<html>
							<h1>BookmarkDB Documentation</h1>
							<p>BookmarkDB is a simple application to track your bookmarks independent of a web<br>
							browser and also to add notes to those bookmarks as well.</p>
							<p>The following is what each button does:</p>
							<ul>
							<li>Home: Resets the list to show all bookmarks you have.</li>
							<li>New: Creates a new bookmark and will open a form to fill out.</li>
							<li>Edit: Select a bookmark in the list and change any of its details.</li>
							<li>Delete: Select a bookmark in the list and then remove it.</li>
							<li>Copy: Copy the bookmark's URL into your clipboard.</li>
							<li>Search: Click this button when a you have a query in the search bar.</li>
							<li>Tags: Opens a new window showing all the tags you have created.<br>
							  Click on a tag and then the main window will show a list of all<br>
							  bookmarks associated with that tag. Click Home to reset the main<br>
							  window.</li>
							</ul>
							<p>The above functions are also avaialb in the menu bar.</p>
							<p>There is an export feature only available in the menu bar. This allows you to<br>
							export your bookmarks to XML, OPML, HTML, Markdown, and pure text.</p>
							</html>
							""";

		JLabel docContentContainer = new JLabel(docContent);

		JPanel panel = new JPanel();
		panel.add(docContentContainer);
		this.getContentPane().add(BorderLayout.CENTER, panel);
		
		this.pack();
		this.setSize(500, 400);
		this.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
	}
}
