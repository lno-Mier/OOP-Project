package models;

import java.util.ArrayList;
import java.util.List;

import enums.School;

public abstract class Employee extends User {
    private static final long serialVersionUID = 1L;

    private double salary;
    private School department;
    private List<Message> inbox = new ArrayList<>();

    public Employee(String id, String firstName, String lastName, String login, String password, double salary, School department) {
        super(id, firstName, lastName, login, password);
        this.salary = salary;
        this.department = department;
    }

    // Геттеры
    public double getSalary() {
        return salary;
    }

    public School getDepartment() {
        return department;
    }

    public List<Message> getInbox() {
        return inbox;
    }

    public void receiveMessage(Message msg) {
        inbox.add(msg);
    }

    public void sendMessage(Employee to, String text) {
        to.receiveMessage(new Message(this, to, text));
    }

    @Override
    public void printInfo() {
        super.printInfo(); // Выведет ID, Name
        System.out.println("Department: " + department);
    }
}