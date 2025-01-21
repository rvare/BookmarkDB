package org.bookmarkdb.main;

import org.bookmarkdb.view.MainGui;
import org.bookmarkdb.model.*;

import java.util.Arrays;

public class MainBookmarkDb {
	public static void main(String[] args) {
		AVL_Tree tree = new AVL_Tree();
		Model model = new Model();
		MainGui view = new MainGui();
		// Controller controller = new Controller(model, view);
		String[] s = {"tag1", "tag2"};
		Bookmark b = new Bookmark("url", "title", "desc", s);
		model.addNewBookmark(b.getTitle(), b);

		String[] s2 = {"tag3", "tag4"};
		b = new Bookmark("url", "some site", "desc", s2);
		model.addNewBookmark(b.getTitle(), b);

		System.out.println("Testing search by title");
		b = model.getBookmarkByTitle("some site");
		System.out.println(b.getTitle());

		System.out.println("Get tags");
		String tagsStuff = model.getTags();
		System.out.println(tagsStuff);

		System.out.println("Get bookmark by tag");
		b = model.getBookmarksByTag("tag1");
		System.out.println(b.getTitle());
		b = model.getBookmarksByTag("tag3");
		System.out.println(b.getTitle());

		view.showMainFrame();
	}
} // End of MainBookmarkDb
