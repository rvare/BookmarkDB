**Table of Contents**

- (Introduction)[#introduction]
- (Repository Location)[#repository-location]
- (Style Guide)[#style-guide]
- (Build Guide)[#build-guide]

- - -

# Introduction

This document is to help new people, and also those who have been working on this, to understand the structure and relationships of the project and its components.

# Repository Location

The repository can be put in any folder in any location you want in your system.

# Style Guide

The following are the styling rules for this project:

- Use K&R indentation style.

```java
while (expression) {
	if (expression) {
		// Do something
	}
}
```

- For class names, use nouns and camel case with the first letter upper-case.
	- Ex: `ClassName`.
- For variable names, use nouns and camel case with the first letter in lower-case.
	- Ex: `variableName`.
- Method names will use verbs and camael case with the first letter in lower-case.
	- Ex: `createObject()`, `saveFile()`
- When a method or class is extremely long, at the closing brace, put the following comment `// End of ClassName` or `// End of methodName`.
	- Use your judgement, but good to do when that ending brace can't be displayed.
- Constants are in UPPER CASE.
- The following are rules for comments:
	- Use comments to make headings that help divide sections of code.
		- You can use these headings to easily jump to different sections of the code.
	- Use comments to help reveal hidden control flows.
	- Use comments to explain "cleaverness", but try to avoid that.

# Build Guide

Before you compile and build the project, you will need the following package [org.json](https://github.com/stleary/JSON-java) from stearly on GitHub. Put this package in a folder called `lib` within the repository, at the same location where the `src` is.

To build the project, run the following in the `src` folder:

```bash
javac -cp ../lib/json-java.jar; -d ../classes org/bookmarkdb/main/MainBookmarkDB.java
```

Then, to run the project:

```bash
javac -cp ../lib/json-java.jar; org/bookmarkdb/main/MainBookmarkDB
```

