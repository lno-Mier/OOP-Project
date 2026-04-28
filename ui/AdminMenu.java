package ui;

import java.util.Scanner;

import enums.*;
import models.*;

public class AdminMenu extends Menu {
   private Admin admin;

   public AdminMenu(Scanner scanner, Admin admin) {
      super(scanner);
      this.admin = admin;
   }

   @Override
   public void show() {
      while (true) {
         printHeader("Admin Menu — " + admin.getFirstName());
         System.out.println("1. View all users");
         System.out.println("2. Add teacher");
         System.out.println("3. Remove teacher");
         System.out.println("4. Add student");
         System.out.println("5. Remove student");
         System.out.println("6. Reset user password");
         System.out.println("0. Logout");
         printDivider();

         int choice = readInt("Choice: ");
         switch (choice) {
            case 1 -> viewAllUsers();
            case 2 -> addTeacher();
            case 3 -> removeTeacher();
            case 4 -> addStudent();
            case 5 -> removeStudent();
            case 6 -> resetPassword();
            case 0 -> { 
               System.out.println("Logging out..."); 
               return; 
            }
            default -> System.out.println("Invalid choice.");
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
   }

   private void addTeacher() {
      String id = readLine("ID: ");
      String firstName = readLine("First name: ");
      String lastName = readLine("Last name: ");
      String login = readLine("Login: ");
      String password = readLine("Password: ");
      double salary = Double.parseDouble(readLine("Salary: "));

      System.out.println("Schools: " + java.util.Arrays.toString(School.values()));
      School school = School.valueOf(readLine("School: ").toUpperCase());

      System.out.println("Titles: " + java.util.Arrays.toString(TeacherTitle.values()));
      TeacherTitle title = TeacherTitle.valueOf(readLine("Title: ").toUpperCase());

      Teacher t = new Teacher(id, firstName, lastName, login, password, salary, school, title, new java.util.ArrayList<>());
      admin.addTeacher(t);
      System.out.println("Teacher added.");
   }

   private void removeTeacher() {
      db.getTeachers().forEach(t -> System.out.println(t.getId() + " — " + t.getFirstName() + " " + t.getLastName()));
      String id = readLine("Teacher ID to remove: ");
      admin.removeTeacher(id);
      System.out.println("Removed.");
   }

   private void addStudent() {
      String id = readLine("ID: ");
      String firstName = readLine("First name: ");
      String lastName = readLine("Last name: ");
      String login = readLine("Login: ");
      String password = readLine("Password: ");
      double gpa = Double.parseDouble(readLine("GPA: "));
      int year = Integer.parseInt(readLine("Year: "));

      System.out.println("Schools: " + java.util.Arrays.toString(School.values()));
      School school = School.valueOf(readLine("School: ").toUpperCase());

      Student s = new Student(id, firstName, lastName, login, password, gpa, year, school);
      admin.addStudent(s);
      System.out.println("Student added.");
   }

   private void removeStudent() {
      db.getStudents().forEach(s -> System.out.println(s.getId() + " — " + s.getFirstName() + " " + s.getLastName()));
      String id = readLine("Student ID to remove: ");
      admin.removeStudent(id);
      System.out.println("Removed.");
   }

   private void resetPassword() {
      String login = readLine("User login: ");
      User user = db.authenticate(login, "");  // ищем без пароля
      // ищем вручную
      user = null;
      for (Teacher t : db.getTeachers())
         if (t.getLogin().equals(login)) { 
            user = t; 
            break; 
         }
      if (user == null)
         for (Student s : db.getStudents())
            if (s.getLogin().equals(login)) { 
               user = s; 
               break; 
            }

      if (user == null) { 
         System.out.println("User not found."); 
         return; 
      }
      String newPass = readLine("New password: ");
      admin.resetPassword(user, newPass);
   }
}