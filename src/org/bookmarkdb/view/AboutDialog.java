package org.bookmarkdb.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;

// TODO CLEAN: Clean up for better reading
// TODO: Create some constants for the strings for easy editing

public class AboutDialog extends JDialog {
	public AboutDialog() {
		System.out.println("AboutDialog constructor");
		this.setTitle("About");
		JLabel applicationTitle = new JLabel("BookmarkDb");
		applicationTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel authorAndCopyrightLabel = new JLabel("(c) 2025 Richard Varela", SwingConstants.CENTER);
		authorAndCopyrightLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel versionNumber = new JLabel("v1.0", SwingConstants.CENTER);
		versionNumber.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel libInfo = new JLabel("JSON-Java: Release 20250107");
		libInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		panel.add(applicationTitle);
		panel.add(authorAndCopyrightLabel);
		panel.add(versionNumber);
		panel.add(libInfo);

		getContentPane().add(BorderLayout.CENTER, panel);

		pack();
		setSize(250, 150);
		setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
	}
}
