# BookmarkDB

A Java application used to manage and keep track of bookmarks.

# Features

- Sort bookmarks alphabetically.
- Save bookmarks into JSON.
- Export bookmarks to different file formats.
	- XML, HTML, Markdown, and pure text.
- Find bookmarks with a search feature.
- Give bookmarks tags to easily find them by category.
	- Not exported in HTML.
- Add notes to your bookmarks for extra details.
	- Not exported in HTML.
- Easily copy bookmark URL with a single click to your clipboard.

# Help

The following is help documentation which can also be found in the application under `Help-->Documentation`.

The following is what each button does:

- **Home:** Resets the list to show all bookmarks you have.
- **New:** Creates a new bookmark and will open a form to fill out.
- **Edit:** Select a bookmark in the list and change any of its details.
- **Delete:** Select a bookmark in the list and then remove it.
- **Copy:** Copy the bookmark's URL into your clipboard.
- **Search:** Click this button when a you have a query in the search bar.
- **Tags:** Opens a new window showing all the tags you have created. Click on a tag and then the main window will show a list of all bookmarks associated with that tag. Click Home to reset the main window.

The above functions are also available in the menu bar.

There is an export feature only available in the menu bar. This allows you to export your bookmarks to XML, HTML, Markdown, and pure text.

# License

This program is distributed under the GPL Version 3 license.

This program makes use of the package `org.json` by stleary, distributed under a public domain license.
Link to the repository: https://github.com/stleary/JSON-java