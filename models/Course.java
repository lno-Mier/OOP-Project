package models;

import enums.School;
import enums.CourseStatus;
import java.util.ArrayList;
import java.util.List;


public class Course {
    private String courseId;    
    private String courseName;  
    private int credits;         
    private School major;       
    private int yearToStudy;    

    private CourseStatus status;       // Статус самого курса например:Открыт, Закрыт или Ожидает
    private List<Lesson> lessons;      // Список занятий по курсу
    private List<Student> students;    // Список студентов которые зарегистрированны на сами курсы


    public Course(String courseId, String courseName, int credits, School major, int yearToStudy){
        this.courseId = courseId;
        this.courseName = courseName;
        this.credits = credits;
        this.major = major;
        this.yearToStudy = yearToStudy;
        

        this.status = CourseStatus.PENDING; 
        this.lessons = new ArrayList<>();
        this.students = new ArrayList<>();
    }


    public String getCourseId() { return courseId; }
    public String getCourseName(){ return courseName; }
    public School getMajor() { return major; }
    public int getCredits() { return credits; }
    public int getYearToStudy() { return yearToStudy; }

   
    public CourseStatus getStatus() { 
        return status; 
    }
    

    public void setStatus(CourseStatus status) { 
        this.status = status; 
    }


    public List<Lesson> getLessons() { 
        return lessons;
    }
    

    public void addLesson(Lesson lesson) { 
        this.lessons.add(lesson);
    }


    public List<Student> getStudents() { 
        return students; 
    }
    

    public void addStudent(Student student) { 
        this.students.add(student);
    }
}
