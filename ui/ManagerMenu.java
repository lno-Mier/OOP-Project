package ui;

import java.util.List;
import java.util.Scanner;

import enums.School;
import models.Complaint;
import models.Course;
import models.Employee;
import models.Manager;
import models.Message;
import models.News;
import models.Request;
import models.Student;
import models.Teacher;
import patterns.FailReport;
import patterns.GpaReport;
import patterns.NotificationService;
import patterns.ReportGenerator;
import services.Logger;

public class ManagerMenu extends Menu {
    private Manager manager;

    public ManagerMenu(Scanner scanner, Manager manager) {
        super(scanner);
        this.manager = manager;
    }

    @Override
    public void show() {
        while (true) {
            printHeader("Manager menu - " + manager.getFirstName() + " " + manager.getLastName());
            System.out.println("1. View Requests");
            System.out.println("2. Approve / Reject Request");
            System.out.println("3. Add News");
            System.out.println("4. Remove News");
            System.out.println("5. View Pending Student Registrations");
            System.out.println("6. Approve Student Registration");
            System.out.println("7. View Students");
            System.out.println("8. View Teachers");
            System.out.println("9. Add Course for Registration");
            System.out.println("10. Assign Course to Teacher");
            System.out.println("11. View Complaints");
            System.out.println("12. Send Message");
            System.out.println("13. View Inbox");
            System.out.println("14. Generate Academic Report");
            System.out.println("0. Logout");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    viewRequests();
                    break;
                case 2:
                    System.out.println("*Are you approve or reject request?*");
                    System.out.println("1. Approve");
                    System.out.println("2. Reject");
                    int secondChoice = readInt("Enter your choice: ");
                    switch (secondChoice) {
                        case 1:
                            String approveId = readLine("Enter Request ID to approve: ");
                            manager.approveRequest(approveId);
                            System.out.println("Request processed.");
                            break;
                        case 2:
                            String rejectId = readLine("Enter Request ID to reject: ");
                            manager.rejectRequest(rejectId);
                            System.out.println("Request processed.");
                            break;
                        default:
                            break;
                    }
                    break;
                case 3:
                    addNews();
                    break;
                case 4:
                    removeNews();
                    break;
                case 5:
                    viewPendingStudents();
                    break;
                case 6:
                    approveStudent();
                    break;
                case 7:
                    viewStudents();
                    break;
                case 8:
                    viewTeachers();
                    break;
                case 9:
                    addCourse();
                    break;
                case 10:
                    assignCourse();
                    break;
                case 11:
                    viewComplaints();
                    break;
                case 12:
                    sendMessage();
                    break;
                case 13:
                    viewInbox();
                    break;
                case 14:
                    generateReport();
                    break;
                case 0:
                    Logger.getInstance().log("Manager logged out", manager.getLogin());
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid Value");
                    break;
            }
        }
    }

    private void viewRequests() {
        System.out.println("\n--- System Requests ---");
        List<Request> requests = manager.viewRequests();
        if (requests.isEmpty()) {
            System.out.println("No requests found.");
            return;
        }
        for (Request r : requests) {
            System.out.printf("ID: %s | From: %s | Status: %s | Date: %s\nDescription: %s\n",
                r.getrId(), r.getAuthor().getFirstName() + " " + r.getAuthor().getLastName(),
                r.getStatus(), r.getCreatedAt(), r.getDescription());
            printDivider();
        }
    }

    private void addNews() {
        String id = readLine("Enter News ID: ");
        String title = readLine("Enter Title: ");
        String content = readLine("Enter Content: ");
        News news = new News(id, title, content);
        manager.addNews(news);
        NotificationService.getInstance().notify("NEWS", title);
        System.out.println("News added.");
        Logger.getInstance().log("Added news " + id, manager.getLogin());
    }

    private void removeNews() {
        String id = readLine("Enter News ID to remove: ");
        manager.removeNews(id);
        System.out.println("News removed.");
    }

    private void viewPendingStudents() {
        System.out.println("\n--- Pending Student Registrations ---");
        manager.viewStudents().stream()
            .filter(s -> !s.isRegistrationApproved())
            .forEach(s -> System.out.println("ID: " + s.getId() + " | Name: " + s.getFirstName() + " " + s.getLastName()));
    }

    private void approveStudent() {
        String id = readLine("Enter Student ID to approve: ");
        boolean result = manager.approveStudentRegistration(id);
        if (result) {
            System.out.println("Registration Approved.");
            Logger.getInstance().log("Approved student " + id, manager.getLogin());
        } else {
            System.out.println("Student with " + id + " is not found.");
        }
    }

    private void viewStudents() {
        System.out.println("\n--- All Students ---");
        for (Student s : manager.viewStudents()) {
            s.printInfo();
            printDivider();
        }
    }

    private void viewTeachers() {
        System.out.println("\n--- All Teachers ---");
        for (Teacher t : manager.viewTeachers()) {
            t.printInfo();
            printDivider();
        }
    }

    private void addCourse() {
        String id = readLine("Enter Course ID: ");
        String name = readLine("Enter Course Name: ");
        int credits = readInt("Enter Credits: ");
        int year = readInt("Enter Year: ");
        System.out.println("Select School: ");

        for (int i = 0; i < School.values().length; i++) {
            System.out.println((i + 1) + ". " + School.values()[i]);
        }

        int schoolChoice = readInt("Choice: ");
        if (schoolChoice <= 0 || schoolChoice > School.values().length) {
            System.out.println("Not Correct Value.");
            return;
        }
        School school = School.values()[schoolChoice - 1];

        Course course = new Course(id, name, credits, school, year);
        if (manager.addCourseForRegistration(course)) {
            System.out.println("Course added successfully.");
            Logger.getInstance().log("Added course " + id, manager.getLogin());
        } else {
            System.out.println("Course ID already exists.");
        }
    }

    private void assignCourse() {
        String tId = readLine("Enter Teacher ID: ");
        String cId = readLine("Enter Course ID: ");
        if (manager.assignCourseToTeacher(tId, cId)) {
            System.out.println("Course assigned successfully.");
        } else {
            System.out.println("Failed to assign course. Check IDs.");
        }
    }

    private void viewComplaints() {
        System.out.println("\n--- Complaints ---");
        List<Complaint> complaints = manager.viewComplaints();
        if (complaints.isEmpty()) {
            System.out.println("No complaints.");
        } else {
            for (Complaint c : complaints) {
                System.out.printf("ID: %s | From: %s | Title: %s\nText: %s\n",
                    c.getComplaintId(), c.getSender().getFirstName(), c.getTitle(), c.getText());
                printDivider();
            }
        }
    }

    private void sendMessage() {
        String receiverId = readLine("Enter receiver ID: ");
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

        if (receiver != null) {
            String text = readLine("Enter message: ");
            manager.sendMessage(receiver, text);
            System.out.println("Message sent.");
        } else {
            System.out.println("Receiver not found.");
        }
    }

    private void viewInbox() {
        System.out.println("\n--- Inbox ---");
        List<Message> inbox = manager.getInbox();
        if (inbox.isEmpty()) {
            System.out.println("Inbox is empty.");
        } else {
            for (Message msg : inbox) {
                System.out.printf("From: %s %s | Time: %s\nText: %s\n",
                    msg.getSender().getFirstName(), msg.getSender().getLastName(),
                    msg.getTime(), msg.getText());
                printDivider();
            }
        }
    }

    private void generateReport() {
        System.out.println("\n1. GPA Report");
        System.out.println("2. Fail Report");
        int choice = readInt("Choice: ");
        ReportGenerator generator;
        if (choice == 1) {
            generator = new ReportGenerator(new GpaReport());
        } else if (choice == 2) {
            generator = new ReportGenerator(new FailReport());
        } else {
            System.out.println("Invalid choice.");
            return;
        }
        System.out.println(generator.generateReport(db.getStudents()));
    }
}
