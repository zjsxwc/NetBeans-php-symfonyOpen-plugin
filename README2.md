ABOUT

symfonyOpen is a small Netbeans plugin to search and open symfony files fast.
symfonyOpen is based on jopen project.
It presents the search results as you type and has a simple directory search.


INSTALLATION

Use the following Netbeans module file:
/build/org-jojo.nbm


USAGE

I recommend to set up a shortcut (e.g. ALT+O).

symfonyOpen will search only in your "main project". You can switch projects using
the built in "Select Project" menu.

Just enter the filename you are looking for to start a search.

symfonyOpen also uses a regex kind of search:
"smecontller.b" will also find "some_controller.rb"

Match file extensions with '$':
".txt$" will not find "file.txt.bak"

To use the directory search enter the the initial letters of the folders
seperated with a whitespace followed by the filename:
"p t y fil" will find "/path/to/your/file.tmp"

Use CTRL+R to reload the filestructure if new files where added to the project
