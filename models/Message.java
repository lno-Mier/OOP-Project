package models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    private Employee sender;     
    private Employee receiver;   
    private String text;         
    private LocalDateTime sentAt;
 
    public Message(Employee sender, Employee receiver, String text) {
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.sentAt = LocalDateTime.now();
    }

    public Employee getSender() { 
        return sender;
    }
    
    public Employee getReceiver() {
         return receiver; 
    }
    
    public String getText() { 
        return text;
    }
    public LocalDateTime getTime() { 
        return sentAt; 
    }
}
