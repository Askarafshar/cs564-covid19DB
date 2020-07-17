import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Main Application.
 * <p>
 * You do not need to change this class.
 */
public class BTreeMain {

    private static long highestRID = -1;

    public static void main(String[] args) {

        /** Read the input file -- input.txt */
        Scanner scan = null;
        try {
            //Original path does not work on my machine (KTW), try again on csl
            //scan = new Scanner(new File("src/input.txt"));
            scan = new Scanner(new File("./input.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }

        /** Read the minimum degree of B+Tree first */

        int degree = scan.nextInt();

        BTree bTree = new BTree(degree);

        /** Reading the database student.csv into B+Tree Node*/
        List<Student> studentsDB = getStudents();

        // clear file contents so we do not duplicate entries
        try {
            new FileWriter("./student.csv", false).close();
        } catch (IOException e1) {
            System.out.println("Failed to clear student.csv prior to inserting data in B+Tree. File may contain duplicate entries after this run.");
            e1.printStackTrace();
        }
        
        // add all students to tree
        for (Student s : studentsDB) {
            bTree.insert(s);
        }
        
        /** Start reading the operations now from input file*/
        try {
            while (scan.hasNextLine()) {
                Scanner s2 = new Scanner(scan.nextLine());

                while (s2.hasNext()) {
                		
                    String operation = s2.next();

                    switch (operation) {
                        case "insert": {

                            long studentId = Long.parseLong(s2.next());
                            String studentName = s2.next() + " " + s2.next();
                            String major = s2.next();
                            String level = s2.next();
                            int age = Integer.parseInt(s2.next());
                            long recordID;
                            try {
                                recordID = Long.parseLong(s2.next());
                            } catch (NoSuchElementException e) {
                                recordID = ++highestRID; 
                            }

                            Student s = new Student(studentId, age, studentName, major, level, recordID);
                            bTree.insert(s);

                            break;
                        }
                        case "delete": {
                            long studentId = Long.parseLong(s2.next());
                            boolean result = bTree.delete(studentId);
                            if (result)
                                System.out.println("Student deleted successfully.");
                            else
                                System.out.println("Student deletion failed.");

                            break;
                        }
                        case "search": {
                            long studentId = Long.parseLong(s2.next());
                            long recordID = bTree.search(studentId);
                            if (recordID != -1)
                                System.out.println("Student exists in the database at " + recordID);
                            else
                                System.out.println("Student does not exist.");
                            break;
                        }
                        case "print": {
                            List<Long> listOfRecordID = new ArrayList<>();
                            listOfRecordID = bTree.print();
                            System.out.println("List of recordIDs in B+Tree " + listOfRecordID.toString());
                        }
                        default:
                            System.out.println("Wrong Operation");
                            break;
                    }
                }
                s2.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    /**
     * Parses student.csv to return a list of all student entries. 
     * 
     * @return  list of student entries
     * @author  kwalker26
     */
    private static List<Student> getStudents() {
        // local variables
        String currStudent, name, major, level;
        long sid, rid;
        int age;
        String[] studentInfo;
        List<Student> studentList = new ArrayList<>();

        // open file, try block will manage closing buffer when we finish
        try (BufferedReader studentBuf = new BufferedReader(new FileReader("./Student.csv"))) {
            // parse each line
            while((currStudent = studentBuf.readLine()) != null) {
                // split line by delimiter
                studentInfo = currStudent.split(",");
                
                // extract student data
                sid = Long.parseLong(studentInfo[0]);
                name = studentInfo[1];
                major = studentInfo[2];
                level = studentInfo[3];
                age = Integer.parseInt(studentInfo[4]);
                rid = Integer.parseInt(studentInfo[5]);

                // add student to list
                studentList.add(new Student(sid, age, name, major, level, rid));

                // update highest stored rid
                if (highestRID < rid) {
                    highestRID = rid;
                }
            }

        }
        catch (FileNotFoundException e) {
            System.out.println("File student.csv not found.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Age or record ID was an invalid integer.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Reading a line failed.");
            e.printStackTrace();
        }
        return studentList;
    }
}
