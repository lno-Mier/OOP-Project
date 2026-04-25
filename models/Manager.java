package models;

import java.util.List;

import enums.ManagerType;
import enums.RequestStatus;
import enums.School;
import services.Database;

public class Manager extends Employee {
    private ManagerType managerType;

    public Manager(String id, String firstName, String lastName, String login, String password, Double salary, School department, ManagerType managerType) {
        super(id, firstName, lastName, login, password, salary, department);
        this.managerType = managerType;
    }

    public ManagerType getManagerType() {
        return managerType;
    }

    //Методы с новостями для Менеджера
    public void addNews(News news){
        Database.getInstance().addNews(news);
    }
    public void removeNews(String nId){
        Database.getInstance().removeNews(nId);
    }
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

    @Override
    public void displayInfo(){
        super.displayInfo();
        System.out.println("Manager Type: " + managerType);
    }
}
