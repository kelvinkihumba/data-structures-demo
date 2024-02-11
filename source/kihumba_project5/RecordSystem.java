package kihumba_project5;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * A class that holds the main function and its functions
 * 
 * @author kihumba
 *
 */
public class RecordSystem {
	//scanner object to read keyboard input
	private static Scanner keyboardInput;
	//scanner object to read a file
    private static Scanner fileInput;
    //object to read and write to a random access file
    private static RandomAccessFile raf;
    //file object
    private static File  file;
    // hash table object
    private static Hashing <Integer, Integer> hashTable = new Hashing<> ();
    // variable indicating if index has been built
    private static boolean created = false;
    
    /**
     * Main method. This is the driver function that implements a small
     * database to manage a record system for students. It is capable of
     * adding records, deleting, displaying and modifying the records
     * 
     * @param args
     * @throws IOException
     */
	public static void main(String[] args) throws IOException {
		keyboardInput = new Scanner(System.in);
	
		// print project information
		myInfo();
		
	    while(true) {
			// print the menu
			printMenu();	
			
			try {
				//read keyboard input
				int selection = keyboardInput.nextInt();
				
				switch(selection) {
				  
				case 1:
					try{
						menuOption1(); break;
					}
					
					catch (FileNotFoundException ex) {
						break;
					}
				
				case 2: menuOption2(); break; 
				
				case 3: menuOption3(); break;
				
				case 4: menuOption4(); break;
				
				case 5: menuOption5(); break;
				
				case 6: menuOption6(); break;
				
				case 7: menuOption7(); break;
				
				case 8: menuOption8(); break;
				
				case 9: menuOption9(); break; 
				
				default: 
					System.out.println("Sorry, invalid choice!"); 
				    break;
			
				}
			}
			
			catch (InputMismatchException ex) {
				System.out.println("Non-numeric input! Please try again");
				
				//clear keyboard input
				keyboardInput.next();
			}
			
	    }		
		
	}
	
	
	/**
	 * display the header. It contains the name, course, project and
	 * due date.
	 * 
	 * @param none
	 * @return none
	 */
	public static void myInfo() {
		System.out.println("Programmer:\tKelvin Kihumba");
		System.out.println("Course:\t\tCOSC 311, F'22");
		System.out.println("Project:\t5");
		System.out.println("Due Date:\t12-06-22");
	}
	
	/**
	 * print the main menu
	 * 
	 * @param none
	 * @return none
	 */
	public static void printMenu() {
		System.out.println("\n\tMenu\n\t====");
		System.out.println("1: Make a random-access file");
		System.out.println("2: Display a random-access file");
		System.out.println("3: Build the index");
		System.out.println("4: Display the index");
		System.out.println("5: Retrieve a record");
		System.out.println("6: Modify a record");
		System.out.println("7: Add a record");
		System.out.println("8: Delete a record");
		System.out.println("9: Exit");
		System.out.print("\nEnter your choice:");
	}
	
	/**
	 * create a random access file (name from user) using content of a text 
	 * file specified by the user
	 * 
	 * @throws IOException
	 * @param none
	 * @return none
	 */
	public static void menuOption1() throws IOException {
		//ask the user for the input and output files
		System.out.print("Enter an input file name:");
        String input = keyboardInput.next();
        
        //create input file
        file = new File (input);
        //check if the file entered by the user exists
        if (!file.exists())
        	System.out.println(input +" (No such file or directory)");
        fileInput = new Scanner(file);
        
        System.out.print("Enter an output file name:");
        String output = keyboardInput.next();
        RecordSystem.file = new File (output);
        
        //create output file (read and write random access file)
        file = new File (output);
        raf = new RandomAccessFile(file, "rw");
        raf.setLength(0);
        //create a student object to transfer student details 
        Student student = new Student();
        while(fileInput.hasNext()) {
        	student.readFromTextFile(fileInput);
        	student.writeToFile(raf);
        }
        
        //close the file after writing
        raf.close();
        System.out.println("Random access file is built successfully!"); 
	}
	
