package gmit;

import java.io.*;

import org.jsoup.*;
import org.jsoup.nodes.Document;
/**
 * Acts as a container for the Strings created from the input file or URL and provides methods for searching, deleting and replacing Strings.
 * @author Malcolm O'Beirn
 * @version 1.0 Jan 7 2014
 *
 */
public class FileManager {
	private int InitialSize;//This is set to the imported file size divided by 5 to give the approximate number of words
	private String[] words = new String[InitialSize];//String array to hold words from file
	/**
	 * This takes in new local files or URLs, removes all non letter characters and splits the resulting String into a container of words
	 * @param file
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void parse(String file) throws IOException, FileNotFoundException {//file may not be found
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;//each line read from the file
			String lines = null;//all the lines added together
			StringBuilder sb = new StringBuilder();
			InitialSize = (int)((file.length())/5);//set array length to approx number of words
			
			while ((line = br.readLine())!=null) {//Iterate over each line in the buffer until it is empty
				sb.append(line);//use the StringBuilder to add each line
				lines = sb.toString();//convert to a String
				
				/*strip leading and trailing whitespace, then use a regex to remove all characters which are not letters or spaces, 
				 *then change to lower case, divide into individual words and add to String array*/
			}
			words = lines.trim().replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
			br.close();//keep things tidy
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**This takes in new local html files or URLs, removes all html formatting, removes all non letter characters and splits the resulting String into a container of words
	 * @param html A html file
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void parseHtml(String html) throws IOException, FileNotFoundException {//URL may not be found, file may not be found
		Document doc;
		try {
			doc = Jsoup.connect(html).get();//use Jsoup.connect to create connection and .get to parse the file, stripping html
			String text = doc.text();//convert to string
			words = text.trim().replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
			/*strip leading and trailing whitespace, then use a regex to remove all characters which are not letters or spaces, 
			 *then change to lower case, divide into individual words and add to String array, as above*/
			
		} catch (IOException ioe) {//also catches FileNotFound
			ioe.printStackTrace();
		}
	}
	private void resize(){
		String[] temp = new String[(int)(words.length * 1.5)]; //Create a new array one and a half times the size of the existing array
		
		for (int i=0; i<words.length; i++){ //Iterate over the array
			temp[i] = words[i]; //Copy each element from the old array to the new array
		}
		
		words = temp; //Replace the old array with the new array
	}
	/**
	 * Checks if the search word is contained within the results.
	 * @param word The word to be searched for.
	 * @return True if found, False if not found.
	 */
	public boolean contains(String word){
		if (this.getFirstIndex(word) >= 0) {//if the index is in the array. ie not -1
			return true;
		}
		return false;
	}
	/**
	 * Gets the first index position of the search word.
	 * @param word The word to be searched for.
	 * @return Index position of first occurrence of search word.
	 */
	public int getFirstIndex(String word){ //Return the first index position of a search word in the array
		//System.out.println(words.length);//just a check
		for (int i = 0; i < words.length; i++){ //Loop over the array
			//System.out.println(words[i]);//check that array contains words
			if (words[i].equals(word)){ //If the array slot matches the search word
				return i; //Return the index of the search word in the array
			}
		}
		return -1;//if word not found
		}
	
	/**
	 * Gets the first index position of the search word.
	 * @param word The word to be searched for.
	 * @return Index position of last occurrence of search word. Returns -1 if word not found.
	 */
	public int getLastIndex(String word){ //Return the last index position of a search word in the array
		for (int i = words.length - 1; i >= 0; i--){ //Loop over the array backwards
			//System.out.println(words[i]);//check that array contains words
			if (words[i].equals(word)){ //If the array slot matches the search word
				return i; //Return the index of the search word in the array
			}
		}
		return -1;//if word not found
		}
