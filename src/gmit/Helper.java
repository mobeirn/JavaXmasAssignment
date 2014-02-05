package gmit;

import java.io.*;

/**Manages help files for the help menus
 * 
 * @author Malcolm O'Beirn
 * @version 1.0 Jan 07, 2014
 */
public class Helper {//manages help files for the help menu
	/**Gets a helpFile text file and reads it using readHelp
	 * 
	 * @param helpFile
	 */
	public void getHelpFile(String helpFile) {//2 methods as I did not want try to have to put the get menu in runner in a try catch block
		try {
			readHelp(helpFile);//calls method below
		} catch (FileNotFoundException e) {
			System.out.println("Could not find file.");
			e.printStackTrace();
		} catch (IOException e) {//if using web based files, must be caught anyway
			System.out.println("Problem with the connection.");
			e.printStackTrace();
		}
	}
	/**Reads a helpFile text file one line at a time into the program
	 * 
	 * @param helpFile
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void readHelp(String helpFile) throws IOException, FileNotFoundException {//file may not be found
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(helpFile)));
			//get the help file, can also use a URL for online help
			String line = null;//each line read from the file
			while ((line = br.readLine())!=null) {//Iterate over each line in the buffer until it is empty
				System.out.println(line);//print out each line of the help file
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		br.close();//tidy up
	}

}
