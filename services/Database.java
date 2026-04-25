package services;

import java.util.ArrayList;
import java.util.List;

import models.Manager;

public class Database {
    private static Database instance;
    
    private List<Manager> managers;

    private Database(){
        managers = new ArrayList<>();
    }

    public static Database getInstance(){
        if (instance == null){
            instance = new Database();
        }
        return instance;
    }
}
