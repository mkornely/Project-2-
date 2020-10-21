// --== CS400 File Header Information ==--
// Name: Elaina Timmerman
// Email: eltimmerman@wisc.edu
// Team: BA
// Role: back end developer
// TA: Brianna Cochran
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

// Student class creates a student object with the student's name, id, and grade in class
public class Student implements Comparable<Student> {
  private String studentName;
  private Integer studentId;
  private Double grade;


  // constructor that takes String student, Integer studnetId, and Double grade
  // as parameters and sets it to correct Student variables
  public Student(String student, Integer studentId, Double grade) {
    this.studentName = student;
    this.studentId = studentId;
    this.grade = grade;
  }

  // default constructor with no arguments
  // set studentName to NA, studentID to 0, and grade to 0.0
  public Student() {
    this.studentName = "NA";
    this.studentId = 0;
    this.grade = 0.0;
  }

  // returns student's name
  public String getStudentName() {
    return this.studentName;
  }

  // returns student's id number
  public Integer getStudentId() {
    return this.studentId;
  }

  // return student's grade
  public Double getGrade() {
    return this.grade;
  }

  // updates student's name to newName
  public void setStudentName(String newName) {
    this.studentName = newName;
  }

  // updates student's grade to newGrade
  public void setGrade(Double newGrade) {
    this.grade = newGrade;
  }

  // updates student's id to newId
  public void setStudentId(Integer newId) {
    this.studentId = newId;
  }

  // returns of string of student's name, id, and grade in the class
  public String toString() {
    return this.studentName + ", " + this.studentId + ", " + this.grade;
  }

  // compares the studentName and the name put in as parameter
  // returns a negative integer, zero, or a positive integer if name is less than, equals to, or
  // greater than studentName
  public int compareTo(Student student) {
    return this.studentName.compareTo(student.studentName);
  }

}
