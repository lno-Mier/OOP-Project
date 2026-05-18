package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import enums.CourseStatus;
import enums.School;

public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    private String courseId;
    private String courseName;
    private int credits;
    private School major;
    private int yearToStudy;
    private CourseStatus status;
    private List<Lesson> lessons;
    private List<Student> students;
    private List<Teacher> teachers;

    public Course(String courseId, String courseName, int credits, School major, int yearToStudy) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credits = credits;
        this.major = major;
        this.yearToStudy = yearToStudy;
        this.status = CourseStatus.PENDING;
        this.lessons = new ArrayList<>();
        this.students = new ArrayList<>();
        this.teachers = new ArrayList<>();
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public School getMajor() {
        return major;
    }

    public int getCredits() {
        return credits;
    }

    public int getYearToStudy() {
        return yearToStudy;
    }

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

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void addTeacher(Teacher teacher) {
        if (!this.teachers.contains(teacher)) {
            this.teachers.add(teacher);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Course course = (Course) o;
        return Objects.equals(courseId, course.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (%d credits, %s)", courseId, courseName, credits, status);
    }
}
