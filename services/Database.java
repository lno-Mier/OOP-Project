    package services;

    import java.util.ArrayList;
    import java.util.List;

    import models.*;

    public class Database {
        private static Database instance;
        
        private List<Manager> managers;
        private List<News> news;
        private List<Request> requests;
        private List<Student> students;
        private List<Teacher> teachers;
        private List<Course> courses;

        private Database(){
            managers = new ArrayList<>();
            news = new ArrayList<>();
            requests = new ArrayList<>();
            students = new ArrayList<>();
            teachers = new ArrayList<>();
            courses = new ArrayList<>();
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


        //Методы для Студентов
        public void addStudent(Student student){
            this.students.add(student);
        }
        public void removeStudent(String id){
            this.students.removeIf(n -> n.getId().equals(id));
        }
        public List<Student> getStudents(){
            return students;
        }


        //Методы для учителей
        public void addTeacher(Teacher teacher){
            this.teachers.add(teacher);
        }
        public void removeTeacher(String id){
            this.teachers.removeIf(n -> n.getId().equals(id));
        }
        public List<Teacher> getTeachers(){
            return teachers;
        }


        //Методы для Курсов
        public void addCourse(Course course){
            this.courses.add(course);
        }
        public void removeCourse(String courseId){
            this.courses.removeIf(n -> n.getCourseId().equals(courseId));
        }
        public List<Course> getCourses(){
            return courses;
        }


        public static Database getInstance(){
            if (instance == null){
                instance = new Database();
            }
            return instance;
        }
    }
