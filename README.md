# Student Management System

This is a beginner-friendly Java project for managing students, courses, and enrollments.

## Features

- Add student
- Add course
- Enroll student in multiple courses
- View students
- View enrollments
- Process enrollment using a thread
- Handle invalid input with exception handling
- Save and load data using files

## Classes Used

- `Student`
- `Course`
- `InvalidDataException`
- `EnrollmentProcessor`
- `studentmgnt`

## Data Stored

The program saves data in these files:

- `students.txt`
- `courses.txt`
- `enrollments.txt`

## How To Compile

Open terminal in the project folder and run:

```powershell
javac javafa\studentmgnt.java
```

## How To Run

```powershell
java -cp javafa studentmgnt
```

## Menu Options

1. Add Student
2. Add Course
3. Enroll Student
4. View Students
5. View Enrollments
6. Process Enrollment (Thread)
7. Save Data
8. Exit

## Example Student Details

- `studentId` as `int`
- `name` as `String`
- `email` as `String`

## Example Course Details

- `courseId` as `int`
- `courseName` as `String`
- `fee` as `double`

## Recommended File Structure

For beginners, using a single file is fine:

- `studentmgnt.java`

For better project structure later, you can use multiple files:

- `Student.java`
- `Course.java`
- `InvalidDataException.java`
- `EnrollmentProcessor.java`
- `studentmgnt.java`

## Notes

- This project uses `HashMap` and `ArrayList`.
- A student can enroll in multiple courses.
- Data is loaded automatically when the program starts.
- Data is saved when you choose save or exit.
