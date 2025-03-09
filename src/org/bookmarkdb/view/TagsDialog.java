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

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;

public class TagsDialog extends JDialog {
	private DefaultListModel<String> listModel;
	private JList<String> tagList;
	private JButton okayButton;
	private JButton cancelButton;

	private String selectedTag;

	private boolean cancelFlag;

	private final int WIDTH = 500;
	private final int HEIGHT = 300;

	public TagsDialog(String[] tagsList) {
		// Create the list and DefaultListModel
		listModel = new DefaultListModel<String>();
		tagList = new JList<String>();
		tagList.setModel(listModel);

		for (String i : tagsList) {
			listModel.addElement(i);
		}

		tagList.addListSelectionListener(new tagListListener());
		this.getContentPane().add(BorderLayout.CENTER, tagList);


		// Create dialog buttons
		JPanel dialogButtonPanel = new JPanel();
		this.okayButton = new JButton("OK");
		this.okayButton.addActionListener(new okayListener());
		dialogButtonPanel.add(okayButton);

		this.cancelButton = new JButton("Cancel");
		this.cancelButton.addActionListener(new cancelListener());
		dialogButtonPanel.add(cancelButton);
		this.getContentPane().add(BorderLayout.SOUTH, dialogButtonPanel);

		
		this.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);

		cancelFlag = false;

		this.setSize(WIDTH, HEIGHT);
	}

	// Getters
	public String getSelectedTag() {
		return this.selectedTag;
	}

	public boolean canceledHit() {
		return this.cancelFlag;
	}

	// Internal classes
	class okayListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("OK hit");
			cancelFlag = false;
			dispose();
		}
	}

	class cancelListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("Cancel hit");
			cancelFlag = true;
			dispose();
		}
	}

	class tagListListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent selEvent) {
			if (!selEvent.getValueIsAdjusting()) {
				System.out.println("Selected tag");
				String item = tagList.getSelectedValue();
				assert item != null : "Selected tag item is null.";
				selectedTag = item;
				System.out.println(selectedTag);
			}
		}
	}
} // End of TagsDialog
