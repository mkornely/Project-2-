// --== CS400 File Header Information ==--
// Name: Brian Wang
// Email: bwang338@wisc.edu
// Team: BA
// TA: Brianna Cochran
// Lecturer: Gary Dahl
// Notes to Grader: None

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DataWrangler {
	
	protected static RedBlackTree<Student> importData(String filename){
		RedBlackTree<Student> roster = new RedBlackTree<Student>();
		File file = new File(filename);
		Scanner scan;
		try {
			scan = new Scanner(file);
		} catch (Exception e) {
			System.out.println("We cannot find the file you inputted.");
			return null;
		}
		String data;
		String[] studentInfo;
		String name;
		int id;
		double gradeInClass;
		while (scan.hasNextLine()) {
			data = scan.nextLine();
			studentInfo = data.split(", ");
			if (checkFile(studentInfo)) {
				name = studentInfo[0];
				id = Integer.parseInt(studentInfo[1]);
				gradeInClass = Double.parseDouble(studentInfo[2]);
				roster.insert(new Student(name, id, gradeInClass));
			}
		}
		scan.close();
		return roster;
	}
	
	private static boolean checkFile(String[] data) {
		if (data.length != 3) {
			return false;
		}
		return true;
	}
	
	protected static boolean exportData(RedBlackTree<Student> roster, String newFile) {
		try {
			FileWriter writer = new FileWriter(newFile);
			if (exportHelper(writer, roster.root)==true) {
				writer.flush();
				return true;
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			return false;
		}
		return false;
	}
	
	private static boolean exportHelper(FileWriter writer, RedBlackTree.Node<Student> current) {
		if (current == null) {
			return true;
		}
		Student node = current.data;
		String data = node.getStudentName() + ", " + ((Integer)node.getStudentId()).toString() + ", " + ((Double)node.getGrade()).toString() + "/n";
		try {
			writer.write(data);
		} catch (IOException e) {
			return false;
		}
		return exportHelper(writer, current.leftChild) && exportHelper(writer, current.rightChild);
		
	}
}
