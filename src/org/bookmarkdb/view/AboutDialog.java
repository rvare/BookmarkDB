package org.bookmarkdb.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;

public class AboutDialog extends JDialog {
	public AboutDialog() {
		System.out.println("AboutDialog constructor");
		JLabel applicationTitle = new JLabel("BookmarkDb");
		applicationTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel authorAndCopyrightLabel = new JLabel("(c) 2025 Richard Varela", SwingConstants.CENTER);
		authorAndCopyrightLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel versionNumber = new JLabel("v1.0", SwingConstants.CENTER);
		versionNumber.setAlignmentX(Component.CENTER_ALIGNMENT);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		panel.add(applicationTitle);
		panel.add(authorAndCopyrightLabel);
		panel.add(versionNumber);

		getContentPane().add(BorderLayout.CENTER, panel);

		pack();
		setSize(200, 100);
		setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
	}
}
