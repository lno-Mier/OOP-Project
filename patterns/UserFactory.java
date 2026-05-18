package patterns;

import java.util.ArrayList;

import enums.ManagerType;
import enums.School;
import enums.TeacherTitle;
import models.Admin;
import models.EmployeeResearcher;
import models.Manager;
import models.Student;
import models.Teacher;
import models.User;

public class UserFactory {

   public static User createAdmin(String id, String firstName, String lastName, String login, String password) {
      return new Admin(id, firstName, lastName, login, password);
   }

   public static User createManager(String id, String firstName, String lastName, String login, String password, double salary, School department, ManagerType type) {
      return new Manager(id, firstName, lastName, login, password, salary, department, type);
   }

   public static User createTeacher(String id, String firstName, String lastName, String login, String password, double salary, School department, TeacherTitle title) {
      return new Teacher(id, firstName, lastName, login, password, salary, department, title, new ArrayList<>());
   }

   public static User createStudent(String id, String firstName, String lastName, String login, String password, double gpa, int year, School major) {
      return new Student(id, firstName, lastName, login, password, gpa, year, major);
   }

   public static User createEmployeeResearcher(String id, String firstName, String lastName, String login, String password, double salary, School department) {
      return new EmployeeResearcher(id, firstName, lastName, login, password, salary, department);
   }

   public static User createByRole(String role, String id, String firstName, String lastName, String login, String password) {
      switch (role.toLowerCase()) {
         case "admin":
            return createAdmin(id, firstName, lastName, login, password);
         case "manager":
            return createManager(id, firstName, lastName, login, password, 0, School.SITE, ManagerType.DEPARTMENT);
         case "teacher":
            return createTeacher(id, firstName, lastName, login, password, 0, School.SITE, TeacherTitle.TUTOR);
         case "student":
            return createStudent(id, firstName, lastName, login, password, 0.0, 1, School.SITE);
         case "researcher":
            return createEmployeeResearcher(id, firstName, lastName, login, password, 0, School.SITE);
         default:
            throw new IllegalArgumentException("Unknown role: " + role);
      }
   }
}
