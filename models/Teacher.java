package models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import enums.School;
import enums.TeacherTitle;
import interfaces.Observer;
import interfaces.Researcher;
import services.Database;

/**
 * Represents a teacher of the university.
 * <p>
 * Implements {@link Researcher} via composition with a
 * {@link ResearcherProfile}. Following the task rule that
 * "professors are always researchers", the profile is automatically
 * created when the teacher's title is {@link TeacherTitle#PROFESSOR}.
 * For other titles, the teacher becomes a researcher only on explicit
 * promotion via {@link #makeResearcher()}.
 * <p>
 * Also implements {@link Observer} to receive notifications such as
 * news announcements from the manager.
 */
public class Teacher extends Employee implements Researcher, Observer {
    private static final long serialVersionUID = 1L;

    /** Academic title (e.g. LECTOR, PROFESSOR). */
    private TeacherTitle teacherTitle;

    /** Courses currently taught by the teacher. */
    private List<Course> courses;

    /** Research profile; {@code null} when the teacher is not a researcher. */
    private ResearcherProfile researcherProfile;

    /** Ratings collected from students. */
    private List<Double> ratings = new ArrayList<>();

    /**
     * Creates a new teacher. If the title is PROFESSOR, the researcher
     * profile is initialized immediately so the teacher becomes a
     * researcher by default.
     *
     * @param id           unique identifier
     * @param firstName    first name
     * @param lastName     last name
     * @param login        login
     * @param password     password
     * @param salary       monthly salary
     * @param department   school the teacher belongs to
     * @param teacherTitle academic title
     * @param courses      initial list of courses (may be empty)
     */
    public Teacher(String id, String firstName, String lastName, String login, String password, double salary, School department, TeacherTitle teacherTitle, List<Course> courses) {
        super(id, firstName, lastName, login, password, salary, department);
        this.teacherTitle = teacherTitle;
        this.courses = courses;
        if (teacherTitle == TeacherTitle.PROFESSOR) {
            this.researcherProfile = new ResearcherProfile();
        }
    }

    /** @return the teacher's academic title */
    public TeacherTitle getTeacherTitle() {
        return teacherTitle;
    }

    /** @return live list of courses taught by the teacher */
    public List<Course> getCourses() {
        return courses;
    }

    /** @return the teacher's research profile, or {@code null} */
    public ResearcherProfile getResearcherProfile() {
        return researcherProfile;
    }

    /** @return live list of ratings submitted by students */
    public List<Double> getRatings() {
        return ratings;
    }

    /** @return {@code true} if the teacher has a researcher profile */
    public boolean isResearcher() {
        return researcherProfile != null;
    }

    /**
     * Promotes the teacher to a researcher by creating an empty
     * researcher profile if one does not exist yet. Called when a
     * non-professor teacher needs to start publishing papers.
     */
    public void makeResearcher() {
        if (researcherProfile == null) {
            researcherProfile = new ResearcherProfile();
        }
    }

    /**
     * Adds a course to the teacher's load.
     *
     * @param course course to assign
     */
    public void addCourse(Course course) {
        this.courses.add(course);
    }

    /**
     * Appends a student rating. Ratings are not range-checked here;
     * the {@link Student#rateTeacher(Teacher, double)} method validates
     * the input.
     *
     * @param rating rating value
     */
    public void addRating(double rating) {
        ratings.add(rating);
    }

    /**
     * Computes the arithmetic mean of all collected ratings.
     *
     * @return average rating, or 0.0 if no ratings have been received
     */
    public double getAverageRating() {
        if (ratings.isEmpty()) {
            return 0.0;
        }
        double sum = 0;
        for (Double r : ratings) {
            sum += r;
        }
        return sum / ratings.size();
    }

    /**
     * Creates a complaint authored by this teacher and registers it
     * with the database. The complaint receives a freshly generated
     * UUID as its identifier.
     *
     * @param title short title of the complaint
     * @param text  body of the complaint
     */
    public void sendComplaint(String title, String text) {
        String complaintId = UUID.randomUUID().toString();
        Complaint complaint = new Complaint(complaintId, this, title, text);
        Database.getInstance().addComplaint(complaint);
        System.out.println("Complaint [" + title + "] was registered by " + this.getFirstName());
    }

    /**
     * Adds a research paper, creating the profile on first call.
     */
    @Override
    public void addPaper(ResearchPaper paper) {
        if (researcherProfile == null) {
            researcherProfile = new ResearcherProfile();
        }
        researcherProfile.addPaper(paper);
    }

    /**
     * Adds a research project, creating the profile on first call.
     */
    @Override
    public void addProject(ResearchProject project) {
        if (researcherProfile == null) {
            researcherProfile = new ResearcherProfile();
        }
        researcherProfile.addProject(project);
    }

    /**
     * @return list of papers, or empty list when not a researcher
     */
    @Override
    public List<ResearchPaper> getPapers() {
        if (researcherProfile == null) {
            return new ArrayList<>();
        }
        return researcherProfile.getPapers();
    }

    /**
     * @return list of projects, or empty list when not a researcher
     */
    @Override
    public List<ResearchProject> getProjects() {
        if (researcherProfile == null) {
            return new ArrayList<>();
        }
        return researcherProfile.getProjects();
    }

    /**
     * @return h-index of the teacher's profile, or 0 when not a
     *         researcher
     */
    @Override
    public int getHIndex() {
        if (researcherProfile == null) {
            return 0;
        }
        return researcherProfile.getHIndex();
    }

    /**
     * Prints the teacher's papers, sorted by the provided comparator.
     *
     * @param comparator ordering to apply before printing
     */
    @Override
    public void printPapers(Comparator<ResearchPaper> comparator) {
        if (researcherProfile == null) {
            System.out.println("Not a researcher.");
            return;
        }
        List<ResearchPaper> sorted = new ArrayList<>(researcherProfile.getPapers());
        sorted.sort(comparator);
        for (ResearchPaper p : sorted) {
            System.out.println("  " + p);
        }
    }

    /**
     * Receives a notification from the notification service.
     *
     * @param eventType type of the event
     * @param data      payload describing the event
     */
    @Override
    public void update(String eventType, Object data) {
        System.out.println("[Notification to " + getFirstName() + "] " + eventType + ": " + data);
    }

    /**
     * Prints the teacher's full profile, including title, number of
     * courses, average rating, and researcher status.
     */
    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("Title: " + teacherTitle);
        System.out.println("Number of courses: " + courses.size());
        System.out.println("Average rating: " + String.format("%.2f", getAverageRating()));
        if (isResearcher()) {
            System.out.println("Researcher (h-index: " + getHIndex() + ")");
        }
    }
}