	/**
	 * print the contents of random access file specified by the user
	 * 
	 * @throws IOException
	 * @param none
	 * @return none
	 */
	public static void menuOption2() throws IOException {
		//ask the user for the random access file
		System.out.print("Enter the random access file name:");
		String fileName = keyboardInput.next();
		
		//open random access file
		raf = new RandomAccessFile(fileName, "rw");
		//check if the file entered exixts
		if (raf.length() == 0) {
			System.out.println("Create the random access file first");
		}
		Student student = new Student(); 
		
		try {
    		raf.seek(0);//set the file pointer to the beginning
    		int i = 0;
    		boolean j = true;
    		boolean validInput = true;
    		
    		label:
	    	while (true) {
	    		student.readFromFile(raf); 
	    		
	    		//check if any file has been marked as deleted
	    		if (!student.getFirst().trim().equals("DELETED")) {	 
	    			System.out.println(student);
	    			i++;
	    		}
	        	//print the records 5 at a time	
	    	    if ((i%5 == 0) && (j == true) && (i != 0)) {

	    	      do {  
	    	    	System.out.print("\nEnter N (for next 5 records), A (for all remaining records), M(for main menu):");
	    	    	String choice = keyboardInput.next();
	    	    	choice = choice.toLowerCase();
	    	    	
	    	    	switch(choice) {
	    	    	
	    	    	case "n": 
	    	    		break;    //leave switch loop
	    	    		
	    	    	case "a": 
	    	    		j = false; //skip this 'if' loop in the future (print all remaining records)
	    	    		break;
	    	    		
	    	    	case "m": 
	    	    		break label; //exit while loop (return to the main method)
	    	    		
	    	    	default:
	    	    		//go to the beginning of the 'do...while' loop
	    	    		validInput = false;
	    	    		System.out.println("\nSorry, invalid input!");
	    	    		break;    		
	    	    	}
	    	    	
	    	       } while (validInput == false);
	    	    }
	    	}
    	}
		
		catch (IOException ex) {}
	}
	
	/**
	 * This method creates the index from the random access file.
	 * The index is put in a binary tree (key and address)
	 * @throws IOException
	 */
	public static void menuOption3() throws IOException{
		//check if the random access file has already been created
		if (file == null) {
			System.out.println("Create the random access file first!");
			return;
		}
		//ask the user for the file name
		System.out.print("Enter the random access file name:");
		String rafName = keyboardInput.next();
		File input = new File(rafName);//create the file object
		RecordSystem.file = new File (rafName);
		raf = new RandomAccessFile(rafName, "rw");
		if (raf.length() == 0) {
			System.out.println(rafName+" does not exist!");
		}
		//create the student object
		Student student = new Student(); 
		raf.seek(0);
		
		//get length of file
		long total = (raf.length())/92;
		total = (int)total;
		//build the index
		for (int i = 0; i<total; i++) {
			student.readFromFile(raf);
			if (!student.getFirst().trim().equals("DELETED")) {	
				int key = student.getID();
				hashTable.put(new Pair<>(key, i)); //Add ID and record number
			}
			else {
				i++;
			}
		}
		created = true;
		System.out.println("Index was built successfully!");
	}
	
	/**
	 * This method prints the index keys and addresses of the records
	 * The indices can be printed from the first one or from any record
	 * of the user's choice
	 * @param none
	 * @return none
	 */
	public static void menuOption4() {
		//check if the index has been built
		if (created == false) {
			System.out.println("Build the index first!");
			return;
		}
		int start = -1;
		int end = -1;
		//ask the user for the starting index
		System.out.print("Enter the starting index (0 - 36): ");
		String input = keyboardInput.nextLine(); //read keyboard input
		input = keyboardInput.nextLine();
		try {
			if (!input.equals("")) {	
			    start = Integer.parseInt(input);
			    if (start == -1) start=-2;
			}    
		}
		catch (NumberFormatException e) {
			System.out.println("Non-numeric input!");
			return;
		}
		//ask the user for the ending index
		System.out.print("Enter the ending index (0 - 36): ");
		input = keyboardInput.nextLine();
		try {
			if (!input.equals(""))	
			    end = Integer.parseInt(input);
		}
		catch (NumberFormatException e) {
			System.out.println("Non-numeric input!");
			return;
		}
		
		if (start != -1 && end != -1) {
			hashTable.printLevels(start, end);//print the index level by level
		}
		else if (start == -1 || end == -1)
			hashTable.printLevels(0, 36);
		//check for invalid range of index
		else if (start < -1 || start > end || end > 36) {
			System.out.println("Index range is out of bounds!");
			return;
		}
		else hashTable.printLevels(0, 36);
	}
	
	/**
	 * This method retrieves a record from the random access file
	 * using an input (ID) from the user
	 * @throws IOException
	 * @param none
	 * @return none
	 */
	public static void menuOption5() throws IOException {
		//check if index has been built
		if (created == false) {
			System.out.println("Build the index first!");
			return;
		}
		//ask the user for the ID
		System.out.print("Enter a student ID: ");
		int input = keyboardInput.nextInt();
		//look for the requested ID in the hash table
		Pair<Integer,Integer> query = hashTable.find(new Pair<>(input,0));
		//if not found
		if (query == null) {
			System.out.println(input+" is an invalid ID");
			return;
		}
		//if found
		int address = query.getValue();
		raf = new RandomAccessFile(file, "rw");
		raf.seek(0);
		raf.seek(address*92);
		//create a student object
		Student student = new Student();
        student.readFromFile(raf);
		//print record
		System.out.println(student);
	}
	
