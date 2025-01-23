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
		System.out.println("Add bookmarks to model");

		String[] tagArr1 = {"tag1", "tag2"};
		Bookmark b1 = new Bookmark("url1", "First bookmark", "The first", tagArr1);

		String[] tagArr2 = {"tag3", "tag4"};
		Bookmark b2 = new Bookmark("url2", "Second bookmark", "The 2nd", tagArr2);

		String[] tagArr3 = {"tag5", "tag6"};
		Bookmark b3 = new Bookmark("url3", "Third bookmark", "The 3rd", tagArr3);

		System.out.println("Main: Insertion test");
		System.out.println("  Insert 1st bookmark");
		model.addNewBookmark(b1.getTitle(), b1);
		System.out.println("  Insert 2nd bookmark");
		model.addNewBookmark(b2.getTitle(), b2);
		System.out.println("  Insert 3rd bookmark");
		model.addNewBookmark(b3.getTitle(), b3);


		System.out.println("===");
		System.out.println("Main: Testing search by title");
		System.out.println("  Get 1st bookmark:");
		Bookmark tB = model.getBookmarkByTitle("First bookmark");
		System.out.println(String.format("  Main: Checking node:\n" , tB));
		System.out.println("  Get 2nd bookmark:");
		tB = model.getBookmarkByTitle("Second bookmark");
		System.out.println(String.format("  Main: Checking node:\n" , tB));
		System.out.println("  Get 3rd bookmark:");
		tB = model.getBookmarkByTitle("Third bookmark");
		System.out.println(String.format("  Main: Checking node:\n" , tB));


		System.out.println("===");
		System.out.println("Main: Get tags");
		String tagsStuff = model.getTags();
		System.out.println(tagsStuff);


		System.out.println("===");
		System.out.println("Main: Get bookmark by tag");
		System.out.println("  Main: Get 1st bookmark:");
		tB = model.getBookmarksByTag("tag1");
		System.out.println(String.format("Checking node:\n" , tB));
		System.out.println("  Main: Get 2nd bookmark:");
		tB = model.getBookmarksByTag("tag3");
		System.out.println(String.format("Checking node:\n" , tB));
		System.out.println("  Main: Get 3rd bookmark:");
		tB = model.getBookmarksByTag("tag5");
		System.out.println(String.format("Checking node:\n" , tB));

		System.out.println("==Get second bookmark first==");
		tB = model.getBookmarkByTitle("Second bookmark");
		System.out.println(String.format("Checking node:\n%s" , tB));
		System.out.println("===");
		System.out.println("Main: Testing setters");
		System.out.println("  Main: Change bookmark title");
		// System.out.println("   Did we get it?");
		model.setBookmarkTitle("Second bookmark", "Cool site");
		tB = model.getBookmarkByTitle("Cool site");
		System.out.println(String.format("  Main: Checking node:\n" , tB));
		System.out.println("  Main: Change bookmark description");
		model.setBookmarkDescription("Cool site", "A really cool website");
		System.out.println(String.format("  Main: Checking node:\n" , tB));
		System.out.println("  Main: Change bookmark URL");
		model.setBookmarkURL("Cool site", "url2");
		System.out.println(String.format("  Main: Checking node:\n" , tB));

		view.showMainFrame();
	}
} // End of MainBookmarkDb
