package models;

import java.util.ArrayList;
import java.util.List;

import enums.School;

public class Student extends User {
    private static final long serialVersionUID = 1L;

    private Double gpa;
    private int year;
    private School major;
    private List<Course> registeredCourses = new ArrayList<>();
    // private Transcript transcript;
    private ResearcherProfile researcherProfile;

    public Student(String id, String firstName, String lastName, String login, String password, Double gpa, int year, School major) {
        super(id, firstName, lastName, login, password);
        this.gpa = gpa;
        this.year = year;
        this.major = major;
    }

    // Геттеры
    public Double getGpa() {
        return gpa;
    }
    public int getYear() {
        return year;
    }
    public School getMajor() {
        return major;
    }
    public ResearcherProfile getResearcherProfile() { 
        return researcherProfile; 
    }

    public boolean isResearcher() { 
        return researcherProfile != null; 
    }
    
    public void makeResearcher() {
        if (researcherProfile == null)
            researcherProfile = new ResearcherProfile();
    }

    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("GPA: " + gpa);
        System.out.println("Year: " + year);
    }
}