package models;

import java.util.ArrayList;
import java.util.List;

import enums.School;
import enums.TeacherTitle;

public class Teacher extends Employee{
    //Тут тоже джиги, я тут от руки набросал по идеи. Если чет пропустил изменяйте. Бирак насчет изменений в тг пишите

    private TeacherTitle teacherTitle;
    private List<Course> courses;

    public Teacher(String id, String firstName, String lastName, String login, String password, double salary, School department, TeacherTitle teacherTitle, List<Course> courses) {
        super(id, firstName, lastName, login, password, salary, department);
        this.teacherTitle = teacherTitle;
        this.courses = new ArrayList<>();
    }

    //Геттеры
    public TeacherTitle getTeacherTitle() {
        return teacherTitle;
    }
    public List<Course> getCourses() {
        return courses;
    }

    //методы для добавления курса для учителя. Вот это трогать не надо :))
    public void addCourse(Course course){
        this.courses.add(course);
    }

    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("Title: " + teacherTitle);
    }
}
