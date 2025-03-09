The following are steps on how to build BookmarkDB from source.

# 1 Gathering the files

Clone the repository into a directory of your choice.

Go to https://github.com/stleary/JSON-java and download the jar. This file provides all the JSON classes used in BookmarkDB. Put the jar file in a folder called `lib` in the repository at the same level as the `src` directory.

If the jar file is not available or you prefer to build the external library from source, please following the steps in the `README` file at the repo pasted above.

# 2 Compiling

Compile the project with the `src` directory by running the following:

```text
javac -cp ../lib/<org.json jar file>; -d ../classes org/bookmarkdb/main/MainBookmarkDb.java
```

To run the program, performt the following:

```text
java -cp .;../lib<org.json jar file> org/bookmarkdb/main/MainBookmarkDb
```