/**
 * Gets all indexes of the search word.
 * @param searchWord The word to be searched for.
 */
	public void  getAllIndices(String searchWord){
		int[] result = new int[words.length];//set the int array equal in length to words String[]
		
		for (int j = 0; j < words.length; j++) {//loop over words array
			if ((words[j].equals(searchWord))){//if search word found
				result[j] = j;//set int in int[] result to that number
				System.out.println("Word *" + searchWord + "* found at index " + j);
				/*tried to get this to return an int[] but couldn't process it
				 *properly in Runner
				 */
				}
			}
		}
	
	/**
	 * Lists all words or only unique words
	 * @param isAllContents True if all words to be searched for, false if only unique words
	 */
	public void listContents(boolean isAllContents) {//true if all contents, false if only unique contents
		
		if (isAllContents) {
			for (int i = 0; i < words.length; i++) {
				System.out.println("Index " + i + ": " + words[i]);
			}
		} else {
			for (int j = 0; j < words.length; j++){   
			    String thisWord = words[j];//Get the next word to check
			    boolean seenThisBefore = false;
			    //Check if we've seen it before (by checking all array indexes below j)
			    for (int i = 0; i < j; i++){
			        if (words[i].equals(thisWord)){
			            seenThisBefore = true;//if word already in array
			        }
			    }
			    if (!seenThisBefore){
			        System.out.println(words[j]);//If we have not seen the word before, print out the word
			    }
			}
		}
	}
	
	/**
	 * Gets the total size of the container.
	 * @return Returns total size of the container.
	 */
	public int containerSize(){
		return words.length;
	}
	/**
	 * Gets the number of words in the container, not the container size!
	 * @return The number of words in the container.
	 */
	public int count(){
		int counter = 0; //Use a variable called counter to store the number of words
		
		for (int i = 0; i < words.length; i++){ //Loop over the array
			if (words[i] != null) counter++; //If an index position is not null, increment the counter
		}
		return counter;//Return the total number of words
	}
	/**
	 * Gets the number of words in the container matching the search word
	 * @param searchWord The word to be searched for.
	 * @return The number of words in the container matching the search word
	 */
	public int countOccurrences(String searchWord){
		int counter = 0;
		for (int i = 0; i < words.length; i++) {
			if (words[i] != null && (words[i].equals(searchWord))){
				counter++;//increment each time search word is found
			}
		}
		return counter;//Return the total number of the search word
	}
	/**
	 * Gets the number of distinct words in the container
	 * @return The number of distinct words in the container
	 */
	public int countDistinct() {
		/*This is very inefficient and I found a much faster way using hashmaps,
		 * but you wanted container based solutions so be prepared to wait :)
		 */
		int distinctWords = 0;//this will increment by 1 every time a new word is found

		for (int j = 0; j < words.length; j++){   
		    String thisWord = words[j];//Get the next word to check
		    boolean seenThisBefore = false;
		    //Check if we've seen it before (by checking all array indexes below j)
		    for (int i = 0; i < j; i++){
		        if (words[i].equals(thisWord)){
		            seenThisBefore = true;//if word already in array
		        }
		    }
		    if (!seenThisBefore){
		        distinctWords++;//If we have not seen the word before, increment the running total of distinct integers
		    }
		}
		return distinctWords;
	}

	/**
	 * Deletes a specified word or a word at a specified index
	 * @param searchWord The word to be deleted or the index number
	 * @param deleteIsWord True if it is a word to be deleted, false if it is an index
	 */
	public void delete(String searchWord, boolean deleteIsWord){//boolean deleteIsWord true if word, false if index
		if (deleteIsWord) {//if search item is a word
			for (int i = 0; i < words.length; i++) {//loop over array deleting words that match search word
			if (words[i].equals(searchWord)) {
				words[i] = null;
					}
				}
			} else {// if search item is an index
				int searchIndex = Integer.parseInt(searchWord);//to get index if not a word
				words[searchIndex] = null;//straight to index, no need for loop
			}
		}
	/**
	 * Replaces a specified word or a word at a specified index
	 * @param searchWord The word to be replaced or the index number
	 * @param replaceWord the replacement word.
	 * @param searchIsWord True if it is a word to be replaced, false if it is an index
	 */
	public void replace(String searchWord, String replaceWord, boolean searchIsWord){//boolean searchIsWord true if word, false if index
		if (searchIsWord){//if search item is a word
			for (int i = 0; i < words.length; i++) {//loop over array deleting words that match search word
			if (words[i].equals(searchWord)) {
				words[i].replaceAll(searchWord, replaceWord);//similarly we could replace just the first instance with words[i].replaceFirst(searchWord, replaceWord);
					}
				}
			} else {// if search item is an index
				int searchIndex = Integer.parseInt(searchWord);//to get index if not a word
				words[searchIndex] = replaceWord;//straight to index, no need for loop
			}
		}
	/**
	 * Reduces container size to exact number of words.
	 */
	public void optimise() {
		String[] temp = new String[this.count()];//temporary String array the size of the amount of words in the original array
		for (int i = 0; i < words.length; i++) {//iterate over the original array
			if (words[i] != null) {//if there is something at that position
				temp[i] = words[i];//Copy each element from the old array to the new array
			}
		}
		words = temp;//Replace the old array with the new array
	}

}
