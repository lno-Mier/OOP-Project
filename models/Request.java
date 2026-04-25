package models;

import java.time.LocalDateTime;

import enums.RequestStatus;

public class Request {
    private String rId;
    private Employee author;
    private String description;
    private RequestStatus status;
    private LocalDateTime createdAt;

    public Request(String rId, Employee author, String description, RequestStatus status, LocalDateTime createdAt){
        this.rId = rId;
        this.author = author;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
    }

    //Геттеры
    public String getrId() {
        return rId;
    }
    public Employee getAuthor() {
        return author;
    }
    public String getDescription() {
        return description;
    }
    public RequestStatus getStatus() {
        return status;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    //Сеттеры только для статуса
    public void setStatus(RequestStatus status) {
        this.status = status;
    }
}
