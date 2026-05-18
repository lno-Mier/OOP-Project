package services;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import enums.CourseStatus;
import enums.ManagerType;
import enums.ResearchField;
import enums.School;
import enums.TeacherTitle;
import models.Admin;
import models.Complaint;
import models.Course;
import models.EmployeeResearcher;
import models.Manager;
import models.News;
import models.Request;
import models.ResearchPaper;
import models.ResearchProject;
import models.Student;
import models.Teacher;
import models.User;

public class Database implements Serializable {
    private static final long serialVersionUID = 1L;

    private static Database instance;
    private static final String DATA_FILE = "database.dat";

    private List<Admin> admins = new ArrayList<>();
    private List<Manager> managers = new ArrayList<>();
    private List<Teacher> teachers = new ArrayList<>();
    private List<Student> students = new ArrayList<>();
    private List<EmployeeResearcher> employeeResearchers = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();
    private List<News> news = new ArrayList<>();
    private List<Complaint> complaints = new ArrayList<>();
    private List<Request> requests = new ArrayList<>();
    private List<ResearchProject> projects = new ArrayList<>();

    private Database() {
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
            instance.seed();
        }
        return instance;
    }

    public User authenticate(String login, String password) {
        for (Admin a : admins) {
            if (a.getLogin().equals(login) && a.checkPassword(password)) {
                return a;
            }
        }
        for (Manager m : managers) {
            if (m.getLogin().equals(login) && m.checkPassword(password)) {
                return m;
            }
        }
        for (Teacher t : teachers) {
            if (t.getLogin().equals(login) && t.checkPassword(password)) {
                return t;
            }
        }
        for (Student s : students) {
            if (s.getLogin().equals(login) && s.checkPassword(password)) {
                return s;
            }
        }
        for (EmployeeResearcher er : employeeResearchers) {
            if (er.getLogin().equals(login) && er.checkPassword(password)) {
                return er;
            }
        }
        return null;
    }

    public User findUserByLogin(String login) {
        for (Admin a : admins) {
            if (a.getLogin().equals(login)) {
                return a;
            }
        }
        for (Manager m : managers) {
            if (m.getLogin().equals(login)) {
                return m;
            }
        }
        for (Teacher t : teachers) {
            if (t.getLogin().equals(login)) {
                return t;
            }
        }
        for (Student s : students) {
            if (s.getLogin().equals(login)) {
                return s;
            }
        }
        for (EmployeeResearcher er : employeeResearchers) {
            if (er.getLogin().equals(login)) {
                return er;
            }
        }
        return null;
    }

    public void addAdmin(Admin admin) {
        admins.add(admin);
    }

    public void removeAdmin(String id) {
        admins.removeIf(a -> a.getId().equals(id));
    }

    public List<Admin> getAdmins() {
        return admins;
    }

    public void addManager(Manager m) {
        managers.add(m);
    }

    public void removeManager(String id) {
        managers.removeIf(m -> m.getId().equals(id));
    }

    public List<Manager> getManagers() {
        return managers;
    }

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
        for (Teacher t : teachers) {
            if (t.getId().equals(id)) {
                return t;
            }
        }
        return null;
    }

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
        for (Student s : students) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    public void addEmployeeResearcher(EmployeeResearcher er) {
        employeeResearchers.add(er);
    }

    public void removeEmployeeResearcher(String id) {
        employeeResearchers.removeIf(e -> e.getId().equals(id));
    }

    public List<EmployeeResearcher> getEmployeeResearchers() {
        return employeeResearchers;
    }

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
        for (Course c : courses) {
            if (c.getCourseId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    public void addNews(News n) {
        news.add(n);
    }

    public void removeNews(String nId) {
        news.removeIf(n -> n.getnId().equals(nId));
    }

    public List<News> getNews() {
        return news;
    }

    public void addComplaint(Complaint c) {
        complaints.add(c);
    }

    public List<Complaint> getComplaints() {
        return complaints;
    }

    public void addRequest(Request r) {
        requests.add(r);
    }

    public void removeRequest(String rId) {
        requests.removeIf(r -> r.getrId().equals(rId));
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void addProject(ResearchProject p) {
        projects.add(p);
    }

    public List<ResearchProject> getProjects() {
        return projects;
    }

    public void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(instance);
            System.out.println("[Database] Data saved.");
        } catch (IOException e) {
            System.err.println("[Database] Save failed: " + e.getMessage());
        }
    }

    public static boolean load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            instance = (Database) ois.readObject();
            System.out.println("[Database] Data loaded from " + DATA_FILE);
            return true;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("[Database] No saved data found, using defaults.");
            return false;
        }
    }

    private void seed() {
        Admin admin = new Admin("A001", "System", "Admin", "admin", "admin123");
        admins.add(admin);

        Manager manager = new Manager("M001", "Aizat", "Nurova", "manager1", "pass123", 250000.0, School.SITE, ManagerType.DEPARTMENT);
        managers.add(manager);

        Teacher t1 = new Teacher("T001", "Akhmet", "Baizhanov", "prof_akhmet", "pass123", 400000, School.SITE, TeacherTitle.PROFESSOR, new ArrayList<>());
        Teacher t2 = new Teacher("T002", "Zarina", "Usupova", "lect_zarina", "pass123", 300000, School.SITE, TeacherTitle.LECTOR, new ArrayList<>());

        ResearchPaper paper1 = new ResearchPaper("P001", "OOP in Modern Java", List.of("Akhmet Baizhanov"), 25, "IEEE", 12, LocalDate.of(2023, 6, 15), "10.1109/oop.001", ResearchField.SOFTWARE_ENGINEERING);
        ResearchPaper paper2 = new ResearchPaper("P002", "Design Patterns Revisited", List.of("Akhmet Baizhanov"), 8, "ACM", 18, LocalDate.of(2024, 1, 10), "10.1109/oop.002", ResearchField.SOFTWARE_ENGINEERING);
        ResearchPaper paper3 = new ResearchPaper("P003", "Inheritance Considered Harmful", List.of("Akhmet Baizhanov"), 5, "IEEE", 10, LocalDate.of(2022, 11, 20), "10.1109/oop.003", ResearchField.SOFTWARE_ENGINEERING);
        t1.addPaper(paper1);
        t1.addPaper(paper2);
        t1.addPaper(paper3);

        teachers.add(t1);
        teachers.add(t2);

        Course oop = new Course("CS101", "Object-Oriented Programming", 6, School.SITE, 2);
        Course ds = new Course("CS201", "Data Structures", 5, School.SITE, 2);
        oop.setStatus(CourseStatus.OPEN);
        ds.setStatus(CourseStatus.OPEN);

        t1.addCourse(oop);
        t1.addCourse(ds);
        oop.addTeacher(t1);
        ds.addTeacher(t1);
        t2.addCourse(oop);
        oop.addTeacher(t2);

        courses.add(oop);
        courses.add(ds);

        Student student1 = new Student("S001", "Arman", "Seitkali", "arman", "pass123", 3.5, 2, School.SITE);
        student1.setRegistrationApproved(true);
        Student student2 = new Student("S002", "Dana", "Bekova", "dana_s", "pass123", 3.8, 4, School.SITE);
        student2.setRegistrationApproved(true);
        Student student3 = new Student("S003", "Bek", "Nurlan", "bek_n", "pass123", 0.0, 1, School.SITE);
        students.add(student1);
        students.add(student2);
        students.add(student3);

        oop.addStudent(student1);
        oop.addStudent(student2);
        ds.addStudent(student1);
        student1.getRegisteredCourses().add(oop);
        student1.getRegisteredCourses().add(ds);
        student2.getRegisteredCourses().add(oop);

        news.add(new News("N001", "Welcome!", "Spring semester has started."));
        news.add(new News("N002", "Exam schedule", "Final exams start May 20."));

        EmployeeResearcher er = new EmployeeResearcher("ER001", "Timur", "Bekov", "researcher1", "pass123", 350000, School.SITE);
        ResearchPaper erPaper = new ResearchPaper("P010", "Deep Learning Foundations", List.of("Timur Bekov"), 12, "IEEE", 8, LocalDate.of(2024, 3, 5), "10.1109/dl.001", ResearchField.AI);
        er.addPaper(erPaper);
        employeeResearchers.add(er);

        System.out.println("[Database] Ready. Test accounts:");
        System.out.println("  admin       / admin123");
        System.out.println("  manager1    / pass123");
        System.out.println("  prof_akhmet / pass123  (Professor, researcher)");
        System.out.println("  lect_zarina / pass123  (Lector)");
        System.out.println("  arman       / pass123  (Student, year 2, approved)");
        System.out.println("  dana_s      / pass123  (Student, year 4, approved)");
        System.out.println("  bek_n       / pass123  (Student, year 1, pending)");
        System.out.println("  researcher1 / pass123  (Employee Researcher)");
    }
}
