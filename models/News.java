package models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class News implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nId;
    private String title;
    private String content;
    private LocalDateTime date;

    public News(String nId, String title, String content) {
        this.nId = nId;
        this.title = title;
        this.content = content;
        this.date = LocalDateTime.now();
    }

    //Геттеры
    public String getnId() {
        return nId;
    }
    public String getTitle() {
        return title;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public String getContent() {
        return content;
    }

}
