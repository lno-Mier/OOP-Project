package models;

import java.time.LocalDateTime;

public class News {
    private String nId;
    private String title;
    private String content;
    private LocalDateTime date;

    public News(String nId, String title, String content, LocalDateTime date){
        this.nId = nId;
        this.title = title;
        this.content = content;
        this.date = date;
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
