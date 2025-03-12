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

# 3 Running

To run the program, go to the `classes` directroy and run the following:

```text
java -cp .;../lib<org.json jar file> org/bookmarkdb/main/MainBookmarkDb
```

# 4 Creating the Jar File

To create the jar file, first create the `manifest.txt` file in the `classes` directory. Be sure to include the following:

```text
Main-Class: org.bookmarkdb.main.MainBookmarkDb
Class-Path: ./lib/<org.json jar>
```

Then run the following inside the `classes` directory:

```text
jar -cvmf manifest.txt ../bookmarkdb.jar org
```

# Shipping

If you decide to ship the program out, like share it with someone and such, do the following:

1. Change the Manifest file to reflect that the program's jar file and the org.json jar file will be at the same directory level.
2. Zip both jars in the same folder.

And that's it.
