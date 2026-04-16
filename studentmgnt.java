import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class InvalidDataException extends Exception {
    InvalidDataException(String rrMessage) {
        super(rrMessage);
    }
}

class Student {
    int studentId;
    String name;
    String email;

    Student(int rrStudentId, String rrName, String rrEmail) {
        studentId = rrStudentId;
        name = rrName;
        email = rrEmail;
    }

    @Override
    public String toString() {
        return "ID: " + studentId + ", Name: " + name + ", Email: " + email;
    }
}

class Course {
    int courseId;
    String courseName;
    double fee;

    Course(int rrCourseId, String rrCourseName, double rrFee) {
        courseId = rrCourseId;
        courseName = rrCourseName;
        fee = rrFee;
    }

    @Override
    public String toString() {
        return "Course ID: " + courseId + ", Course Name: " + courseName + ", Fee: " + fee;
    }
}

class EnrollmentProcessor extends Thread {
    private final Student rrStudent;
    private final Course rrCourse;

    EnrollmentProcessor(Student rrStudent, Course rrCourse) {
        this.rrStudent = rrStudent;
        this.rrCourse = rrCourse;
    }

    @Override
    public void run() {
        System.out.println("\nProcessing enrollment for " + rrStudent.name + " in " + rrCourse.courseName + "...");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException rrException) {
            System.out.println("Enrollment thread interrupted.");
            return;
        }

        System.out.println("Enrollment processed successfully for " + rrStudent.name + " in " + rrCourse.courseName + ".\n");
    }
}

public class studentmgnt {
    static Scanner rr = new Scanner(System.in);

    static HashMap<Integer, Student> rrStudents = new HashMap<Integer, Student>();
    static HashMap<Integer, Course> rrCourses = new HashMap<Integer, Course>();
    static HashMap<Integer, ArrayList<Integer>> rrEnrollments = new HashMap<Integer, ArrayList<Integer>>();

    static final String STUDENT_FILE = "javafa/students.txt";
    static final String COURSE_FILE = "javafa/courses.txt";
    static final String ENROLLMENT_FILE = "javafa/enrollments.txt";

    public static void main(String[] args) {
        loadData();

        while (true) {
            System.out.println("===== Student Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Add Course");
            System.out.println("4. Enroll Students");
            System.out.println("5. View Enrollments");
            System.out.println("6. Process Enrollment (Thread)");
            System.out.println("7. Save Data");
            System.out.println("8. Exit");

            int rrChoice = readInt("Enter your choice: ");

            switch (rrChoice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewStudents();
                    break;
                case 3:
                    addCourse();
                    break;
                case 4:
                    enrollStudent();
                    break;
                case 5:
                    viewEnrollments();
                    break;
                case 6:
                    processEnrollment();
                    break;
                case 7:
                    saveData();
                    break;
                case 8:
                    saveData();
                    System.out.println("Exiting program. Data saved.");
                    rr.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please select from 1 to 8.\n");
            }
        }
    }

    static void addStudent() {
        try {
            int rrStudentId = readInt("Enter student ID: ");
            if (rrStudents.containsKey(rrStudentId)) {
                throw new InvalidDataException("Student ID already exists.");
            }

            System.out.print("Enter student name: ");
            String rrName = rr.nextLine().trim();
            if (rrName.isEmpty()) {
                throw new InvalidDataException("Student name cannot be empty.");
            }

            System.out.print("Enter student email: ");
            String rrEmail = rr.nextLine().trim();
            if (!rrEmail.contains("@") || !rrEmail.contains(".")) {
                throw new InvalidDataException("Invalid email format.");
            }

            Student rrStudent = new Student(rrStudentId, rrName, rrEmail);
            rrStudents.put(rrStudentId, rrStudent);
            System.out.println("Student added successfully.\n");
        } catch (InvalidDataException rrException) {
            System.out.println("Error: " + rrException.getMessage() + "\n");
        }
    }

