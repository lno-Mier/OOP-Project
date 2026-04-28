package services;

import java.util.ArrayList;
import java.util.List;

import enums.*;
import models.*;

public class Database {
   private static Database instance;

   // Сохраняем данные в массивах
   private List<Admin> admins = new ArrayList<>();
   private List<Manager> managers = new ArrayList<>();
   private List<Teacher> teachers = new ArrayList<>();
   private List<Student> students = new ArrayList<>();
   private List<Course> courses = new ArrayList<>();
   private List<News> news = new ArrayList<>();
   private List<Complaint> complaints = new ArrayList<>();
   private List<Request> requests = new ArrayList<>();
   private List<ResearchProject> projects = new ArrayList<>();

   private Database() {
      seed();
   }

   // Точка доступа
   public static Database getInstance() {
      if (instance == null) {
         instance = new Database();
      }
      return instance;
   }

   // Аутентификация - сопоставляем логин и пароль
   public User authenticate(String login, String password) {
      for (Admin a : admins)
         if (a.getLogin().equals(login) && a.checkPassword(password))
            return a;
      for (Manager m : managers)
         if (m.getLogin().equals(login) && m.checkPassword(password))
            return m;
      for (Teacher t : teachers)
         if (t.getLogin().equals(login) && t.checkPassword(password))
            return t;
      for (Student s : students)
         if (s.getLogin().equals(login) && s.checkPassword(password))
            return s;
      return null;
   }

   /* Базовые методы - добавление, удаление, геттеры - для работы с данными */

   // Admins
   public void addAdmin(Admin admin) {
      admins.add(admin);
   }
   public void removeAdmin(String id) {
      admins.removeIf(a -> a.getId().equals(id));
   }
   public List<Admin> getAdmins() {
      return admins;
   }

   // Managers
   public void addManager(Manager m) {
      managers.add(m);
   }
   public void removeManager(String id) {
      managers.removeIf(m -> m.getId().equals(id));
   }
   public List<Manager> getManagers() {
      return managers;
   }

   // Teachers
   public void addTeacher(Teacher t) {
      teachers.add(t);
   }
   public void removeTeacher(String id) {
      teachers.removeIf(m -> m.getId().equals(id));
   }
   public List<Teacher> getTeachers() {
      return teachers;
   }
   public Teacher getTeacherById(String id) {
      for (Teacher t : teachers)
         if (t.getId().equals(id)) return t;
      return null;
   }

   // Students
   public void addStudent(Student s) {
      students.add(s);
   }
   public void removeStudent(String id) {
      students.removeIf(s -> s.getId().equals(id));
   }
   public List<Student> getStudents() {
      return students;
   }

   public Student getStudentById(String id) {
      for (Student s : students)
         if (s.getId().equals(id))
            return s;
      return null;
   }

   // Courses
   public void addCourse(Course c) {
      courses.add(c);
   }
   public void removeCourse(String id) {
      courses.removeIf(c -> c.getCourseId().equals(id));
   }
   public List<Course> getCourses() {
      return courses;
   }

   public Course getCourseById(String id) {
      for (Course c : courses)
         if (c.getCourseId().equals(id))
            return c;
      return null;
   }

   // News
   public void addNews(News n) {
      news.add(n);
   }
   public void removeNews(String nId) { 
      news.removeIf(n -> n.getnId().equals(nId)); 
   }
   public List<News> getNews() {
      return news;
   }

   // Complaints
   public void addComplaint(Complaint c) {
      complaints.add(c);
   }
   public List<Complaint> getComplaints() {
      return complaints;
   }

   // Requests
   public void addRequest(Request r) {
      requests.add(r); 
   }
   public void removeRequest(String rId) {
      requests.removeIf(r -> r.getrId().equals(rId)); 
   }
   public List<Request> getRequests() { 
      return requests; 
   }

   // Projects
   public void addProject(ResearchProject p) { 
      projects.add(p); 
   }
   public List<ResearchProject> getProjects() { 
      return projects; 
   }

   // Начальные данные
   private void seed() {
      // Admins
      Admin admin = new Admin("A001", "System", "Admin", "admin", "admin123");
      admins.add(admin);

      // Managers
      Manager manager = new Manager("M001", "Aizat", "Nurova", "manager1", "pass123", 250000, School.SITE, ManagerType.OR);
      managers.add(manager);

      // Teachers
      Teacher t1 = new Teacher("T001", "Akhmet", "Baizhanov", "prof_akhmet", "pass123", 400000, School.SITE, TeacherTitle.PROFESSOR, new ArrayList<>());
      Teacher t2 = new Teacher("T002", "Zarina", "Usupova", "lect_zarina", "pass123", 300000, School.SITE, TeacherTitle.LECTOR, new ArrayList<>());
      teachers.add(t1);
      teachers.add(t2);

      // Courses
      Course oop = new Course("CS101", "Object-Oriented Programming", 6, School.SITE, 2);
      Course ds = new Course("CS201", "Data Structures", 5, School.SITE, 2);
      oop.setStatus(CourseStatus.OPEN);
      ds.setStatus(CourseStatus.OPEN);

      t1.addCourse(oop);
      t1.addCourse(ds);

      courses.add(oop);
      courses.add(ds);

      // Students
      Student student1 = new Student("S001", "Arman", "Seitkali", "arman", "pass123", 3.5, 2, School.SITE);

      Student student2 = new Student("S002", "Dana", "Bekova", "dana_s", "pass123", 3.8, 4, School.SITE);
      students.add(student1);
      students.add(student2);

      // Добавляем студентов на курсы
      oop.addStudent(student1);
      oop.addStudent(student2);
      ds.addStudent(student1);

      // News
      news.add(new News("N001", "Welcome!", "Spring semester has started."));
      news.add(new News("N002", "Exam schedule", "Final exams start May 20."));

      // EmployeeResearcher
      EmployeeResearcher er = new EmployeeResearcher("ER001", "Timur", "Bekov", "researcher1", "pass123", 350000, School.SITE);
      ResearchPaper paper = new ResearchPaper("P001", "Deep Learning in OOP", List.of("Timur Bekov"), 12, "IEEE", 8, ResearchField.AI);
      er.addPaper(paper);
      
      System.out.println("[Database] Ready. Test accounts:");
      System.out.println("  admin       / admin123");
      System.out.println("  manager1    / pass123");
      System.out.println("  prof_akhmet / pass123");
      System.out.println("  lect_zarina / pass123");
      System.out.println("  arman       / pass123");
      System.out.println("  dana_s      / pass123");
      System.out.println("  researcher1 / pass123");
   }
}