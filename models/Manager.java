package models;

import java.util.List;

import enums.CourseStatus;
import enums.ManagerType;
import enums.RequestStatus;
import enums.School;
import services.Database;

public class Manager extends Employee {
    private static final long serialVersionUID = 1L;

    private ManagerType managerType;

    public Manager(String id, String firstName, String lastName, String login, String password, Double salary, School department, ManagerType managerType) {
        super(id, firstName, lastName, login, password, salary, department);
        this.managerType = managerType;
    }

    public ManagerType getManagerType() {
        return managerType;
    }

    // Методы с новостями для Менеджера
    public void addNews(News news){
        Database.getInstance().addNews(news);
    }
    public void removeNews(String nId){
        Database.getInstance().removeNews(nId);
    }
    // Методы с запросами
    public List<Request> viewRequests(){
        return Database.getInstance().getRequests();
    }
    public void approveRequest(String rId) {
        for (Request r : Database.getInstance().getRequests()) {
            if (r.getrId().equals(rId)) {
                r.setStatus(RequestStatus.APPROVED);
                break;
            }
        }
    }
    public void rejectRequest(String rId) {
        for (Request r : Database.getInstance().getRequests()) {
            if (r.getrId().equals(rId)) {
                r.setStatus(RequestStatus.REJECTED);
                break;
            }
        }
    }

    // Методы со студентами
    public boolean approveStudentRegistration(String id){
        for(Student s : Database.getInstance().getStudents()){
            if (s.getId().equals(id)){
                s.setRegistrationApproved(true); 
                return true;
            }
        }
        return false;
    }
    public List<Student> viewStudents(){
        return Database.getInstance().getStudents();
    }

    // Методы с учителями
    public List<Teacher> viewTeachers(){
        return Database.getInstance().getTeachers();
    }
    
    public boolean assignCourseToTeacher(String teacherId, String courseId) {
        Teacher teacher = null;
        for (Teacher t : Database.getInstance().getTeachers()) {
            if (t.getId().equals(teacherId)) {
                teacher = t;
                break;
            }
        }

        Course course = null;
        for (Course c : Database.getInstance().getCourses()) {
            if (c.getCourseId().equals(courseId)) {
                course = c;
                break;
            }
        }

        if (teacher == null || course == null) {
            return false;
        }

        teacher.addCourse(course);
        course.addTeacher(teacher);

        return true;
    }

    public boolean addCourseForRegistration(Course course) {
        if (Database.getInstance().getCourses().stream().anyMatch(c -> c.getCourseId().equals(course.getCourseId()))) {
            return false;
        }
        course.setStatus(CourseStatus.OPEN);
        Database.getInstance().addCourse(course);
        return true;
    }


    // Работа с жалобами (просмотр)
    public List<Complaint> viewComplaints() {
        return Database.getInstance().getComplaints();
    }

    @Override
    public void printInfo(){
        super.printInfo();
        System.out.println("Manager Type: " + managerType);
    }
}