    static void addCourse() {
        try {
            int rrCourseId = readInt("Enter course ID: ");
            if (rrCourses.containsKey(rrCourseId)) {
                throw new InvalidDataException("Course ID already exists.");
            }

            System.out.print("Enter course name: ");
            String rrCourseName = rr.nextLine().trim();
            if (rrCourseName.isEmpty()) {
                throw new InvalidDataException("Course name cannot be empty.");
            }

            double rrFee = readDouble("Enter course fee: ");
            if (rrFee < 0) {
                throw new InvalidDataException("Course fee cannot be negative.");
            }

            Course rrCourse = new Course(rrCourseId, rrCourseName, rrFee);
            rrCourses.put(rrCourseId, rrCourse);
            System.out.println("Course added successfully.\n");
        } catch (InvalidDataException rrException) {
            System.out.println("Error: " + rrException.getMessage() + "\n");
        }
    }

    static void enrollStudent() {
        try {
            int rrStudentId = readInt("Enter student ID: ");
            if (!rrStudents.containsKey(rrStudentId)) {
                throw new InvalidDataException("Student not found.");
            }

            int rrCourseId = readInt("Enter course ID: ");
            if (!rrCourses.containsKey(rrCourseId)) {
                throw new InvalidDataException("Course not found.");
            }

            if (!rrEnrollments.containsKey(rrStudentId)) {
                rrEnrollments.put(rrStudentId, new ArrayList<Integer>());
            }

            if (rrEnrollments.get(rrStudentId).contains(rrCourseId)) {
                throw new InvalidDataException("Student already enrolled in this course.");
            }

            rrEnrollments.get(rrStudentId).add(rrCourseId);
            System.out.println("Student enrolled successfully.\n");
        } catch (InvalidDataException rrException) {
            System.out.println("Error: " + rrException.getMessage() + "\n");
        }
    }

    static void viewStudents() {
        if (rrStudents.isEmpty()) {
            System.out.println("No students available.\n");
            return;
        }

        System.out.println("\n----- Student List -----");
        for (Student rrStudent : rrStudents.values()) {
            System.out.println(rrStudent);
        }
        System.out.println();
    }

    static void viewEnrollments() {
        if (rrEnrollments.isEmpty()) {
            System.out.println("No enrollments available.\n");
            return;
        }

        System.out.println("\n----- Enrollment List -----");
        for (Map.Entry<Integer, ArrayList<Integer>> rrEntry : rrEnrollments.entrySet()) {
            Student rrStudent = rrStudents.get(rrEntry.getKey());
            if (rrStudent == null) {
                continue;
            }

            System.out.println("Student: " + rrStudent.name + " (ID: " + rrStudent.studentId + ")");

            ArrayList<Integer> rrCourseList = rrEntry.getValue();
            for (Integer rrCourseId : rrCourseList) {
                Course rrCourse = rrCourses.get(rrCourseId);
                if (rrCourse != null) {
                    System.out.println("  Enrolled in: " + rrCourse.courseName + " | Fee: " + rrCourse.fee);
                }
            }
            System.out.println();
        }
    }

    static void processEnrollment() {
        try {
            int rrStudentId = readInt("Enter student ID: ");
            if (!rrStudents.containsKey(rrStudentId)) {
                throw new InvalidDataException("Student not found.");
            }

            int rrCourseId = readInt("Enter course ID: ");
            if (!rrCourses.containsKey(rrCourseId)) {
                throw new InvalidDataException("Course not found.");
            }

            if (!rrEnrollments.containsKey(rrStudentId) || !rrEnrollments.get(rrStudentId).contains(rrCourseId)) {
                throw new InvalidDataException("This enrollment does not exist. Please enroll first.");
            }

            Student rrStudent = rrStudents.get(rrStudentId);
            Course rrCourse = rrCourses.get(rrCourseId);

            EnrollmentProcessor rrThread = new EnrollmentProcessor(rrStudent, rrCourse);
            rrThread.start();
            rrThread.join();
        } catch (InvalidDataException rrException) {
            System.out.println("Error: " + rrException.getMessage() + "\n");
        } catch (InterruptedException rrException) {
            System.out.println("Thread error: " + rrException.getMessage() + "\n");
        }
    }

