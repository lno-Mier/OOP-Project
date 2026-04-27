package models;

import enums.LessonType;


public class Lesson {
    private String topic; 
    private LessonType type; 
    private int durationMinutes; 
    private String roomNumber; 


    public Lesson(String topic, LessonType type, int durationMinutes, String roomNumber) {
        this.topic = topic;
        this.type = type;
        this.durationMinutes = durationMinutes;
        this.roomNumber = roomNumber;
    }

    public String getTopic() { 
        return topic; 
    }
    public LessonType getType() { 
        return type; 
    }
    public int getDurationMinutes() { 
        return durationMinutes; 
    }
    public String getRoomNumber() { 
        return roomNumber;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (Room: %s, %d min)", type, topic, roomNumber, durationMinutes);
    }
}
