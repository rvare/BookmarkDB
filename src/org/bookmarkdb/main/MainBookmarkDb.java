package org.bookmarkdb.main;

import org.bookmarkdb.view.MainGui;
import org.bookmarkdb.model.Model;

public class MainBookmarkDb {
	public static void main(String[] args) {
		Model model = new Model();
		MainGui view = new MainGui();
		// Controller controller = new Controller(model, view);
		view.showMainFrame();
	}
} // End of MainBookmarkDb
