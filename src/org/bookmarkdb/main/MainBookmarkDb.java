package org.bookmarkdb.main;

import org.bookmarkdb.view.MainGui;
import org.bookmarkdb.model.*;

public class MainBookmarkDb {
	public static void main(String[] args) {
		AVL_Tree tree = new AVL_Tree();
		Model model = new Model();
		MainGui view = new MainGui();
		// Controller controller = new Controller(model, view);
		view.showMainFrame();
	}
} // End of MainBookmarkDb
