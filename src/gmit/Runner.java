package gmit;

import java.awt.Toolkit;
import java.io.*;
import java.util.*;
/**
 * Runs the Test Processor program, contains menu items, main method and text processing methods.
 * @author Malcolm O'Beirn
 * @version 1.0 Jan 7 2014
 */
public class Runner {
	private Scanner textInput = new Scanner(System.in);//will handle keyboard input
	private boolean keepRunning= true; //Loop control variable for the do-loop
	private FileManager fm = new FileManager();//to hold processed file
	private Helper helper = new Helper();

	public static void main(String[] args) throws Exception {//because runner throws exception. File may not be found or there may be a connection problem
		new Runner();//start new runner

	}
	/**
	 * Creates an introduction screen with a main menu below. Keeps running until quit is selected from the menu
	 * @throws Exception If the search file is not found or there is an IO connection problem
	 */
	public Runner() throws Exception {
		//These two lines only appear at the start of the program
		System.out.println("------------------- Array Based Text Processor --------------------");
		System.out.println("You should process a file or URL first before using the other menus");
		
		mainmenu();
		
		System.out.println("Goodbye!");
	}
	
	private void mainmenu() {//all menus hidden from view
		do {//Will run at least once and every time a method completes
			System.out.println("----------------------");
			System.out.println("Main Menu");
			System.out.println("----------------------");
			System.out.println("1) Parse a file or URL");
			System.out.println("2) Search and Edit");
			System.out.println("3) Statistics");
			System.out.println("4) Help");
			System.out.println("5) Quit");
			System.out.println("Select option [1-5]: >");
			
			String selection = textInput.next(); //Get the selected item	
			switch (selection) {//select sub-menu based on input
			case "1":
				parseMenu();
				break;
			case "2":
				if (fm.containerSize() > 0) {
					searchMenu();
				} else {
					System.out.println("You must parse a file before processing.");
					System.out.println("Press any key to go back to main menu.");
					selection = textInput.next();
					if (selection != null) {
						mainmenu();
					}
				}
				break;
			case "3":
				if (fm.containerSize() > 0) {
					statsMenu();
				} else {
					System.out.println("You must parse a file before processing.");
					System.out.println("Press any key to go back to main menu.");
					selection = textInput.next();
					if (selection != null) {
						mainmenu();
					}
				}
				break;			
			case "4":
				helpMenu();
				break;
			case "5":
				quitMenu();
				break;		
			default:
				break;
			}
		} while (keepRunning);//program will keep running until quit selected
	}

	private void parseMenu() {
		System.out.println("--------- Parse Files -------");
		System.out.println("Menu");
		System.out.println("-----------------------------");
		System.out.println("1) Parse file or URL");
		System.out.println("2) Parse html file or URL (remove all html formatting)");
		System.out.println("Select option [1-2]: >");
		System.out.println("Select any other key to return to main menu: >");
		
		String selection = textInput.next();
		
		switch (selection) {//I have used switches instead of if else loops as they are easier to read in this context
		case "1":
			parseFile();
			break;
		case "2":
			parseHtmlFile();
			break;			
		default:
			break;
		}
	}
	
	private void parseFile() {
		System.out.println("Select file or URL to parse: >");
		String file = textInput.next();//get name of file
		
		System.out.println("Parsing file");//let user know what's happening
		
		try {
			fm.parse(file);
			Toolkit.getDefaultToolkit().beep();
			//some of these files take a looooooong time, this beeps when finished so you can do other things, watch cats on youtube etc.
			System.out.println("File parsed successfully, you may now search and edit");//confirm to client that they can go to next stage
		} catch (IOException ioe) {
			System.out.println("There was a problem with the connection, or the file could not be found");//tell user what happened
			ioe.printStackTrace();
		}	
	}
	
	private void parseHtmlFile() {
		System.out.println("Select html file or URL to parse: >");
		String html = textInput.next();
		
		System.out.println("Parsing file");
		
		try {
			fm.parseHtml(html);//call special html parsing method
			Toolkit.getDefaultToolkit().beep();//alert user
			System.out.println("Html file parsed successfully, you may now search and edit");//confirm to client that they can go to next stage
		} catch (IOException e) {
			System.out.println("There was a problem with the connection, or the file could not be found");//tell user what happened
			e.printStackTrace();
		}

	}
	
	private void searchMenu() {
		System.out.println("------- Search -------");
		System.out.println("Menu");
		System.out.println("----------------------");
		System.out.println("1) Find index positions of words");
		System.out.println("2) Replace words");
		System.out.println("3) Delete words");
		System.out.println("4) List all words");
		System.out.println("5) List all unique words");
		System.out.println("Select option [1-5]: >");
		System.out.println("Select any other key to return to main menu: >");
		
		String selection = textInput.next();
		
		switch (selection) {
		case "1":
			searchIndexMenu();
			break;
		case "2":
			replaceMenu();
			break;			
		case "3":
			deleteMenu();
			break;
		case "4":
			System.out.println("Warning, this make take a very long time,");
			System.out.println("Type 4 to confirm listing all words,");
			System.out.println("or any other key to return to main menu: >");
			String confirmAll = textInput.next();
			if (confirmAll.equals("4")) {//if the user is sure
				fm.listContents(true);//true means all words are to be searched for
			}
			break;
		case "5":
			System.out.println("Warning, this make take a very long time,");
			System.out.println("Type 5 to confirm listing all unique words,");
			System.out.println("or any other key to return to main menu: >");
			String confirmUnique = textInput.next();
			if (confirmUnique.equals("5")) {
				fm.listContents(false);//false means only unique words are to be searched for
			}
			break;
		default:
			break;
		}
	}

