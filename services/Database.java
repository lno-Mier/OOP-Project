    package services;

    import java.util.ArrayList;
    import java.util.List;

    import models.Manager;
    import models.News;
    import models.Request;

    public class Database {
        private static Database instance;
        
        private List<Manager> managers;
        private List<News> news;
        private List<Request> requests;

        private Database(){
            managers = new ArrayList<>();
            news = new ArrayList<>();
            requests = new ArrayList<>();
        }

        //Методы для News
        public void addNews(News newss){
            this.news.add(newss);
        }
        public void removeNews(String nId){
            this.news.removeIf(n -> n.getnId().equals(nId));
        }
        public List<News> getNews(){
            return news;
        }


        //Методы для Request
        public void addRequest(Request reguest){
            this.requests.add(reguest);
        }
        public void removeRequest(String rId){
            this.requests.removeIf(n -> n.getrId().equals(rId));
        }
        public List<Request> getRequests(){
            return requests;
        }


        //Методы для Менеджера
        public void addManager(Manager manager){
            this.managers.add(manager);
        }
        public void removeManager(String id){
            this.managers.removeIf(n -> n.getId().equals(id));
        }
        public List<Manager> getManagers(){
            return managers;
        }


        public static Database getInstance(){
            if (instance == null){
                instance = new Database();
            }
            return instance;
        }
    }
