package org.bookmarkdb.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;

public class FormDialog extends JDialog { // TODO: Change to JDialogue because it'll stop once it opens
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

	public FormDialog() {
		System.out.println("GuiForm constructor");
		// setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Form elements: fields and labels
		JPanel formPanel = new JPanel();
		formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
		getContentPane().add(BorderLayout.CENTER, formPanel);

		Box urlBox = new Box(BoxLayout.X_AXIS);
		JLabel urlLabel = new JLabel("URL: ");
		textFieldUrl = new JTextField(10);
		urlBox.add(urlLabel);
		urlBox.add(textFieldUrl);
		formPanel.add(urlBox);

		Box titleBox = new Box(BoxLayout.X_AXIS);
		JLabel titleLabel = new JLabel("Title: ");
		textFieldTitle = new JTextField(10);
		titleBox.add(titleLabel);
		titleBox.add(textFieldTitle);
		formPanel.add(titleBox);

		Box descriptionBox = new Box(BoxLayout.Y_AXIS);
		JLabel descriptionLabel = new JLabel("Description: ");
		textAreaDescription = new JTextArea(10, 10);
		descriptionBox.add(descriptionLabel);
		descriptionBox.add(textAreaDescription);
		formPanel.add(descriptionBox);

		Box tagsBox = new Box(BoxLayout.X_AXIS);
		JLabel tagsLabel = new JLabel("Tags: ");
		textFieldTags = new JTextField(10);
		tagsBox.add(tagsLabel);
		tagsBox.add(textFieldTags);
		formPanel.add(tagsBox);

		// Form buttons
		JPanel formButtonPanel = new JPanel();
		buttonSave = new JButton("Save");
		buttonSave.addActionListener(new saveListener());
		formButtonPanel.add(buttonSave);
		buttonCancel = new JButton("Cancel");
		buttonCancel.addActionListener(new cancelListener());
		formButtonPanel.add(buttonCancel);
		getContentPane().add(BorderLayout.SOUTH, formButtonPanel);

		setSize(500, 300);
	} // End constructor

	// TODO: Get rid of dispose and make it so that save will return a string in JSON format
	// Internal classes
	// NOTE: This class will go to the controller and handle the saving of the data but the button that constructs the form won't do that
	class saveListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("FormDialog save button fired");
			dispose();
		}
	}

	class cancelListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("FormDialog cancel button fired");
			dispose();
		}
	}
} // End of GuiForm
