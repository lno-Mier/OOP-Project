package ui;

import java.util.List;
import java.util.Scanner;

import enums.CourseStatus;
import enums.LessonType;
import models.Course;
import models.Employee;
import models.Lesson;
import models.Manager;
import models.Mark;
import models.Message;
import models.Student;
import models.Teacher;
import services.Logger;

public class TeacherMenu extends Menu {
    private Teacher teacher;

    public TeacherMenu(Scanner scanner, Teacher teacher) {
        super(scanner);
        this.teacher = teacher;
    }

    @Override
    public void show() {
        while (true) {
            printHeader("Teacher Menu - " + teacher.getFirstName() + " " + teacher.getLastName());
            System.out.println("1. View my courses");
            System.out.println("2. Manage course (lessons & status)");
            System.out.println("3. View students of a course");
            System.out.println("4. Put marks");
            System.out.println("5. Send message");
            System.out.println("6. View inbox");
            System.out.println("7. Send complaint");
            if (teacher.isResearcher()) {
                System.out.println("R. Research menu");
            }
            System.out.println("0. Logout");
            printDivider();

            String input = readLine("Choice: ");
            switch (input) {
                case "1":
                    viewCourses();
                    break;
                case "2":
                    manageCourse();
                    break;
                case "3":
                    viewStudents();
                    break;
                case "4":
                    putMarks();
                    break;
                case "5":
                    sendMessage();
                    break;
                case "6":
                    viewInbox();
                    break;
                case "7":
                    sendComplaint();
                    break;
                case "r":
                case "R":
                    if (teacher.isResearcher()) {
                        new ResearchMenu(scanner, teacher, teacher).show();
                    } else {
                        System.out.println("You are not a researcher.");
                    }
                    break;
                case "0":
                    Logger.getInstance().log("Teacher logged out", teacher.getLogin());
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void viewCourses() {
        System.out.println("\n--- My Courses ---");
        List<Course> courses = teacher.getCourses();
        if (courses.isEmpty()) {
            System.out.println("You have no assigned courses.");
            return;
        }
        for (int i = 0; i < courses.size(); i++) {
            Course c = courses.get(i);
            System.out.printf("%d. %s [%s] - Credits: %d, Status: %s%n", i + 1, c.getCourseName(), c.getCourseId(), c.getCredits(), c.getStatus());
        }
    }

    private void manageCourse() {
        viewCourses();
        if (teacher.getCourses().isEmpty()) {
            return;
        }
        int courseIndex = readInt("Select course (0 to cancel): ") - 1;
        if (courseIndex == -1) {
            return;
        }
        if (courseIndex < 0 || courseIndex >= teacher.getCourses().size()) {
            System.out.println("Invalid selection.");
            return;
        }
        Course course = teacher.getCourses().get(courseIndex);

        System.out.println("\n1. Add lesson");
        System.out.println("2. Change course status");
        int choice = readInt("Choice: ");

        if (choice == 1) {
            String topic = readLine("Lesson topic: ");
            System.out.println("1. LECTURE  2. PRACTICE");
            int typeChoice = readInt("Type: ");
            LessonType type = (typeChoice == 1) ? LessonType.LECTURE : LessonType.PRACTICE;
            int duration = readInt("Duration (minutes): ");
            String room = readLine("Room number: ");
            Lesson newLesson = new Lesson(topic, type, duration, room);
            course.addLesson(newLesson);
            System.out.println("Lesson added.");
        } else if (choice == 2) {
            System.out.println("1. OPEN  2. CLOSED  3. PENDING");
            int statusChoice = readInt("New status: ");
            switch (statusChoice) {
                case 1:
                    course.setStatus(CourseStatus.OPEN);
                    break;
                case 2:
                    course.setStatus(CourseStatus.CLOSED);
                    break;
                case 3:
                    course.setStatus(CourseStatus.PENDING);
                    break;
                default:
                    System.out.println("Invalid choice.");
                    return;
            }
            System.out.println("Status updated to " + course.getStatus());
        }
    }

    private void viewStudents() {
        viewCourses();
        if (teacher.getCourses().isEmpty()) {
            return;
        }
        int courseIndex = readInt("Select course (0 to cancel): ") - 1;
        if (courseIndex == -1) {
            return;
        }
        if (courseIndex < 0 || courseIndex >= teacher.getCourses().size()) {
            System.out.println("Invalid selection.");
            return;
        }
        Course course = teacher.getCourses().get(courseIndex);
        List<Student> students = course.getStudents();
        System.out.println("\n--- Students in " + course.getCourseName() + " ---");
        if (students.isEmpty()) {
            System.out.println("No students enrolled.");
            return;
        }
        for (Student s : students) {
            System.out.printf("ID: %s | Name: %s %s | GPA: %.2f%n", s.getId(), s.getFirstName(), s.getLastName(), s.getGpa());
        }
    }

    private void putMarks() {
        viewCourses();
        if (teacher.getCourses().isEmpty()) {
            return;
        }
        int courseIndex = readInt("Select course (0 to cancel): ") - 1;
        if (courseIndex == -1) {
            return;
        }
        if (courseIndex < 0 || courseIndex >= teacher.getCourses().size()) {
            System.out.println("Invalid selection.");
            return;
        }
        Course course = teacher.getCourses().get(courseIndex);
        List<Student> students = course.getStudents();
        if (students.isEmpty()) {
            System.out.println("No students in this course.");
            return;
        }
        for (Student s : students) {
            System.out.printf("%s - %s %s%n", s.getId(), s.getFirstName(), s.getLastName());
        }
        String studentId = readLine("Student ID: ");
        Student target = null;
        for (Student s : students) {
            if (s.getId().equals(studentId)) {
                target = s;
                break;
            }
        }
        if (target == null) {
            System.out.println("Student not found.");
            return;
        }
        Mark mark = target.getOrCreateMark(course);
        System.out.println("Current mark: " + mark);
        System.out.println("1. Set 1st attestation");
        System.out.println("2. Set 2nd attestation");
        System.out.println("3. Set final exam");
        int choice = readInt("Choice: ");
        double score = readDouble("Score: ");
        switch (choice) {
            case 1:
                mark.setFirstAttestation(score);
                break;
            case 2:
                mark.setSecondAttestation(score);
                break;
            case 3:
                mark.setFinalExam(score);
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }
        System.out.println("Mark updated: " + mark);
        Logger.getInstance().log("Updated mark for " + target.getLogin() + " in " + course.getCourseId(), teacher.getLogin());
    }

    private void sendMessage() {
        String receiverId = readLine("Enter receiver Employee ID: ");
        Employee receiver = null;
        for (Teacher t : db.getTeachers()) {
            if (t.getId().equals(receiverId)) {
                receiver = t;
                break;
            }
        }
        if (receiver == null) {
            for (Manager m : db.getManagers()) {
                if (m.getId().equals(receiverId)) {
                    receiver = m;
                    break;
                }
            }
        }
        if (receiver == null) {
            System.out.println("Employee not found.");
            return;
        }
        String text = readLine("Message: ");
        teacher.sendMessage(receiver, text);
        System.out.println("Message sent.");
    }

    private void viewInbox() {
        System.out.println("\n--- Inbox ---");
        List<Message> inbox = teacher.getInbox();
        if (inbox.isEmpty()) {
            System.out.println("Inbox is empty.");
            return;
        }
        for (Message msg : inbox) {
            System.out.printf("From: %s %s | Time: %s%n", msg.getSender().getFirstName(), msg.getSender().getLastName(), msg.getTime());
            System.out.println("Text: " + msg.getText());
            System.out.println("-".repeat(20));
        }
    }

    private void sendComplaint() {
        String title = readLine("Complaint title: ");
        String text = readLine("Details: ");
        teacher.sendComplaint(title, text);
    }
}
