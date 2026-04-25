package models;

import enums.School;

public class Student extends User {
    //Джига если надо сами тут измените. Мне просто для Менеджера надо было это создать.


    private Double gpa;
    private int year;
    private School major;
    private boolean isRegistrationApproved;

    public Student(String id, String firstName, String lastName, String login, String password, Double gpa, int year, School major, boolean isRegistrationApproved) {
        super(id, firstName, lastName, login, password);
        this.gpa = gpa;
        this.year = year;
        this.major = major;
        this.isRegistrationApproved = isRegistrationApproved;
    }

    //Геттеры для студента
    public Double getGpa() {
        return gpa;
    }
    public int getYear() {
        return year;
    }
    public School getMajor() {
        return major;
    }
    public boolean IsRegistrationApproved() {
        return isRegistrationApproved;
    }

    public void setRegistrationApproved(boolean isRegestrationApproved) {
        this.isRegistrationApproved = isRegestrationApproved;
    }

    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("GPA: " + gpa);
        System.out.println("Year: " + year);
    }
}
