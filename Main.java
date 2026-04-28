import models.*;
import services.Database;
import ui.*;

import java.util.Scanner;

public class Main {
   public static void main(String[] args) {
      Database db = Database.getInstance();
      Scanner scanner = new Scanner(System.in);

      System.out.println("==========================================");
      System.out.println("   KBTU University System");
      System.out.println("==========================================");

      while (true) {
         System.out.println();
         String login = prompt(scanner, "Login: ");
         String password = prompt(scanner, "Password: ");

         User user = db.authenticate(login, password);

         if (user == null) {
            System.out.println("Wrong login or password. Try again.");
            continue;
         }

         System.out.println("Welcome, " + user.getFirstName() + " " + user.getLastName() + "!");

         if (user instanceof Admin a) 
            new AdminMenu(scanner, a).show();
         // else if (user instanceof Manager m)
         //    new ManagerMenu(scanner, m).show();
         else if (user instanceof Teacher t)
            new TeacherMenu(scanner, t).show();
         // else if (user instanceof Student s)
         //    new StudentMenu(scanner, s).show();

         // db.save();
         System.out.println("==========================================");
      }
   }

   private static String prompt(Scanner scanner, String label) {
      System.out.print(label);
      return scanner.nextLine().trim();
   }
}