	/**
	 * This method modifies a record of the user's choice
	 * The record is also changed in the random access file
	 * @throws IOException
	 * @param none
	 * @return none
	 */
	public static void menuOption6() throws IOException {
		//check if index has been built
		if (created == false) {
			System.out.println("Build the index first!");
			return;
		}
		//ask the user for a student ID
		System.out.print("Enter a student ID: ");
		int input = keyboardInput.nextInt();
		//look for the requested ID in the hash table
		Pair<Integer,Integer> query = hashTable.find(new Pair<>(input,0));
		if (query == null) {
			System.out.println(input+" is an invalid ID");
			return;
		}
		int address = query.getValue(); // get the index
		raf = new RandomAccessFile(file, "rw");
		raf.seek(0);
		raf.seek(address*92);
		//create a student object
		Student student = new Student();
        student.readFromFile(raf);
		//print record
		System.out.println(student);
		
		 int check = 0;
	        do {//ask the user for the fields to be modified
		        System.out.println("\n1: Change the first name");
				System.out.println("2: Change the last name");
				System.out.println("3: Change the GPA");
				System.out.println("4: Done");
				System.out.print("Enter your choice: ");
				
				int choice = keyboardInput.nextInt();
				check = choice;
			
				switch (choice) {
				
				case 1:
					// change first name
					System.out.print("Enter the first name:");
					String firstName = keyboardInput.next();
					student.setFirst(firstName);
					break;
					
				case 2: 
					// change second name
					System.out.print("Enter the last name:");
					String lastName = keyboardInput.next();
					student.setLast(lastName);
					break;
					
				case 3: 
					// change the GPA
					System.out.print("Enter the GPA:");
					double gpa = keyboardInput.nextDouble();
					student.setGPA(gpa);
					break;
					
				case 4:
					// make the changes
					raf.seek(0);
					raf.seek(address*92);
					student.writeToFile(raf);//make the changes 
					raf.close();
		
			    }
			} while (check != 4);
	}
	
	/**
	 * This method adds a new record to both the binary tree
	 * and the random access file
	 * 
	 * @throws IOException
	 * @param none
	 * @return none
	 */
	public static void menuOption7() throws IOException {
		// check if the index has been built
		if (created == false) {
			System.out.println("Build the index first!");
			return;
		}
		// create new random access file 
		raf = new RandomAccessFile(file, "rw");
		raf.seek(0);
		int fileLength = (int) raf.length();
		raf.seek(fileLength);
		Student student = new Student();
		//ask for all the fields to create a record
		System.out.print("Enter the first name:");
		String firstName = keyboardInput.next();
		student.setFirst(firstName);
		
		System.out.print("Enter the last name:");
		String lastName = keyboardInput.next();
		student.setLast(lastName);
		
		System.out.print("Enter the ID:");
		int id = keyboardInput.nextInt();
		student.setID(id);
		
		System.out.print("Enter the GPA:");
		double gpa = keyboardInput.nextDouble();
		student.setGPA(gpa);
		
		// check if the ID already exists
		if (hashTable.find(new Pair(id,0)) != null) {
			System.out.println("Duplicate ID! Please try again.");
			return;
		}
		student.writeToFile(raf);//add the record
		System.out.println("Record added!");
		raf.close();//close the random access file to make changes
		
		//open the random access file
		raf = new RandomAccessFile(file, "rw");
		raf.seek(0);
		long total = (raf.length())/92;
		total = (int)total;
		for (int i = 0; i<total; i++) {
			student.readFromFile(raf);
			//ignore 'deleted' records
			if (!student.getFirst().trim().equals("DELETED")) {	
				int key = student.getID();
				hashTable.put(new Pair<>(key, i)); //Add ID and record number
			}
			else {
				i++;
			}
		}
	}
	
	/**
	 * This method deletes any record of the user's choice
	 * @param none
	 * @return none
	 * @throws IOException
	 */
	public static void menuOption8() throws IOException {
		// check if the index has been built
		if (created == false) {
			System.out.println("Build the index first!");
			return;
		}
		// ask the user for a student ID
		System.out.print("Enter a student ID: ");
		int input = keyboardInput.nextInt();
		// look for the ID in the hash table
		Pair<Integer,Integer> query = hashTable.find(new Pair<>(input,0));
		// if it doesn't exist
		if (query == null) {
			System.out.println(input+" is an invalid ID");
			return;
		}
		int address = query.getValue();// get the index
		raf = new RandomAccessFile(file, "rw");
		raf.seek(0);
		raf.seek(address*92);
		//create a student object
		Student student = new Student();
        student.readFromFile(raf);
        student.setFirst("DELETED");// lazy delete the record
        raf.seek(0);
		raf.seek(address*92);
		student.writeToFile(raf);
		raf.close();
		hashTable.delete(query); // remove the record from the hash table
		System.out.println("Record has been deleted");
	}
	
	/**
	 * this method prints all the pairs before exiting the program
	 */
	public static void menuOption9() {
		hashTable.printLevels2(0, 36);
		System.out.println();
		System.out.println("Exiting the database management system");
		System.exit(0); //close program
	}

}
