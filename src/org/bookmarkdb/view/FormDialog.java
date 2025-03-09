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

public class FormDialog extends JDialog {
	// These four strings will be used to capture the data and to be converted to JSON
	private String url;
	private String title;
	private String description;
	private String[] tags;

	private Date dateCreated;
	private Date dateModified;

	private JButton buttonSave;
	private JButton buttonCancel;

	private JTextField textFieldUrl;
	private JTextField textFieldTitle;
	private JTextField textFieldTags;

	private JTextArea textAreaDescription;

	private boolean cancelFlag;

	private final int WIDTH = 500;
	private final int HEIGHT = 300;

	public FormDialog() {
		System.out.println("GuiForm constructor");
		// setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Form elements: fields and labels
		JPanel formPanel = new JPanel();
		formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
		this.getContentPane().add(BorderLayout.CENTER, formPanel);

		// URL input
		Box urlBox = new Box(BoxLayout.X_AXIS);
		JLabel urlLabel = new JLabel("URL: ");
		this.textFieldUrl = new JTextField(10);
		urlBox.add(urlLabel);
		urlBox.add(textFieldUrl);
		formPanel.add(urlBox);

		// Title input
		Box titleBox = new Box(BoxLayout.X_AXIS);
		JLabel titleLabel = new JLabel("Title: ");
		this.textFieldTitle = new JTextField(10);
		titleBox.add(titleLabel);
		titleBox.add(textFieldTitle);
		formPanel.add(titleBox);

		// Will need to change so the label is on top rather than on the side, but will fix later
		// Description input
		Box descriptionBox = new Box(BoxLayout.X_AXIS);
		JLabel descriptionLabel = new JLabel("Description: ");
		this.textAreaDescription = new JTextArea(10, 10);
		this.textAreaDescription.setLineWrap(true);
		descriptionBox.add(descriptionLabel);
		descriptionBox.add(textAreaDescription);
		formPanel.add(descriptionBox);

		// Tags input
		Box tagsBox = new Box(BoxLayout.X_AXIS);
		JLabel tagsLabel = new JLabel("Tags: ");
		this.textFieldTags = new JTextField(10);
		tagsBox.add(tagsLabel);
		tagsBox.add(textFieldTags);
		formPanel.add(tagsBox);

		// Form buttons
		JPanel formButtonPanel = new JPanel();
		this.buttonSave = new JButton("Save");
		this.buttonSave.addActionListener(new saveListener());
		formButtonPanel.add(buttonSave);
		this.buttonCancel = new JButton("Cancel");
		this.buttonCancel.addActionListener(new cancelListener());
		formButtonPanel.add(buttonCancel);
		this.getContentPane().add(BorderLayout.SOUTH, formButtonPanel);

		this.setSize(WIDTH, HEIGHT);
		this.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);

		this.cancelFlag = false;
	} // End constructor

	// Getters
	public String getTitleText() {
		return this.textFieldTitle.getText();
	}

	public String getUrlText() {
		return this.textFieldUrl.getText();
	}

	public String getDescriptionText() {
		return this.textAreaDescription.getText();
	}

	public String getTagsText() {
		return this.textFieldTags.getText();
	}

	public boolean canceledHit() {
		return this.cancelFlag;
	}

	// Setters
	public void setFormDialog(final String url, final String title, final String desc, final ArrayList<String> tags) {
		this.textFieldUrl.setText(url);
		this.textFieldTitle.setText(title);
		this.textAreaDescription.setText(desc);

		String tagsString = String.format("%s", tags);
		tagsString = tagsString.replace("[", " ");
		tagsString = tagsString.replace("]", " ");
		System.out.println(tagsString.trim());
		this.textFieldTags.setText(tagsString.trim());
	}

	// Listeners
	// TODO: Get rid of dispose and make it so that save will return a string in JSON format. Is this TODO necessary?
	// Internal classes
	// NOTE: This class will go to the controller and handle the saving of the data but the button that constructs the form won't do that
	class saveListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("FormDialog save button fired");
			cancelFlag = false; // The reason this is here is to ensure that it's false when saved, but it might be too redundant
			dispose();
		}
	}

	class cancelListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("FormDialog cancel button fired");
			cancelFlag = true;
			dispose();
		}
	}
} // End of GuiForm
