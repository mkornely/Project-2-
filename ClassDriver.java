
// --== CS400 File Header Information ==--
// Name: Michael Kornely
// Email: mkornely@wisc.edu
// Team: BA
// Role: Front End
// TA: Brianna Cochran
// Lecturer: Florian
// Notes to Grader: Decided to remove the rename student function
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.io.*;
import java.nio.file.*;
import java.util.InputMismatchException;

public class ClassDriver {

  // runs the driver
  public static void main(String[] args) {

    run();

  }

  /**
   * Runs the driver
   * 
   * 
   */
  public static void run() {

    Scanner scan = new Scanner(System.in);
    RedBlackTree<Student> rbtree = new RedBlackTree<Student>();
    RosterEditor roster = new RosterEditor(rbtree);
    System.out.println("Welcome to the Student Class Roster ");
    boolean exit = false;
//keeps going till exit signal 
    while (!exit) {
      //inputs 
      System.out.println(" Enter {A} to add a student to the roster");
      System.out.println(" Enter {S} to search for a student in the roster");
      // System.out.println(" Enter {R} to rename a student in the roster");
      System.out.println(" Enter {G} to change the grade of a student in the roster");
      System.out.println(" Enter {P} to print the roster");
      System.out.println(" Enter {O} to output the roster to a text file");
      System.out.println(" Enter {C} to clear the roster");
      System.out.println(" Enter {I} to import the roster from a text file");
      System.out.println(
          "\n Don't know what to do???? Enter {H} to get examples on using the application");
      System.out.println(" Enter {Q} to quit application");

      String stringchoice = null;
      char choice = 'H';
      boolean selectionstring = false;
//makes sure char is not a empty string 
      while (!selectionstring) {
        System.out.print("\n Your Selection: ");
        stringchoice = scan.nextLine();

        if (stringchoice.length() == 0) {
          System.out.println("\n Please enter a valid character!");

        } else {
          choice = stringchoice.toLowerCase().charAt(0);
          selectionstring = true;
        }
      }
      System.out.println("__________________________________________________________ \n");

//picks a choice 
      switch (choice) {
        case 'a':
          String name = null;;
          boolean repeatname = false;
          System.out.println("Adding a new student...");
          //makes sure a name isnt repeated 
          while (!repeatname)
            try {
              System.out.println("Student name: ");
              name = scan.nextLine().trim().toLowerCase();
              roster.search(name);
              System.out.println("This name is already in the roster. Please try again!");

            } catch (NoSuchElementException e) {
              repeatname = true;
            }

          int id = 0;
          boolean correctidInput = false;
          //makes sure it is a valid id 
          while (!correctidInput) {
            try {
              System.out.println("Student ID: ");
              id = scan.nextInt();
              correctidInput = true;
            } catch (InputMismatchException e) {
              System.out.println("Invalid input. Enter a valid integer for the ID");
            }
          }


          double grade = 0.0;
          boolean correctgradeInput = false;
          //make sure its a valid grade
          while (!correctgradeInput) {
            try {
              System.out.print("Student grade: ");
              grade = scan.nextDouble();
              if (grade < 0.00 || grade > 100.00)
                throw new InputMismatchException();
              correctgradeInput = true;
            } catch (InputMismatchException e) {
              System.out.println(
                  "Invalid input. Enter a valid grade (can be any positive rational number between 0 and 100");

            }
          }


          rbtree.insert(new Student(name, id, grade));
          System.out.println("Student added.");
          break;


        case 's':
          System.out.println("Enter the name of a student to find");
          String studentname = scan.nextLine().toLowerCase();
          try {
            roster.search(studentname);
          } catch (NoSuchElementException e) {
            e.getMessage();
          }
          break;


        /*
         * case 'r':
         * System.out.println("Enter the name of a current student to change their name: "); String
         * oldname = scan.nextLine().trim();
         * 
         * try { roster.search(oldname.toLowerCase()); } catch (NoSuchElementException e) {
         * e.getMessage(); break; } System.out.println("Enter the new name for Student " + oldname);
         * String newname = scan.nextLine().trim();
         * 
         * try { roster.adjustName(oldname.toLowerCase(), newname.toLowerCase()); } catch
         * (NoSuchElementException e) {
         * 
         * System.out.println("Name not sucessfully changed"); break; }
         * System.out.println("Name sucessfully changed! Student " + oldname + " is now " +
         * newname);
         * 
         * break;
         */


        case 'g':
          System.out.println("Enter the name of a current student to change their grade: ");
          String gradename = scan.nextLine().trim();
          double oldgrade = 0.00;
//finds the grade if the student exists 
          try {
            oldgrade = roster.search(gradename.toLowerCase()).getGrade();

          } catch (NoSuchElementException e) {
            e.getMessage();
            break;
          }

          boolean correctinput = false;
          double newgrade = 0.00;
          //makes sure grade is correct and in the scope 
          while (!correctinput) {
            try {
              System.out.println("Enter the new grade for Student " + gradename);
              newgrade = scan.nextDouble();
              if (newgrade > 100.00 || newgrade < 0.00)
                throw new InputMismatchException();

              correctinput = true;
            } catch (InputMismatchException e) {
              System.out.println(
                  "Please try again. You must enter a number that is between 0 and 100. For example: 78.5, 98, 56.97");
            }

          }

          try {
            roster.adjustGrade(gradename, newgrade);
          } catch (NoSuchElementException e) {

            System.out.println("Grade was not sucessfully changed");
            break;
          }
          System.out.println("Grade was sucessfully changed! Student " + gradename
              + " old grade was " + oldgrade + " and is now " + newgrade);

          break;



        case 'p':
          try {
            System.out.println("The roster is as follows: " + rbtree.toString());
          } catch (NullPointerException e) {
            System.out.println("Roster is empty!");
          }
          break;
        case 'o':
          System.out.println("Enter the file name or the file path to be exported: ");
          String outputfile = scan.nextLine();
          if (!DataHandler.exportData(rbtree, outputfile)) {
            System.out.println("Unable to output the tree to the file.");
          }



        case 'c':
          roster.clear();
          System.out.println("Roster is cleared.");

          break;
        case 'i':
          System.out.println("Enter the file name or the file path: ");
          String filename = scan.nextLine();

          RedBlackTree<Student> importedtree = null;
          try {
            importedtree = DataHandler.importData(filename);
          } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            break;
          }

          if (importedtree == null) {
            System.out.println("Tree cannot be formed from data provided");
          } else {
            rbtree = importedtree;
            roster = new RosterEditor(rbtree);
          }

          break;


        case 'h':
          System.out.println("Add: Provide a valid name, integer ID, and grade to "
              + "add a new student to the roster. These will be prompted to you one by one "
              + "\n Example: Adam Rodgers, 9087861, 87.90");

          System.out.println("\nSearch: Provide the name of the student that you would like to "
              + "search for. If they are in the roster, their information will be printed.");

          // System.out.println(
          // "\nChange name: Provide the name of the student that you would like to rename; "
          // + "you will be prompted for the desired new name.");

          System.out.println(
              "\nChange grade: Provide the name of the student whose grade you would like to change; "
                  + "you will be prompted for the new grade.");

          System.out
              .println("\nPrint roster: Prints details of each student that is in the roster.");

          System.out.println("\nClear roster: Removes every student from the roster.");

          System.out.println("\nLoad file: Provide the name of a text file to load students into "
              + "the roster. The file must be in the same directory as this application."
              + "\n Example: roster.txt");

          System.out.println(
              "\nOutput file: Exports contents of the roster to a new text file; you will be prompted for the file name or path.");
          break;


        case 'q':
          System.out.println("Closing the interface.");
          exit = true;
          scan.close();
          break;

        default:
          System.out.println(
              "Invalid input. Only enter a specified letter (not case sensitive). Try again");
          break;


      }

      System.out.println("\n--Press Enter to continue--");
      scan.nextLine();

    }


    System.out.println("Bye!");


  }

}

