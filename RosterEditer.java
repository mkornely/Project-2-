import java.util.NoSuchElementException;

// --== CS400 File Header Information ==--
// Name: Elaina Timmerman
// Email: eltimmerman@wisc.edu
// Team: BA
// Role: back end developer
// TA: Brianna Cochran
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>


// search, adjust grade adjust name and print all
public class RosterEditer {
  RedBlackTree<Student> roster = new RedBlackTree();


  /**
   * constructor for RosterEditor
   * 
   * @param roster
   */
  public RosterEditer(RedBlackTree roster) {
    this.roster = roster;
  }

  /**
   * searches through roster, using a helper, to find student
   * 
   * @param studentName
   * @return student object
   */
  public Student search(String studentName) {
    Student student = new Student(studentName, 0, 0.0);

    return searchHelper(student, roster.root);
  }

  /**
   * search helper method to traverse through RedBlackTree
   * 
   * @param studentName
   * @param current
   * @return Student object
   */
  public Student searchHelper(Student student, RedBlackTree.Node<Student> current) {
    if (current == null) {
      throw new NoSuchElementException("Student cannot be found");
    }
    if (student.compareTo(current.data) == 0) {
      return current.data;
    } else if(student.compareTo(current.data) < 0) {
      return searchHelper(student, current.leftChild);
    } else if (student.compareTo(current.data) > 0) {
      return searchHelper(student, current.rightChild);
    } else {
      throw new NoSuchElementException("Student cannot be found");
    }
  }

  /**
   * searches to find student and change their grade
   * 
   * @param studentName
   * @param newGrade
   */
  public void adjustGrade(String studentName, Double newGrade) {
    search(studentName).setGrade(newGrade);
  }

  /**
   * prints the roster
   * 
   * @return a string of the roster
   */
  public String printAll() {
    return roster.toString();
  }

  /**
   * clears the roster
   */
  public void clear() {
    roster.root = null;
  }
}
