// --== CS400 File Header Information ==--
// Name: Alexander Baraban
// Email: baraban@wisc.edu
// Team: BA
// Role: Test Engineer 2
// TA: Bri Cochran
// Lecturer: Dahl
// Notes to Grader: 
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
public class Atest {
	
	/*
	 * My student toString Method:
	 * 		//"Name: " + this.studentName + ", ID: " + this.studentId + ", Grade: " + this.grade + "\n"
	 */
	
	/*
	 * This method checks if the student's fields are correct by using the getters
	 * 
	 */
	@Test
	void testStudent()
	{
		Student s = new Student("beebs", 1, 85.0); // creates new student, name is beebs, id# is 1, grade is 85.0
		
		if(!s.getStudentName().equals("beebs")) // checks if the name, id and grade are equal to what was passed as a parameter
		{
			fail("beebs name is incorrect");
		}
		if(s.getStudentId() != 1)
		{
			fail("beebs student ID is incorrect");
		}
		if(s.getGrade() != 85.0)
		{
			fail("beebs grade is incorrect");
		}
		
		Student s1 = new Student("michael", 50, 90.0);
		if(!s.getStudentName().equals("beebs") || !s1.getStudentName().equals("michael"))
		{
			fail("one of the students names are incorrect");
		}
		if(s.getStudentId() != 1 || s1.getStudentId() != 50)
		{
			fail("one of the students IDs are incorrect");
		}
		if(s.getGrade() != 85.0 || s1.getGrade() != 90.0)
		{
			fail("one of the students grade is incorrect");
		}

	}
	
	/*
	 * tests the "clear" method of roster Editer
	 */
	@Test
	void testRosterEditerClear()
	{
		Student s = new Student("beebs", 1, 85.0);
		Student s1 = new Student("michael", 2, 90.0);
		Student s2 = new Student("alex", 3, 1.0);
		Student s3 = new Student("ayo gotti", 4, 2.0);
		RedBlackTree<Student> roster=new RedBlackTree<Student>();
		roster.insert(s);
		roster.insert(s1);
		roster.insert(s2);
		roster.insert(s3);
		RosterEditer r = new RosterEditer(roster);
		r.clear();
		if(r.roster.root != null)
		{
			fail("The clear method is not working as expected");
		}
		
	}
	
	/*
	 * tests if the search method for rosterEditer works
	 */
	@Test
	void testRosterEditerSearch()
	{
		Student s = new Student("beebs", 1, 85.0);
		Student s1 = new Student("michael", 2, 90.0);
		Student s2 = new Student("alex", 3, 1.0);
		Student s3 = new Student("ayo gotti", 4, 2.0);
		RedBlackTree<Student> roster=new RedBlackTree<Student>();
		roster.insert(s);
		roster.insert(s1);
		roster.insert(s2);
		roster.insert(s3);
		RosterEditer r = new RosterEditer(roster);
		if(!r.search("beebs").equals(r.roster.root.data))
		{
			fail("the search method is not working correctly");
		}
	}
	/*
	 * tests if the adjust grade method works correctly
	 */
	@Test
	void testRosterEditerAdjustGrade()
	{
		Student s = new Student("beebs", 1, 85.0); //creates 4 new students with various names, grades, and IDs
		Student s1 = new Student("michael", 2, 90.0);
		Student s2 = new Student("alex", 3, 1.0);
		Student s3 = new Student("ayo gotti", 4, 2.0);
		RedBlackTree<Student> roster=new RedBlackTree<Student>(); // creates a new RBT called roster, contains student objects
		roster.insert(s);
		roster.insert(s1);
		roster.insert(s2);
		roster.insert(s3);
		RosterEditer r = new RosterEditer(roster); // creates a new rosterEditer, calls roster as a parameter
		r.adjustGrade("beebs", 2.25); // attempts to adjust the grade of "beebs" to 2.25
		if(r.roster.root.data.getGrade() != 2.25) // if beebs grade is not 2.25, fails
		{
			fail("the adjustGrade method is not working correctly");
		}
	}
	
	/*
	 * tests if the print method is equivalent to the roster to string method
	 */
	@Test
	void testRosterEditerPrint()
	{
		Student s = new Student("beebs", 1, 85.0); //creates 4 new students with various names, grades, and IDs
		Student s1 = new Student("michael", 2, 90.0);
		Student s2 = new Student("alex", 3, 1.0);
		Student s3 = new Student("ayo gotti", 4, 2.0);
		RedBlackTree<Student> roster=new RedBlackTree<Student>(); // creates a new RBT called roster, contains student objects
		roster.insert(s);
		roster.insert(s1);
		roster.insert(s2);
		roster.insert(s3);
		RosterEditer r = new RosterEditer(roster);
		String expected = r.roster.toString();
		String actual = r.printStudents();
		if(!actual.equals(expected)) // if the roster.toString doesnt equal r.print students, fails.
		{
			fail("The printStudents method is not working correctly");
		}
	}
	
}
