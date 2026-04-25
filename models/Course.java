package models;

import enums.School;

public class Course {
    private String courseId;
    private String courseName;
    private int credits;
    private School major;
    private int yearToStudy;

    public Course(String courseId, String courseName, int credits, School major, int yearToStudy){
        this.courseId = courseId;
        this.courseName = courseName;
        this.credits = credits;
        this.major = major;
        this.yearToStudy = yearToStudy;
    }

    public String getCourseId() {
        return courseId;
    }
    public String getCourseName(){
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
}
