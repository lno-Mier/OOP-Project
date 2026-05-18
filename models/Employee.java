package models;

import java.util.ArrayList;
import java.util.List;

import enums.School;

/**
 * Abstract base class for all university employees.
 * <p>
 * Extends {@link User} with employment-related fields (salary,
 * department) and provides a personal inbox for receiving messages
 * from other employees.
 * <p>
 * Concrete employee types — Teacher, Manager, Admin, and
 * EmployeeResearcher — extend this class. Student does not extend
 * Employee because students are not employees of the university.
 */
public abstract class Employee extends User {
    private static final long serialVersionUID = 1L;

    /** Monthly salary of the employee. */
    private double salary;

    /** School (department) the employee belongs to. */
    private School department;

    /** Inbox of messages received from other employees. */
    private List<Message> inbox = new ArrayList<>();

    /**
     * Creates a new employee.
     *
     * @param id         unique identifier
     * @param firstName  first name
     * @param lastName   last name
     * @param login      login used for authentication
     * @param password   password used for authentication
     * @param salary     monthly salary
     * @param department school the employee belongs to
     */
    public Employee(String id, String firstName, String lastName, String login, String password, double salary, School department) {
        super(id, firstName, lastName, login, password);
        this.salary = salary;
        this.department = department;
    }

    /** @return the employee's monthly salary */
    public double getSalary() {
        return salary;
    }

    /** @return the school (department) the employee belongs to */
    public School getDepartment() {
        return department;
    }

    /** @return the live list of messages received by this employee */
    public List<Message> getInbox() {
        return inbox;
    }

    /**
     * Appends a message to this employee's inbox. Called by the sender
     * via {@link #sendMessage(Employee, String)}.
     *
     * @param msg the message to receive
     */
    public void receiveMessage(Message msg) {
        inbox.add(msg);
    }

    /**
     * Sends a message from this employee to another employee. The
     * message is created with the current timestamp and appended to
     * the recipient's inbox.
     *
     * @param to   recipient of the message
     * @param text body of the message
     */
    public void sendMessage(Employee to, String text) {
        to.receiveMessage(new Message(this, to, text));
    }

    /**
     * Prints the employee's identity (from {@link User#printInfo()})
     * followed by the department.
     */
    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("Department: " + department);
    }
}
