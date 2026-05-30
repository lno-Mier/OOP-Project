package models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Complaint implements Serializable {
    private static final long serialVersionUID = 1L;

    private String complaintId;

    private Employee sender;
    private String title;   
    private String text;    
    private LocalDateTime sentAt; // Время отправки

    public Complaint(String complaintId,Employee sender, String title, String text) {
        this.complaintId = complaintId;
        this.sender = sender;
        this.title = title;
        this.text = text;
        this.sentAt = LocalDateTime.now(); // Для установки текущего времени
    }
    //Тут изменил если что
    public String getComplaintId() {
        return complaintId;
    }
    public Employee getSender() { return sender; }
    public String getTitle() { return title; }
    public String getText() { return text; }
    public LocalDateTime getSentAt() { return sentAt; }

    @Override
    public String toString() {
        return "Complaint: " + title + " from " + sender.getFirstName() + " " + sender.getLastName();
    }
}