	private void searchIndexMenu(){
		System.out.println("------- Search Indices-------");
		System.out.println("Menu");
		System.out.println("-----------------------------");
		System.out.println("1) Find first index position of word");
		System.out.println("2) Find last index position of word");
		System.out.println("3) List all index positions of word");
		System.out.println("Select option [1-3]: >");
		System.out.println("Select any other key to return to main menu: >");
		
		String selection = textInput.next();
		String searchWord;
		
		switch (selection) {
		case "1":
			System.out.println("Please type the word to search for: >");
			searchWord = textInput.next();
			if (fm.contains(searchWord)) {//check that word in array
				System.out.println("The first index number of *" + searchWord +"* is " + fm.getFirstIndex(searchWord));
				}
			break;
		case "2":
			System.out.println("Please type the word to search for: >");
			searchWord = textInput.next();
			if (fm.contains(searchWord)) {
				System.out.println("The last index number of *" + searchWord +"* is " + fm.getLastIndex(searchWord));
				}
			break;
		case "3":
			System.out.println("Please type the word to search for: >");
			searchWord = textInput.next();
			if (fm.contains(searchWord)) {
				fm.getAllIndices(searchWord);
				}
		
			break;			

		default:
			break;
		}
		
	}
	private void countMenu() {
		System.out.println("-------- Count Words --------");
		System.out.println("Menu");
		System.out.println("-----------------------------");
		System.out.println("1) Count the total number of words");
		System.out.println("2) Count the total number of a particular word");
		System.out.println("3) Count all distinct words");
		System.out.println("Select option [1-3]: >");
		System.out.println("Select any other key to return to main menu: >");
		
		String selection = textInput.next();
		
		switch (selection) {
		case "1":
			System.out.println("Total number of words is " + fm.count());
			break;
		case "2":
			System.out.println("Please enter the word to count: >");
			String countWord = textInput.next();
			System.out.println("Total number of search word *" + countWord + "* is " + fm.countOccurrences(countWord));
			break;
		case "3":
			System.out.println("Total number of distinct words is " + fm.countDistinct());
			break;			
		default:
			break;
		}
	}
	
	private void replaceMenu() {
		System.out.println("------- Replace Words -------");
		System.out.println("Menu");
		System.out.println("-----------------------------");
		System.out.println("1) Replace all instances of a particular word");
		System.out.println("2) Replace a word at a particular index");
		System.out.println("Select option [1-2]: >");
		System.out.println("Select any other key to return to main menu: >");
		
		String selection = textInput.next();
		
		switch (selection) {
		case "1":
			System.out.println("Please enter the word to replace: >");
			String searchWord = textInput.next();
			System.out.println("Please enter the replacement word: >");
			String replaceWord = textInput.next();
			System.out.println("All instances of *" + searchWord + "* will be replaced with *" + replaceWord + "*,");//confirm replacement
			System.out.println("the result will look like gobbledegook.");
			System.out.println("You may look like an idiot to your friends.");
			System.out.println("Type r to confirm replacement,");
			System.out.println("or any other key to return to main menu: >");
			String confirmDeleteWord = textInput.next();
			if (confirmDeleteWord.equals("r")) {
				fm.replace(searchWord, replaceWord, true);//true means it is a word to be replaced
			}
			break;
		case "2":
			System.out.println("Please enter the index of the word to replace: >");
			String searchIndex = textInput.next();
			System.out.println("Please enter the replacement word: >");
			String replaceIndexWord = textInput.next();
			System.out.println("The word at index " + searchIndex + " will be replaced with *" + replaceIndexWord + "*, are you sure?");
			System.out.println("Type r to confirm replacement,");
			System.out.println("or any other key to return to main menu: >");
			String confirmDeleteIndex = textInput.next();
			if (confirmDeleteIndex.equals("r")) {
				fm.replace(searchIndex, replaceIndexWord, false);//false means it is an index position of a word to be replaced
			}
			break;
		default:
			break;
		}

	}
	private void deleteMenu() {
		System.out.println("-------- Delete Words -------");
		System.out.println("Menu");
		System.out.println("-----------------------------");
		System.out.println("1) Delete all instances of a particular word");
		System.out.println("2) Delete a word at a particular index");
		System.out.println("Select option [1-2]: >");
		System.out.println("Select any other key to return to main menu: >");
		
		String selection = textInput.next();
		
		switch (selection) {
		case "1":
			System.out.println("Please enter the word to delete: >");
			String deleteWord = textInput.next();
			System.out.println("All instances of *" + deleteWord + "* will be deleted, are you sure?");//confirm deletion
			System.out.println("This cannot be reversed");
			System.out.println("Type d to confirm deletion,");
			System.out.println("or any other key to return to main menu: >");
			String confirmDeleteWord = textInput.next();
			if (confirmDeleteWord.equals("d")) {
				fm.delete(deleteWord, true);//true means it is a word to be deleted
			}
			break;
		case "2":
			System.out.println("Please enter the index of the word to delete: >");
			String deleteIndex = textInput.next();
			System.out.println("The word at index " + deleteIndex + " will be deleted, are you sure?");
			System.out.println("This cannot be reversed");
			System.out.println("Type d to confirm deletion,");
			System.out.println("or any other key to return to main menu: >");
			String confirmDeleteIndex = textInput.next();
			if (confirmDeleteIndex.equals("d")) {
				fm.delete(deleteIndex, false);//false means it is an index position of a word to be deleted
			}
			break;
		default:
			break;
		}	
	}
	
