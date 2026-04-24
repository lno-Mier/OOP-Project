package models;

import enums.School;

public abstract class Employee extends User {
    private Double salary;
    private School department;

    public Employee(String id, String firstName, String lastName, String login, String password, Double salary, School department) {
        super(id, firstName, lastName, login, password);
        this.salary = salary;
        this.department = department;
    }

    //Геттер для департамента, для зп не нужно
    public Double getSalary() {
        return salary;
    }

    public School getDepartment() {
        return department;
    }

    @Override
    public void displayInfo(){
        System.out.println("Name: " + getFirstName() + " " + getLastName());
        System.out.println("Department: " + department);
    }
}