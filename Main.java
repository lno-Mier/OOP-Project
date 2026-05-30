import java.util.Scanner;

import interfaces.Observer;
import models.Admin;
import models.EmployeeResearcher;
import models.Manager;
import models.Student;
import models.Teacher;
import models.User;
import patterns.NotificationService;
import services.Database;
import services.Logger;
import ui.AdminMenu;
import ui.ManagerMenu;
import ui.ResearchMenu;
import ui.StudentMenu;
import ui.TeacherMenu;

public class Main {
    public static void main(String[] args) {
        Database.load();
        Database db = Database.getInstance();
        Scanner scanner = new Scanner(System.in);

        registerObservers();

        System.out.println("==========================================");
        System.out.println("   KBTU University System");
        System.out.println("==========================================");

        while (true) {
            System.out.println();
            String login = prompt(scanner, "Login (or 'exit'): ");
            if (login.equalsIgnoreCase("exit")) {
                db.save();
                System.out.println("Goodbye!");
                return;
            }
            String password = prompt(scanner, "Password: ");

            User user = db.authenticate(login, password);
            if (user == null) {
                System.out.println("Wrong login or password. Try again.");
                continue;
            }

            System.out.println("Welcome, " + user.getFirstName() + " " + user.getLastName() + "!");
            Logger.getInstance().log("Logged in", user.getLogin());

            if (user instanceof Admin) {
                new AdminMenu(scanner, (Admin) user).show();
            } else if (user instanceof Manager) {
                new ManagerMenu(scanner, (Manager) user).show();
            } else if (user instanceof Teacher) {
                new TeacherMenu(scanner, (Teacher) user).show();
            } else if (user instanceof Student) {
                new StudentMenu(scanner, (Student) user).show();
            } else if (user instanceof EmployeeResearcher) {
                EmployeeResearcher er = (EmployeeResearcher) user;
                new ResearchMenu(scanner, er, er).show();
            }

            db.save();
            System.out.println("==========================================");
        }
    }

    private static void registerObservers() {
        Database db = Database.getInstance();
        NotificationService ns = NotificationService.getInstance();
        for (Student s : db.getStudents()) {
            ns.subscribe((Observer) s);
        }
        for (Teacher t : db.getTeachers()) {
            ns.subscribe((Observer) t);
        }
        for (EmployeeResearcher er : db.getEmployeeResearchers()) {
            ns.subscribe((Observer) er);
        }
    }

    private static String prompt(Scanner scanner, String label) {
        System.out.print(label);
        return scanner.nextLine().trim();
    }
}