	private void statsMenu() {
		System.out.println("-------- Statistics ---------");
		System.out.println("Menu");
		System.out.println("-----------------------------");
		System.out.println("1) Get size of container");
		System.out.println("2) Count words");
		System.out.println("Select option [1-2]");
		System.out.println("or other key to return to main menu: >");
		
		String selection = textInput.next();
		
		switch (selection) {
		case "1":
			int conSize = fm.containerSize();
			int wordSize = fm.count();
			System.out.println("Total size of container is " + conSize);
			System.out.println("Total number of words is " + wordSize);
			
			float conRatio = conSize / wordSize;
			System.out.println("Container is " + conRatio + " times the size of it's contents,");
			System.out.println("Would you like to optimise it?");
			System.out.println("Type 1 to optimise, any other key to return to the main menu: >");
			
			String optimise = textInput.next();
			if (optimise.equals("1")) {
				fm.optimise();
			}	
			break;
		case "2":
			countMenu();//goto count sub-menu
			break;	
		default:
			break;
		}
		
	}
	private void helpMenu() {
		System.out.println("----------- Help ------------");
		System.out.println("Menu");
		System.out.println("-----------------------------");
		System.out.println("1) Parsing help section");
		System.out.println("2) Search and Edit help section");
		System.out.println("3) Statistics help section");
		System.out.println("Select option [1-3]: >");
		System.out.println("or other key to return to main menu: >");
		
		String selection = textInput.next(); //Get the selected item	
		switch (selection) {
		case "1":
			parseHelpMenu();
			break;
		case "2":
			searchHelpMenu();
			break;
		case "3":
			statsHelpMenu();
			break;		
		default:
			break;
		}
	}
	private void quitMenu() {
		System.out.println("Are you sure you want to quit? Type 5 again if you are sure");
		System.out.println("or any other key to return to main menu: >");//double check
		String confirmation = textInput.next();
		if (confirmation.equals("5")) {
			keepRunning = false; //Set the loop control variable to false. The while clause will now evaluate to false, terminating the loop
			textInput.close();//close the scanner
		}
	}
	
	private void parseHelpMenu() {
		System.out.println("-------- Parsing Help -------");
		System.out.println("Menu");
		System.out.println("-----------------------------");
		System.out.println("1) Parsing a file or URL");
		System.out.println("2) Parsing a html file or URL (strip html tags)");
		System.out.println("Select option [1-2]: >");
		System.out.println("or other key to return to main menu: >");
		
		String selection = textInput.next(); //Get the selected item
		
		switch (selection) {
		case "1":
			helper.getHelpFile("helpFileParse.txt");//get the relevant help file based on input
			break;
		case "2":
			helper.getHelpFile("helpFileURLParse.txt");
			break;		
		default:
			break;
		}
	}
	private void searchHelpMenu() {
		System.out.println("--- Search and Edit Help ----");
		System.out.println("Menu");
		System.out.println("-----------------------------");
		System.out.println("1) Find index positions of words");
		System.out.println("2) Replace words");
		System.out.println("3) Delete words");
		System.out.println("4) List words");
		System.out.println("Select option [1-4]: >");
		System.out.println("or other key to return to main menu: >");
		
		String selection = textInput.next();
		
		switch (selection) {
		case "1":
			helper.getHelpFile("helpFileIndex.txt");
			break;
		case "2":
			helper.getHelpFile("helpFileReplace.txt");
			break;
		case "3":
			helper.getHelpFile("helpFileDelete.txt");
			break;
		case "4":
			helper.getHelpFile("helpFileList.txt");
			break;
		default:
			break;
		}
	}
	
	private void statsHelpMenu() {
		System.out.println("------ Statistics Help ------");
		System.out.println("Menu");
		System.out.println("-----------------------------");
		System.out.println("1) Container Help");
		System.out.println("2) Counting Help");
		System.out.println("Select option [1-2]: >");
		System.out.println("or other key to return to main menu: >");
		
		String selection = textInput.next();
		
		switch (selection) {
		case "1":
			helper.getHelpFile("helpFileContainer.txt");
			break;
		case "2":
			helper.getHelpFile("helpFileCount.txt");
			break;		
		default:
			break;
		}
	}
}
