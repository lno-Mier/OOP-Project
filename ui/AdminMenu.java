package ui;

import java.util.Arrays;
import java.util.Scanner;

import enums.School;
import enums.TeacherTitle;
import models.Admin;
import models.Student;
import models.Teacher;
import models.User;
import patterns.UserFactory;
import services.Logger;

public class AdminMenu extends Menu {
    private Admin admin;

    public AdminMenu(Scanner scanner, Admin admin) {
        super(scanner);
        this.admin = admin;
    }

    @Override
    public void show() {
        while (true) {
            printHeader("Admin Menu - " + admin.getFirstName());
            System.out.println("1. View all users");
            System.out.println("2. Add teacher");
            System.out.println("3. Remove teacher");
            System.out.println("4. Add student");
            System.out.println("5. Remove student");
            System.out.println("6. Reset user password");
            System.out.println("7. View log");
            System.out.println("0. Logout");
            printDivider();

            int choice = readInt("Choice: ");
            switch (choice) {
                case 1:
                    viewAllUsers();
                    break;
                case 2:
                    addTeacher();
                    break;
                case 3:
                    removeTeacher();
                    break;
                case 4:
                    addStudent();
                    break;
                case 5:
                    removeStudent();
                    break;
                case 6:
                    resetPassword();
                    break;
                case 7:
                    Logger.getInstance().printLog();
                    break;
                case 0:
                    Logger.getInstance().log("Admin logged out", admin.getLogin());
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void viewAllUsers() {
        System.out.println("\n--- Admins ---");
        db.getAdmins().forEach(User::printInfo);
        System.out.println("\n--- Managers ---");
        db.getManagers().forEach(User::printInfo);
        System.out.println("\n--- Teachers ---");
        db.getTeachers().forEach(User::printInfo);
        System.out.println("\n--- Students ---");
        db.getStudents().forEach(User::printInfo);
        System.out.println("\n--- Employee Researchers ---");
        db.getEmployeeResearchers().forEach(User::printInfo);
    }

    private void addTeacher() {
        String id = readLine("ID: ");
        String firstName = readLine("First name: ");
        String lastName = readLine("Last name: ");
        String login = readLine("Login: ");
        String password = readLine("Password: ");
        double salary = readDouble("Salary: ");
        System.out.println("Schools: " + Arrays.toString(School.values()));
        // Крч тут был баг с ексепшеном я его исправл
        School school;
        try {
            school = School.valueOf(readLine("School: ").toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid school.");
            return;
        }
        System.out.println("Titles: " + Arrays.toString(TeacherTitle.values()));
        TeacherTitle title;
        try {
            title = TeacherTitle.valueOf(readLine("Title: ").toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid title.");
            return;
        }

        Teacher t = (Teacher) UserFactory.createTeacher(id, firstName, lastName, login, password, salary, school, title);
        admin.addTeacher(t);
        System.out.println("Teacher added.");
        Logger.getInstance().log("Added teacher " + login, admin.getLogin());
    }

    private void removeTeacher() {
        db.getTeachers().forEach(t -> System.out.println(t.getId() + " - " + t.getFirstName() + " " + t.getLastName()));
        String id = readLine("Teacher ID to remove: ");
        admin.removeTeacher(id);
        System.out.println("Removed.");
        Logger.getInstance().log("Removed teacher " + id, admin.getLogin());
    }

    private void addStudent() {
        String id = readLine("ID: ");
        String firstName = readLine("First name: ");
        String lastName = readLine("Last name: ");
        String login = readLine("Login: ");
        String password = readLine("Password: ");
        double gpa = readDouble("GPA: ");
        int year = readInt("Year: ");
        System.out.println("Schools: " + Arrays.toString(School.values()));
        // Два бага лол
        School school;
        try {
            school = School.valueOf(readLine("School: ").toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid school.");
            return;
        }

        Student s = (Student) UserFactory.createStudent(id, firstName, lastName, login, password, gpa, year, school);
        admin.addStudent(s);
        System.out.println("Student added.");
        Logger.getInstance().log("Added student " + login, admin.getLogin());
    }

    private void removeStudent() {
        db.getStudents().forEach(s -> System.out.println(s.getId() + " - " + s.getFirstName() + " " + s.getLastName()));
        String id = readLine("Student ID to remove: ");
        admin.removeStudent(id);
        System.out.println("Removed.");
        Logger.getInstance().log("Removed student " + id, admin.getLogin());
    }

    private void resetPassword() {
        String login = readLine("User login: ");
        User user = db.findUserByLogin(login);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }
        String newPass = readLine("New password: ");
        admin.resetPassword(user, newPass);
        Logger.getInstance().log("Reset password for " + login, admin.getLogin());
    }
}