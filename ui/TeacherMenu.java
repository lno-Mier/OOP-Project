package ui;

import java.util.List;
import java.util.Scanner;

import models.Course;
import models.Employee;
import models.Lesson;
import models.Student;
import models.Teacher;
import models.Message;
import models.Complaint;
import enums.LessonType;
import enums.CourseStatus;


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
            System.out.println("1. View My Courses");
            System.out.println("2. Manage Course (Add lessons & Status)");
            System.out.println("3. View Students in Courses");
            System.out.println("4. Send Message");
            System.out.println("5. View Inbox");
            System.out.println("6. Send Complaint");
            System.out.println("0. Logout");
            printDivider();

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    viewCourses();
                    break;
                case 2:
                    manageCourse();
                    break;
                case 3:
                    viewStudents();
                    break;
                case 4:
                    sendMessage();
                    break;
                case 5:
                    viewInbox();
                    break;
                case 6:
                    sendComplaint();
                    break;
                case 0:
                    System.out.println("Logging out...");
                    return; // Возврат в предыдущее меню ну или назад просто
                default:
                    System.out.println("Invalid choice. Please try again.");
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
            System.out.printf("%d. %s [%s] - Credits: %d, Status: %s\n", 
                i + 1, c.getCourseName(), c.getCourseId(), c.getCredits(), c.getStatus());
        }
    }

    private void manageCourse() {
        viewCourses();
        if (teacher.getCourses().isEmpty()) return;

        int courseIndex = readInt("Select course to manage (0 to cancel): ") - 1;
        if (courseIndex == -1) return;

        if (courseIndex >= 0 && courseIndex < teacher.getCourses().size()) {
            Course course = teacher.getCourses().get(courseIndex);
            
            System.out.println("\n1. Add Lesson");
            System.out.println("2. Change Course Status");
            int choice = readInt("Choice: ");
            
            if (choice == 1) {
                String topic = readLine("Enter lesson topic: ");
                System.out.println("1. LECTURE");
                System.out.println("2. PRACTICE");
                int typeChoice = readInt("Select type: ");
                LessonType type = (typeChoice == 1) ? LessonType.LECTURE : LessonType.PRACTICE;
                
                int duration = readInt("Enter duration (minutes): ");
                String room = readLine("Enter room number: ");
                
                Lesson newLesson = new Lesson(topic, type, duration, room);
                course.addLesson(newLesson);
                System.out.println("Lesson added successfully.");
            } else if (choice == 2) {
                System.out.println("1. OPEN");
                System.out.println("2. CLOSED");
                System.out.println("3. PENDING");
                int statusChoice = readInt("Select new status: ");
                switch (statusChoice) {
                    case 1: course.setStatus(CourseStatus.OPEN); break;
                    case 2: course.setStatus(CourseStatus.CLOSED); break;
                    case 3: course.setStatus(CourseStatus.PENDING); break;
                    default: System.out.println("Invalid choice.");
                }
                System.out.println("Course status updated to " + course.getStatus());
            }
        } else {
            System.out.println("Invalid course selection.");
        }
    }

    private void viewStudents() {
        viewCourses();
        if (teacher.getCourses().isEmpty()) return;

        int courseIndex = readInt("Select course to view students (0 to cancel): ") - 1;
        if (courseIndex == -1) return;

        if (courseIndex >= 0 && courseIndex < teacher.getCourses().size()) {
            Course course = teacher.getCourses().get(courseIndex);
            List<Student> students = course.getStudents();
            System.out.println("\n--- Students in " + course.getCourseName() + " ---");
            if (students.isEmpty()) {
                System.out.println("No students enrolled yet.");
            } else {
                for (Student s : students) {
                    System.out.printf("ID: %s | Name: %s %s | GPA: %.2f\n", 
                        s.getId(), s.getFirstName(), s.getLastName(), s.getGpa());
                }
            }
        } else {
            System.out.println("Invalid selection.");
        }
    }

    private void sendMessage() {
        String receiverId = readLine("Enter receiver Employee ID: ");
        Employee receiver = null;
        
        // Поиск получателя через базы данных, среди учителей и менеджеров
        for (Teacher t : db.getTeachers()) {
            if (t.getId().equals(receiverId)) {
                receiver = t;
                break;
            }
        }
        if (receiver == null) {
            for (models.Manager m : db.getManagers()) {
                if (m.getId().equals(receiverId)) {
                    receiver = m;
                    break;
                }
            }
        }
        
        if (receiver != null) {
            String text = readLine("Enter message text: ");
            teacher.sendMessage(receiver, text);
            System.out.println("Message sent successfully.");
        } else {
            System.out.println("Employee not found.");
        }
    }

    private void viewInbox() {
        System.out.println("\n--- Inbox ---");
        List<Message> inbox = teacher.getInbox();
        if (inbox.isEmpty()) {
            System.out.println("Inbox is empty.");
        } else {
            for (Message msg : inbox) {
                System.out.printf("From: %s %s | Time: %s\nText: %s\n", 
                    msg.getSender().getFirstName(), msg.getSender().getLastName(), 
                    msg.getTime(), msg.getText());
                System.out.println("-".repeat(20));
            }
        }
    }

    private void sendComplaint() {
        String title = readLine("Enter complaint title: ");
        String text = readLine("Enter complaint details: ");
        
        teacher.sendComplaint(title, text);
    }
}
