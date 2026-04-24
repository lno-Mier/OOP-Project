package models;

import enums.ManagerType;
import enums.School;

public class Manager extends Employee {
    private ManagerType managerType;

    public Manager(String id, String firstName, String lastName, String login, String password, Double salary, School department, ManagerType managerType) {
        super(id, firstName, lastName, login, password, salary, department);
        this.managerType = managerType;
    }

    public ManagerType getManagerType() {
        return managerType;
    }

    @Override
    public void displayInfo(){
        super.displayInfo();
        System.out.println("Manager Type: " + managerType);
    }
}
