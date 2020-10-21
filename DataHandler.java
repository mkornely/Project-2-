// --== CS400 File Header Information ==--
// Name: David Wissink
// Email: dwissink@wisc.edu
// Team: BA
// TA: Bri
// Lecturer: Dahl
// Notes to Grader: None
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class DataHandler {

    /** 
     * Takes as input the name of a file to read data from, the file is assumed
     * to be a csv file that contains one student per line in the format of:
     * studentname,id,grade.  This method will produce unexpected results otherwise
     * @param filename the name of the file to be read
     * @return A red black tree containing the files contents translated into student
     * objects
     */
    protected static RedBlackTree<Student> importData(String filename) {
        RedBlackTree<Student> tree = new RedBlackTree<Student>();
        File inFile = new File(filename);
        Scanner sc;
        try {
            sc = new Scanner(inFile);
        } catch (Exception E) {
            return null;
        }
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] studentFields = line.split(",");
            if (studentFields.length == 3) {
                String name = studentFields[0].trim();
                int id = Integer.parseInt(studentFields[1].trim());
                double grade = Double.parseDouble(studentFields[2].trim());
                tree.insert(new Student(name, id, grade));
            } 
        }
        sc.close();
        return tree;
    }

    /**
     * Writes the contents of a RedBlackTree to a specified file. The format is the
     * same as what is expected for the importData function
     * NOTE: the file will be written over if it contains any data already
     * @param tree the RedBlackTree to be exported
     * @param filename the name of the file to write to
     * @return true if the write was successful, false otherwise
     */
    protected static boolean exportData(RedBlackTree<Student> tree, String filename) {
        FileWriter fwrite = null;
        try {
            fwrite = new FileWriter(filename, false);
            if(recursiveWrite(fwrite, tree.root)) {
                fwrite.flush();
                return true;
            }
            return false;
        } catch (Exception E) {
            return false;
        }
    } 
    
    /**
     * Private helper method for the exportData() function
     * @param fwrite the filewriter to be written with
     * @param curr the root (or any) node in the RBTree
     * @return true if all writes were successful
     */
    private static boolean recursiveWrite(FileWriter fwrite, RedBlackTree.Node<Student> curr) {
        if (curr == null) return true;
        boolean result = true && recursiveWrite(fwrite, curr.leftChild);
        try {
            fwrite.write(curr.data.toString() + "\n");
        } catch (Exception E) {
            result = false;
        }
        return result && recursiveWrite(fwrite, curr.rightChild);
    } 
}
