package models;

public class Admin extends Employee {
   private static final long serialVersionUID = 1L;

   public Admin(String id, String firstName, String lastName, String login, String password) {
      super(id, firstName, lastName, login, password, 0, null);
   }

   // Управление пользователями через Database
   public void addStudent(Student s) { 
      services.Database.getInstance().addStudent(s); 
   }
   public void removeStudent(String id) { 
      services.Database.getInstance().removeStudent(id); 
   }
   public void addTeacher(Teacher t) { 
      services.Database.getInstance().addTeacher(t); 
   }
   public void removeTeacher(String id) { 
      services.Database.getInstance().removeTeacher(id); 
   }
   public void resetPassword(User user, String newPassword) {
      user.setPassword(newPassword);
      System.out.println("Password reset for " + user.getLogin());
   }

   @Override
   public void printInfo() {
      super.printInfo();
      System.out.println("Role: Administrator");
   }
}