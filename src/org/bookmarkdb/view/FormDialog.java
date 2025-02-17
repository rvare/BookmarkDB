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

	// TODO CLEAN: Clean up comments and make it more readable
	public FormDialog() {
		System.out.println("GuiForm constructor");
		// setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Form elements: fields and labels
		JPanel formPanel = new JPanel();
		formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
		this.getContentPane().add(BorderLayout.CENTER, formPanel);

		Box urlBox = new Box(BoxLayout.X_AXIS);
		JLabel urlLabel = new JLabel("URL: ");
		this.textFieldUrl = new JTextField(10);
		urlBox.add(urlLabel);
		urlBox.add(textFieldUrl);
		formPanel.add(urlBox);

		Box titleBox = new Box(BoxLayout.X_AXIS);
		JLabel titleLabel = new JLabel("Title: ");
		this.textFieldTitle = new JTextField(10);
		titleBox.add(titleLabel);
		titleBox.add(textFieldTitle);
		formPanel.add(titleBox);

		// TODO: Fix graphical issue
		Box descriptionBox = new Box(BoxLayout.Y_AXIS);
		JLabel descriptionLabel = new JLabel("Description: ");
		this.textAreaDescription = new JTextArea(10, 10);
		descriptionBox.add(descriptionLabel);
		descriptionBox.add(textAreaDescription);
		formPanel.add(descriptionBox);

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

		this.setSize(500, 300); // TODO: Create constants
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

		// TODO: Change this so that it doesn't create so many String objects that get destroyed by the garbage collector
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
