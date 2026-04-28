package models;

import java.util.ArrayList;
import java.util.List;

import enums.School;
import enums.TeacherTitle;
import services.Database;


public class Teacher extends Employee {
    private static final long serialVersionUID = 1L;

    private TeacherTitle teacherTitle; // Должность преподавателя, например: Лектор, Профессор и т.д.
    private List<Course> courses;      // Список курсов который ведет этот преподаватель
    private ResearcherProfile researcherProfile;  // null если не исследователь

    public Teacher(String id, String firstName, String lastName, String login, String password, double salary, School department, TeacherTitle teacherTitle, List<Course> courses) {

        super(id, firstName, lastName, login, password, salary, department);
        this.teacherTitle = teacherTitle;
        // Инициализируем пустой список курсов, на всякий случай крч
        this.courses = courses; // new ArrayList<>();
    }

    public TeacherTitle getTeacherTitle() {
        return teacherTitle;
    }
    public List<Course> getCourses() {
        return courses;
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
    
    public void addCourse(Course course){
        this.courses.add(course);
    }

    public void sendComplaint(String title, String text) {
        Complaint complaint = new Complaint(this, title, text);
        Database.getInstance().addComplaint(complaint);  // сохранить
        System.out.println("Complaint [" + title + "] was registered.");
    }

    @Override
    public void printInfo() {
        super.printInfo(); // для того чтобы вывести общую информацию об Employee
        System.out.println("Title: " + teacherTitle);
        System.out.println("Number of courses: " + courses.size());
    }
}
