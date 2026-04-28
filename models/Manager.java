package models;

import java.util.List;

import enums.ManagerType;
import enums.RequestStatus;
import enums.School;
import services.Database;

public class Manager extends Employee {
    private static final long serialVersionUID = 1L;

    private ManagerType managerType;

    public Manager(String id, String firstName, String lastName, String login, String password, double salary, School department, ManagerType managerType) {
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
            }
        }
    }

    // Методы со студентами
    // public void approveStudentRegistration(String id){
    //     for(Student s : Database.getInstance().getStudents()){
    //         if (s.getId().equals(id)){
    //             s.setRegistrationApproved(true);
    //         }
    //     }
    // }
    public List<Student> viewStudents(){
        return Database.getInstance().getStudents();
    }

    // Методы с учителями
    public List<Teacher> viewTeachers(){
        return Database.getInstance().getTeachers();
    }
    public void assignCourseToTeacher(String teacherId, Course course){
        for(Teacher t : Database.getInstance().getTeachers()){
            if (t.getId().equals(teacherId)) {
                t.addCourse(course);
                Database.getInstance().addCourse(course);
            }
        }
    }

    @Override
    public void printInfo(){
        super.printInfo();
        System.out.println("Manager Type: " + managerType);
    }
}
