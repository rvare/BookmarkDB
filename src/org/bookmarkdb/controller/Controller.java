package org.bookmarkdb.controller;

import org.bookmarkdb.model.*;
import org.bookmarkdb.view.*;

public class Controller {

	private final MainGui view;

	public Controller(MainGui view) {
		System.out.println("Controller constructor")
		this.view = view;
	}

	// Inner classes
	class newListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("new fired");
		}
	} // End of newListener

	class editListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("edit fired");
		}
	} // End of editListener

	class copyListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("copy fired");
		}
	} // End of copyListener

	class deleteListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("delete fired");
		}
	} // End of deleteListener

	class searchListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("search fired");
		}
	} // End of searchListener
} // End of Controller
