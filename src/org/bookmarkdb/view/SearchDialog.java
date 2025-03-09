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

public class SearchDialog {
	private String url;
	private String title;
	private String description;
	private String tags;

	private JButton buttonSave;
	private JButton buttonCancel;

	private JTextField textFieldUrl;
	private JTextField textFieldTitle;
	private JTextField textFieldTags;

	private JTextArea textAreaDescription;

	public SearchDialog(final String url, final String title, final String description, final String tags) {
		
	}
} // End SearchDialog
