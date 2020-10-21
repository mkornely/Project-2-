// --== CS400 File Header Information ==--
// Name: Ryan Szymanski
// Email: rpszymanski@wisc.edu
// Team: BA
// Role: Test Engineer 1
// TA: Brianna Cochran
// Lecturer: Florian
// Notes to Grader: None
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.File;

class TestRBTFunctionality {
  /**
   * Tests the basic functionality of the student class and checks if the information is correctly
   * stored
   */
  @Test
  void testStudentClass() {
    Student a = new Student("Peter", 9080, 92.38); //Student 1
    Student b = new Student("Paul", 9081, 90.00); //Student 2
    Student c = new Student("Mary", 9082, 95.44); //Student 3
    Student d = new Student("John", 9083, 87.60); //Student 4 
    Student e = new Student("George", 9084, 82.33); // Student 5

    // This whole sections checks if the student class is working properly and will fail if it is
    // not
    if (!a.getStudentName().equals("Peter") || a.getStudentId() != 9080 || a.getGrade() != 92.38) {
      fail("Student a was not correctly inserted");
    }
    if (!b.getStudentName().equals("Paul") || b.getStudentId() != 9081 || b.getGrade() != 90.00) {
      fail("Student b was not correctly inserted");
    }
    if (!c.getStudentName().equals("Mary") || c.getStudentId() != 9082 || c.getGrade() != 95.44) {
      fail("Student c was not correctly inserted");
    }
    if (!d.getStudentName().equals("John") || d.getStudentId() != 9083 || d.getGrade() != 87.60) {
      fail("Student d was not correctly inserted");
    }
    if (!e.getStudentName().equals("George") || e.getStudentId() != 9084 || e.getGrade() != 82.33) {
      fail("Student e was not correctly inserted");
    }
  }

  /**
   * This tests the DataHandler class and checks if this class correctly makes a new RBT of students
   * in a class
   */
  @Test
  void testDataHandlerClass() {
    
    File file = new File("./sampleclass.txt"); // Makes new file
    if (!file.exists()) {
      fail("WARNING: THIS FILE DOES NOT EXIST"); //No file was found
    } 
    String expectedOutput = // The output that should be the result of the RBT made by the DataHandler Class
"[Name: Lebron James, ID: 23, Grade: 91.21, Name: Bill Gates, ID: 1955, Grade: 75.5, Name: Taylor Swift, ID: 22, Grade: 88.34, Name: Aaron Rodgers, ID: 12, Grade: 100.0," + ""
    + " Name: Ellen DeGeneres, ID: 1958, Grade: 55.8, Name: Randy Marsh, ID: 1997, Grade: 78.23, Name: Will Smith, ID: 1968, Grade: 83.0, Name: Betty White, ID: 1922, Grade: 69.69," + ""
        + " Name: Chuck Norris, ID: 1940, Grade: 99.99, Name: Snoop Dogg, ID: 1971, Grade: 85.67]";
    String actualOutput = (DataHandler.importData("./sampleclass.txt").toString());
    if (!expectedOutput.contentEquals(actualOutput)) {
      fail("Error: This class did not properly add the student roster");
    }
  }

  /**
   * this test tests the functionality of the Roster Editer class and checks if it is working
   * properly
   */
  @Test
  void testRosterEditer() {
    RedBlackTree<Student> roster = new RedBlackTree<Student>();
    roster.insert(new Student("Peter", 9080, 92.38)); // creates a small roster of students in the
                                                      // RBT
    roster.insert(new Student("Paul", 9081, 90.00));
    roster.insert(new Student("Mary", 9082, 95.44));
    roster.insert(new Student("John", 9083, 87.60));
    roster.insert(new Student("George", 9084, 82.33));
    RosterEditer testEditer = new RosterEditer(roster); // new test editer
    String actualOutput = testEditer.printStudents(); // tests the print students method
    String expectedOutput =
        "[Name: Paul, ID: 9081, Grade: 90.0, Name: John, ID: 9083, Grade: 87.6, Name: Peter, ID: 9080, Grade: 92.38, Name: George, ID: 9084, Grade: 82.33, Name: Mary, ID: 9082, Grade: 95.44]";
    if (!actualOutput.equals(expectedOutput)) {
      fail("THE ROSTEREDITER DID NOT CORRECTLY PRINT THE ROSTER!");
    }
    testEditer.adjustGrade("Peter", 95.00); // tests the change grade method
    Student testGradeChange = testEditer.search("Peter");
    if (testGradeChange.getGrade() != 95.00) {
      fail("THE GRADE WAS NOT SUCCESSFULLY CHANGED!");
    }

    testEditer.clear(); // tests if the rosterEditer correctly empties the RBT
    if (roster.root != null) {
      fail("THE TREE DID NOT CLEAR PROPERLY");
    }
  }
}
