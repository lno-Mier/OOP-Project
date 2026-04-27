package models;

import java.util.ArrayList;
import java.util.List;

import enums.School;
import enums.TeacherTitle;


public class Teacher extends Employee {

    private TeacherTitle teacherTitle; // Должность преподавателя, например: Лектор, Профессор и т.д.
    private List<Course> courses;      // Список курсов который ведет этот преподаватель


    public Teacher(String id, String firstName, String lastName, String login, String password, double salary, School department, TeacherTitle teacherTitle, List<Course> courses) {

        super(id, firstName, lastName, login, password, salary, department);
        this.teacherTitle = teacherTitle;
        // Инициализируем пустой список курсов, на всякий случай крч
        this.courses = new ArrayList<>();
    }


    public TeacherTitle getTeacherTitle() {
        return teacherTitle;
    }
    
    public List<Course> getCourses() {
        return courses;
    }
    
    
    /**
     * @param course Курс для добавления
     */
    public void addCourse(Course course){
        this.courses.add(course);
    }

    // --- Дополнительные действия преподавателя, которые будут отправлять жалобу в систему (в деканат или менеджерам).
    /**
     * @param title Заголовок жалобы
     * @param text Текст жалобы
     */
    public void sendComplaint(String title, String text) {
        Complaint complaint = new Complaint(this, title, text);
        // Жалоба создается здесь крч, можете добавить логику для того чтобы добавить ее в базу данных на прямую
        System.out.println("Complaint [" + title + "] was registered by " + this.getFirstName());
    }

    @Override
    public void printInfo() {
        super.printInfo(); // для того чтобы вывести общую информацию об Employee
        System.out.println("Title: " + teacherTitle);
        System.out.println("Number of courses: " + courses.size());
    }
}