    static int readInt(String rrMessage) {
        while (true) {
            try {
                System.out.print(rrMessage);
                int rrValue = Integer.parseInt(rr.nextLine());
                return rrValue;
            } catch (NumberFormatException rrException) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    static double readDouble(String rrMessage) {
        while (true) {
            try {
                System.out.print(rrMessage);
                double rrValue = Double.parseDouble(rr.nextLine());
                return rrValue;
            } catch (NumberFormatException rrException) {
                System.out.println("Invalid input. Please enter a valid decimal number.");
            }
        }
    }

    static void saveData() {
        try {
            BufferedWriter rrStudentWriter = new BufferedWriter(new FileWriter(STUDENT_FILE));
            for (Student rrStudent : rrStudents.values()) {
                rrStudentWriter.write(rrStudent.studentId + "," + rrStudent.name + "," + rrStudent.email);
                rrStudentWriter.newLine();
            }
            rrStudentWriter.close();

            BufferedWriter rrCourseWriter = new BufferedWriter(new FileWriter(COURSE_FILE));
            for (Course rrCourse : rrCourses.values()) {
                rrCourseWriter.write(rrCourse.courseId + "," + rrCourse.courseName + "," + rrCourse.fee);
                rrCourseWriter.newLine();
            }
            rrCourseWriter.close();

            BufferedWriter rrEnrollmentWriter = new BufferedWriter(new FileWriter(ENROLLMENT_FILE));
            for (Map.Entry<Integer, ArrayList<Integer>> rrEntry : rrEnrollments.entrySet()) {
                for (Integer rrCourseId : rrEntry.getValue()) {
                    rrEnrollmentWriter.write(rrEntry.getKey() + "," + rrCourseId);
                    rrEnrollmentWriter.newLine();
                }
            }
            rrEnrollmentWriter.close();

            System.out.println("Data saved successfully.\n");
        } catch (IOException rrException) {
            System.out.println("Error while saving data: " + rrException.getMessage() + "\n");
        }
    }

    static void loadData() {
        loadStudents();
        loadCourses();
        loadEnrollments();
    }

    static void loadStudents() {
        try {
            BufferedReader rrStudentReader = new BufferedReader(new FileReader(STUDENT_FILE));
            String rrLine;

            while ((rrLine = rrStudentReader.readLine()) != null) {
                String[] rrParts = rrLine.split(",");
                if (rrParts.length == 3) {
                    int rrStudentId = Integer.parseInt(rrParts[0]);
                    String rrName = rrParts[1];
                    String rrEmail = rrParts[2];
                    rrStudents.put(rrStudentId, new Student(rrStudentId, rrName, rrEmail));
                }
            }

            rrStudentReader.close();
        } catch (IOException rrException) {
            // File may not exist the first time.
        }
    }

    static void loadCourses() {
        try {
            BufferedReader rrCourseReader = new BufferedReader(new FileReader(COURSE_FILE));
            String rrLine;

            while ((rrLine = rrCourseReader.readLine()) != null) {
                String[] rrParts = rrLine.split(",");
                if (rrParts.length == 3) {
                    int rrCourseId = Integer.parseInt(rrParts[0]);
                    String rrCourseName = rrParts[1];
                    double rrFee = Double.parseDouble(rrParts[2]);
                    rrCourses.put(rrCourseId, new Course(rrCourseId, rrCourseName, rrFee));
                }
            }

            rrCourseReader.close();
        } catch (IOException rrException) {
            // File may not exist the first time.
        }
    }

    static void loadEnrollments() {
        try {
            BufferedReader rrEnrollmentReader = new BufferedReader(new FileReader(ENROLLMENT_FILE));
            String rrLine;

            while ((rrLine = rrEnrollmentReader.readLine()) != null) {
                String[] rrParts = rrLine.split(",");
                if (rrParts.length == 2) {
                    int rrStudentId = Integer.parseInt(rrParts[0]);
                    int rrCourseId = Integer.parseInt(rrParts[1]);

                    if (!rrEnrollments.containsKey(rrStudentId)) {
                        rrEnrollments.put(rrStudentId, new ArrayList<Integer>());
                    }
                    rrEnrollments.get(rrStudentId).add(rrCourseId);
                }
            }

            rrEnrollmentReader.close();
        } catch (IOException rrException) {
            // File may not exist the first time.
        }
    }
}
