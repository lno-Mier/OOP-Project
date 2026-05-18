package models;

import java.io.Serializable;
import java.time.LocalDateTime;

import enums.RequestStatus;

public class Request implements Serializable {
    private static final long serialVersionUID = 1L;

    private String rId;
    private Employee author;
    private String description;
    private RequestStatus status;
    private LocalDateTime createdAt;

    public Request(String rId, Employee author, String description) {
        this.rId = rId;
        this.author = author;
        this.description = description;
        this.status = RequestStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

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

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s by %s — %s", rId, description, author.getLogin(), status);
    }
}
