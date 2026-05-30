package ui;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import exceptions.CreditLimitException;
import exceptions.LowHIndexException;
import exceptions.TooManyFailsException;
import interfaces.Researcher;
import models.Course;
import models.Mark;
import models.News;
import models.Student;
import models.Teacher;
import services.Logger;

public class StudentMenu extends Menu {
    private Student student;

    public StudentMenu(Scanner scanner, Student student) {
        super(scanner);
        this.student = student;
    }

    @Override
    public void show() {
        if (!student.isRegistrationApproved()) {
            System.out.println("Your registration is pending approval by the manager. Please wait.");
            return;
        }
        while (true) {
            printHeader("Student Menu - " + student.getFirstName() + " " + student.getLastName());
            System.out.println("1. View profile");
            System.out.println("2. View available courses");
            System.out.println("3. Register for course");
            System.out.println("4. View my courses & marks");
            System.out.println("5. Rate a teacher");
            System.out.println("6. View news");
            if (student.getYear() == 4) {
                System.out.println("7. Choose supervisor");
            }
            if (student.isResearcher()) {
                System.out.println("R. Research menu");
            }
            System.out.println("0. Logout");
            printDivider();

            String input = readLine("Choice: ");
            switch (input) {
                case "1":
                    student.printInfo();
                    break;
                case "2":
                    viewAvailableCourses();
                    break;
                case "3":
                    registerForCourse();
                    break;
                case "4":
                    viewMyCourses();
                    break;
                case "5":
                    rateTeacher();
                    break;
                case "6":
                    viewNews();
                    break;
                case "7":
                    if (student.getYear() == 4) {
                        chooseSupervisor();
                    } else {
                        System.out.println("Invalid choice.");
                    }
                    break;
                case "r":
                case "R":
                    if (student.isResearcher()) {
                        new ResearchMenu(scanner, student, student).show();
                    } else {
                        System.out.println("You are not a researcher.");
                    }
                    break;
                case "0":
                    Logger.getInstance().log("Student logged out", student.getLogin());
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void viewAvailableCourses() {
        List<Course> courses = db.getCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }
        System.out.println("\n--- Available Courses ---");
        for (Course c : courses) {
            System.out.printf("%s | %s | %d credits | %s%n", c.getCourseId(), c.getCourseName(), c.getCredits(), c.getStatus());
        }
    }

    private void registerForCourse() {
        viewAvailableCourses();
        String courseId = readLine("Enter course ID: ");
        Course course = db.getCourseById(courseId);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }
        try {
            student.registerForCourse(course);
            System.out.println("Registered for " + course.getCourseName() + ".");
            Logger.getInstance().log("Registered for " + course.getCourseId(), student.getLogin());
        } catch (CreditLimitException e) {
            System.out.println("Registration failed: " + e.getMessage());
        } catch (TooManyFailsException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    private void viewMyCourses() {
        List<Course> myCourses = student.getRegisteredCourses();
        if (myCourses.isEmpty()) {
            System.out.println("You are not registered in any courses.");
            return;
        }
        System.out.println("\n--- My Courses ---");
        for (Course c : myCourses) {
            Mark m = student.getMarkFor(c);
            String markStr = (m == null) ? "no mark yet" : m.toString();
            System.out.printf("%s | %s | %s%n", c.getCourseId(), c.getCourseName(), markStr);
        }
        System.out.println("\nTotal credits: " + student.getTotalCredits() + "/21");
        System.out.println("Failed courses: " + student.getFailCount() + "/3");
    }

    private void rateTeacher() {
        List<Teacher> teachers = db.getTeachers();
        if (teachers.isEmpty()) {
            System.out.println("No teachers available.");
            return;
        }
        for (Teacher t : teachers) {
            System.out.printf("%s - %s %s (%s)%n", t.getId(), t.getFirstName(), t.getLastName(), t.getTeacherTitle());
        }
        String teacherId = readLine("Enter teacher ID: ");
        Teacher target = null;
        for (Teacher t : teachers) {
            if (t.getId().equals(teacherId)) {
                target = t;
                break;
            }
        }
        if (target == null) {
            System.out.println("Teacher not found.");
            return;
        }
        double rating = readDouble("Enter rating (1-5): ");
        student.rateTeacher(target, rating);
        System.out.println("Thank you for the rating.");
    }

    private void viewNews() {
        List<News> newsList = db.getNews();
        if (newsList.isEmpty()) {
            System.out.println("No news.");
            return;
        }
        System.out.println("\n--- News ---");
        for (News n : newsList) {
            System.out.println(n.getTitle() + " | " + n.getDate());
            System.out.println("  " + n.getContent());
        }
    }

    private void chooseSupervisor() {
        System.out.println("\n--- Available Researchers ---");
        List<Teacher> teachers = db.getTeachers();
        for (Teacher t : teachers) {
            if (t.isResearcher()) {
                System.out.printf("%s - %s %s (h-index: %d)%n", t.getId(), t.getFirstName(), t.getLastName(), t.getHIndex());
            }
        }
        for (Map.Entry<String, Researcher> entry : db.getEmployeeResearchers().stream()
                .collect(java.util.stream.Collectors.toMap(er -> er.getId(), er -> (Researcher) er))
                .entrySet()) {
            System.out.printf("%s - %s (h-index: %d)%n", entry.getKey(), entry.getValue().getClass().getSimpleName(), entry.getValue().getHIndex());
        }
        String id = readLine("Enter researcher ID: ");
        Researcher chosen = null;
        Teacher t = db.getTeacherById(id);
        if (t != null && t.isResearcher()) {
            chosen = t;
        }
        if (chosen == null) {
            for (models.EmployeeResearcher er : db.getEmployeeResearchers()) {
                if (er.getId().equals(id)) {
                    chosen = er;
                    break;
                }
            }
        }
        if (chosen == null) {
            System.out.println("Researcher not found.");
            return;
        }
        try {
            student.setSupervisor(chosen);
            System.out.println("Supervisor assigned.");
            Logger.getInstance().log("Set supervisor " + id, student.getLogin());
        } catch (LowHIndexException e) {
            System.out.println("Failed: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("Failed: " + e.getMessage());
        }
    }
}
