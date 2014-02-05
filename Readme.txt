The program consists of 3 parts
FileManager:
This is where the main functionality of the program is contained with methods for parsing, searching, editing and getting information about files

Runner:
This is where the main method and where the user interface and menus are contained. On starting the program the user is prompted to parse a file.
If a file is not parsed and an attempt is made to access the search, edit or statistics menu, they are reminded to do so again and any key will return them to the man menu.
Help and Quit menus are available without having to parse a file.

The Main menu is broken down into sub-menus
	1) Parse a file or URL
	As some of the files may be very large and take a long time to parse a system beep is emitted once completed so that the user can be notified.
		1) Parse file or URL
		Option to parse local files using system path or remote files using URL.
		2) Parse html file or URL (remove all html formatting)
		Option to parse local html files using system path or remote html files using URL, this will strip all html formatting out.
	2) Search and Edit
		1) Find index positions of words
			1) Find first index position of word
			Does exactly what it says on the tin
			2) Find last index position of word
			Ditto.
			3) List all index positions of word
			Lists every index position of that word
		2) Replace words
		The user is asked for confirmation before replacing words.
			1) Replace all instances of a particular word
			Replaces every instance of a word
			2) Replace a word at a particular index
			Replaces a word at an index number. The word and index are displayed prior to replacement
		3) Delete words
		The user is asked for confirmation before deleting words.
		4) List all words
		The user is warned before listing that the process may take some time for larger files.
		5) List all unique words
		Lists only one instance of each word.
		The user is warned before listing that the process may take some time for larger files.
	3) Statistics
		1) Get size of container
		Gets the size of the word container including unused space.
		2) Count words
		Gets the amount of words (including duplicates) only.
	4) Help
	A comprehensive help menu with sub-menus for Parsing, Search & Edit and Statistics sections.
	5) Quit
	Quit and close the program, the user is asked for confirmation before quitting
	
Helper:
This contains all the methods which manage the help files for the help menu.

Full details of all public methods are in the attached JavaDocs located in the docs folder, and the program is fully commented.
For very large programs it is advised to increase the size of the memory available to the JVM, even so parsing a large file is slow due to the array based nature of the program.
(93 min for War and Peace without extra memory and my laptop is fairly fast!) (Note: when running another test on War & Peace using 2Gb memory, it took 96 minutes, so extra memory would not seem to be a factor)
To run the program, run Runner and follow the instructions. Use the help menus if stuck.