package patterns;

import java.util.ArrayList;
import java.util.List;

import interfaces.Observer;

public class NotificationService {
   private static NotificationService instance;

   private List<Observer> observers = new ArrayList<>();

   private NotificationService() {
   }

   public static NotificationService getInstance() {
      if (instance == null) {
         instance = new NotificationService();
      }
      return instance;
   }

   public void subscribe(Observer observer) {
      if (!observers.contains(observer)) {
         observers.add(observer);
      }
   }

   public void unsubscribe(Observer observer) {
      observers.remove(observer);
   }

   public void notify(String eventType, Object data) {
      for (Observer o : observers) {
         o.update(eventType, data);
      }
   }

   public int getSubscribersCount() {
      return observers.size();
   }
}
