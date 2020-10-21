// --== CS400 File Header Information ==--
// Name: Ian Koh
// Email: iskoh@wisc.edu
// Team: BA
// Role: Front End Developer 2
// TA: Brianna Cochran
// Lecturer: Florian Heimerl
// Notes to Grader: None

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class RosterDriver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("Initiating student roster driver.");
		
		RedBlackTree<Student> roster = new RedBlackTree<>();
		RosterEditer editer = new RosterEditer(roster);
		Scanner sc = new Scanner(System.in);
		
		char input = 0;
		while (input != 'q') {
			
			System.out.print("\n");
			input = userPrompt(sc);
			
			switch (input) {
				case 'a':
					addStudent(sc, roster);
					break;
				case 's':
					searchStudent(sc, editer);
					break;
				case 'g':
					adjustGrade(sc, editer);
					break;
				case 'p':
					try {
						System.out.println(roster.toString());
					} catch (NullPointerException e) {
						System.out.println("Roster empty");
					}
					break;
				case 'c':
					editer.clear();
					break;
				case 'l':
					RedBlackTree<Student> newTree = loadFile(sc);
					
					if (newTree == null) {
						System.out.println("Unable to load file.");
					} else {
						roster = newTree;
						editer = new RosterEditer(roster);
					}
					
					break;
				case 'w':
					writeFile(roster, sc);
					break;
				case 'h':
					help();
					break;
				case 'q':
					System.out.println("Terminating application.");
					break;
				default:
					System.out.println("Unknown character. Please try again.");
					break;
			}
			
			System.out.println("\n--Press Enter to continue--");
			sc.nextLine();
			
		}
	}
	
	/**
	 * Helper method to prompt and retrieve user input at main menu
	 * @param sc
	 * @return char user input
	 */
	private static char userPrompt(Scanner sc) {
		System.out.println("Please select an option below by entering the associated character (not case-sensitive):"
				+ "\n\t[A] Add new student to roster"
				+ "\n\t[S] Search for student in roster"
				+ "\n\t[G] Adjust existing student's grade"
				+ "\n\t[P] Print roster"
				+ "\n\t[C] Clear roster"
				+ "\n\t[L] Load student(s) from .txt file"
				+ "\n\t[W] Write roster to .txt file"
				+ "\n\t[H] Help"
				+ "\n\t[Q] Quit driver");
		
		System.out.print("\nInput: ");
		char i = sc.nextLine().toLowerCase().charAt(0);
		System.out.print("\n");
		
		return i;
	}
	
	/**
	 * Helper method to prompt the target student's name to be used in methods such as 
	 * searchStudent(), renameStudent(), etc.
	 * @param sc
	 * @return name Target Student's name on which to perform operations
	 */
	private static String promptTargetName(Scanner sc) {
		System.out.print("Target student's name: ");
		String name = sc.nextLine().trim();
		System.out.print("\n");
		return name;
	}

	/**
	 * Helper method to prompt Student data and insert new Student into roster
	 * @param sc
	 * @param roster RedBlackTree being used as roster
	 */
	private static void addStudent(Scanner sc, RedBlackTree<Student> roster) {
		System.out.print("Student name: ");
		String name = sc.nextLine().trim();
		
		System.out.print("\nStudent ID: ");
		int id;
		try {
			id = Integer.parseInt(sc.nextLine().trim());
		} catch (InputMismatchException e) {
			System.out.println("Invalid input. Please try again and provide a valid integer");
			return;
		}
		
		System.out.print("\nStudent grade: ");
		double grade;
		try {
			grade = Double.parseDouble(sc.nextLine().trim());
		} catch (InputMismatchException e) {
			System.out.println("\nInvalid input. Please try again and provide a valid double");
			return;
		}
		System.out.print("\n");
		
		try {
			roster.insert(new Student(name, id, grade));
			System.out.println("Student added.");
		} catch (IllegalArgumentException e) {
			System.out.println("Student already in database.");
		}
	}

	/**
	 * Helper method to prompt name of the desired Student
	 * @param sc
	 * @param editer Current RosterEditer used by driver
	 */
	private static void searchStudent(Scanner sc, RosterEditer editer) {
		
		try {
			editer.search(promptTargetName(sc));
		} catch (NoSuchElementException e) {
			System.out.print(e.getMessage());
			return;
		}
		
	}
 	
	/**
	 * Helper method to regrade Student. Prompts the target student's name and calls editer's
	 * adjustGrade() method.
	 * @param sc
	 * @param editer
	 */
	private static void adjustGrade(Scanner sc, RosterEditer editer) {
		String name = promptTargetName(sc);
		
		System.out.print("New grade: ");
		double newGrade = Double.parseDouble(sc.nextLine().trim());
		System.out.print("\n");
		
		try {
			System.out.println("Old: ");
			editer.adjustGrade(name, newGrade);
			
			System.out.println("\nNew: ");
			editer.search(name);
			System.out.print("\n");
		} catch (NoSuchElementException e) {
			System.out.println("\n" + e.getMessage());
		}
	}

	/**
	 * Helper method to prompt and load .txt file into DataHandler's importData() method.
	 * @param sc
	 * @param handler
	 * @return newTree new RedBlackTree returned by importData()
	 */
	private static RedBlackTree<Student> loadFile(Scanner sc) {
		System.out.print("File name: ");
		String filename = sc.nextLine().trim();
		System.out.print("\n");
		
		RedBlackTree<Student> newTree = null;
		try {
			newTree = DataHandler.importData(filename);
		} catch (Exception e) {
			System.out.println("Error: \n" + e.getMessage());
		}
		
		return newTree;
	}
	
	/**
	 * Helper method to write contents of roster to a desired file name.
	 * @param roster
	 * @param sc
	 */
	private static void writeFile(RedBlackTree<Student> roster, Scanner sc) {
		System.out.print("File name: ");
		String filename = sc.nextLine().trim();
		System.out.print("\n");
		
		if (!DataHandler.exportData(roster, filename)) {
			System.out.println("Unable to write file.");
		}
	}
	
	/**
	 * Helper method to print help screen
	 */
	private static void help() {
		System.out.println(
				"Add: Provide a valid name, integer ID (between 0 and 2000000000), and percentage grade to "
				+ "add a new student to the roster. (you will be prompted for each)"
				+ "\n\tex: John Smith, 908165, 90.56"
						
				+ "\n\nSearch: Provide the name of the student that you would like to "
				+ "search for. If they are in the roster, their information will be printed."
				
				+ "\n\nChange name: Provide the name of the student that you would like to rename; "
				+ "you will be prompted for the desired new name."
				
				+ "\n\nChange grade: Provide the name of the student whose grade you would like to change; " 
				+ "you will be prompted for the new grade."

				+ "\n\nPrint roster: Prints details of each student that is in the roster."

				+ "\n\nClear roster: Removes every student from the roster."

				+ "\n\nLoad file: Provide the name of a .txt file from which you would like to load students into "
				+ "the roster. The file must be in the same directory as this application."
				+ "\n\tex: example.txt"

				+ "\n\nWrite file: Saves contents of the roster to a new .txt file; you will be prompted for the file name.");
				
	}
}
