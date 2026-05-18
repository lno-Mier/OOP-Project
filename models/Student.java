package models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import enums.School;
import exceptions.CreditLimitException;
import exceptions.LowHIndexException;
import exceptions.TooManyFailsException;
import interfaces.Observer;
import interfaces.Researcher;

/**
 * Represents a student of the university.
 * <p>
 * Implements {@link Researcher} via composition with a
 * {@link ResearcherProfile}: the profile is {@code null} until the
 * student becomes a researcher (e.g. starts adding papers), at which
 * point it is created lazily.
 * <p>
 * Implements {@link Observer} to receive notifications from the
 * notification service (e.g. when the manager publishes news).
 * <p>
 * Enforces three business rules from the task specification:
 * <ul>
 *   <li>Total credits per semester cannot exceed 21.</li>
 *   <li>A student who has failed 3 or more courses cannot register
 *       for new ones.</li>
 *   <li>Only 4th-year students may pick a supervisor, and the
 *       supervisor must have h-index at least 3.</li>
 * </ul>
 */
public class Student extends User implements Researcher, Observer {
    private static final long serialVersionUID = 1L;

    /** Current grade-point average. */
    private Double gpa;

    /** Year of study (1..4). */
    private int year;

    /** School (faculty) the student is enrolled in. */
    private School major;

    /** Courses the student is currently registered for. */
    private List<Course> registeredCourses = new ArrayList<>();

    /** Marks earned by the student, keyed by course. */
    private Map<Course, Mark> marks = new HashMap<>();

    /** Research profile; {@code null} when the student is not a researcher. */
    private ResearcherProfile researcherProfile;

    /** Supervisor for 4th-year students; {@code null} otherwise. */
    private Researcher supervisor;

    /** True once the manager has approved this student's registration. */
    private boolean isRegistrationApproved;

    /**
     * Creates a new student. Newly created students are not approved
     * by default — the manager must approve them before login is
     * granted access to the student menu.
     *
     * @param id        unique identifier
     * @param firstName first name
     * @param lastName  last name
     * @param login     login used for authentication
     * @param password  password used for authentication
     * @param gpa       initial GPA
     * @param year      year of study
     * @param major     student's school (faculty)
     */
    public Student(String id, String firstName, String lastName, String login, String password, Double gpa, int year, School major) {
        super(id, firstName, lastName, login, password);
        this.gpa = gpa;
        this.year = year;
        this.major = major;
        this.isRegistrationApproved = false;
    }

    /** @return current GPA */
    public Double getGpa() {
        return gpa;
    }

    /** @return current year of study */
    public int getYear() {
        return year;
    }

    /** @return the student's school */
    public School getMajor() {
        return major;
    }

    /** @return live list of courses the student is registered for */
    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    /** @return map of marks keyed by course */
    public Map<Course, Mark> getMarks() {
        return marks;
    }

    /**
     * Looks up the existing mark for a course.
     *
     * @param course course to look up
     * @return the mark, or {@code null} if none has been created yet
     */
    public Mark getMarkFor(Course course) {
        return marks.get(course);
    }

    /**
     * Returns the mark for the given course, creating an empty one
     * if it does not exist yet. Used by Teacher when entering marks.
     *
     * @param course course the mark belongs to
     * @return existing or newly created Mark
     */
    public Mark getOrCreateMark(Course course) {
        Mark m = marks.get(course);
        if (m == null) {
            m = new Mark();
            marks.put(course, m);
        }
        return m;
    }

    /** @return the student's research profile, or {@code null} */
    public ResearcherProfile getResearcherProfile() {
        return researcherProfile;
    }

    /** @return the student's supervisor, or {@code null} */
    public Researcher getSupervisor() {
        return supervisor;
    }

    /** @return {@code true} if the student has a researcher profile */
    public boolean isResearcher() {
        return researcherProfile != null;
    }

    /**
     * Promotes the student to a researcher by creating an empty
     * researcher profile if one does not exist yet.
     */
    public void makeResearcher() {
        if (researcherProfile == null) {
            researcherProfile = new ResearcherProfile();
        }
    }

    /**
     * @return {@code true} if the manager has approved this student's
     *         registration in the system
     */
    public boolean isRegistrationApproved() {
        return isRegistrationApproved;
    }

    /**
     * Updates the student's registration-approval flag. Typically
     * called by the manager from the management menu.
     *
     * @param isRegistrationApproved new value of the flag
     */
    public void setRegistrationApproved(boolean isRegistrationApproved) {
        this.isRegistrationApproved = isRegistrationApproved;
    }

    /**
     * Assigns a supervisor to this student. The supervisor must be a
     * researcher with sufficient h-index.
     *
     * @param supervisor the chosen researcher
     * @throws IllegalStateException if the student is not in 4th year
     * @throws LowHIndexException    if the supervisor's h-index is below 3
     */
    public void setSupervisor(Researcher supervisor) {
        if (this.year != 4) {
            throw new IllegalStateException("Only 4th year students can have a supervisor.");
        }
        if (supervisor.getHIndex() < 3) {
            throw new LowHIndexException("Supervisor must have h-index >= 3. Got: " + supervisor.getHIndex());
        }
        this.supervisor = supervisor;
    }

    /**
     * Sums credits across all currently registered courses.
     *
     * @return total number of credits for the current semester
     */
    public int getTotalCredits() {
        int total = 0;
        for (Course c : registeredCourses) {
            total += c.getCredits();
        }
        return total;
    }

    /**
     * Counts how many marks are below the passing threshold.
     *
     * @return number of failed courses
     */
    public int getFailCount() {
        int fails = 0;
        for (Map.Entry<Course, Mark> entry : marks.entrySet()) {
            if (entry.getValue().isFail()) {
                fails++;
            }
        }
        return fails;
    }

    /**
     * Registers the student for a course, after checking business
     * rules. The course's student list is updated as well.
     *
     * @param course course to register for
     * @throws CreditLimitException   if total credits would exceed 21
     * @throws TooManyFailsException  if the student already has 3+ fails
     */
    public void registerForCourse(Course course) {
        if (getTotalCredits() + course.getCredits() > 21) {
            throw new CreditLimitException("Cannot register: credit limit (21) would be exceeded.");
        }
        if (getFailCount() >= 3) {
            throw new TooManyFailsException("Cannot register: already failed 3 or more courses.");
        }
        if (registeredCourses.contains(course)) {
            System.out.println("Already registered in this course.");
            return;
        }
        registeredCourses.add(course);
        course.addStudent(this);
    }

    /**
     * Submits a rating for the given teacher. Ratings must be between
     * 1 and 5 inclusive; invalid values are rejected without throwing.
     *
     * @param teacher teacher to rate
     * @param rating  numeric rating in range [1, 5]
     */
    public void rateTeacher(Teacher teacher, double rating) {
        if (rating < 1 || rating > 5) {
            System.out.println("Rating must be between 1 and 5.");
            return;
        }
        teacher.addRating(rating);
    }

    /**
     * Adds a research paper to the student's profile, creating the
     * profile if needed.
     */
    @Override
    public void addPaper(ResearchPaper paper) {
        if (researcherProfile == null) {
            researcherProfile = new ResearcherProfile();
        }
        researcherProfile.addPaper(paper);
    }

    /**
     * Adds a research project to the student's profile, creating the
     * profile if needed.
     */
    @Override
    public void addProject(ResearchProject project) {
        if (researcherProfile == null) {
            researcherProfile = new ResearcherProfile();
        }
        researcherProfile.addProject(project);
    }

    /**
     * @return the list of papers from the researcher profile, or an
     *         empty list if the student is not a researcher
     */
    @Override
    public List<ResearchPaper> getPapers() {
        if (researcherProfile == null) {
            return new ArrayList<>();
        }
        return researcherProfile.getPapers();
    }

    /**
     * @return the list of projects from the researcher profile, or an
     *         empty list if the student is not a researcher
     */
    @Override
    public List<ResearchProject> getProjects() {
        if (researcherProfile == null) {
            return new ArrayList<>();
        }
        return researcherProfile.getProjects();
    }

    /**
     * @return h-index computed from the researcher profile, or 0 if
     *         the student is not a researcher
     */
    @Override
    public int getHIndex() {
        if (researcherProfile == null) {
            return 0;
        }
        return researcherProfile.getHIndex();
    }

    /**
     * Prints the student's papers, sorted by the provided comparator.
     * If the student is not a researcher, prints a notice instead.
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
     * Receives a notification from the notification service. Implements
     * the {@link Observer} pattern: when the manager publishes news,
     * every subscribed user — including students — gets {@code update}
     * called.
     *
     * @param eventType type of the event (e.g. "NEWS")
     * @param data      payload describing the event
     */
    @Override
    public void update(String eventType, Object data) {
        System.out.println("[Notification to " + getFirstName() + "] " + eventType + ": " + data);
    }

    /**
     * Prints the student's full profile, including the researcher
     * status and h-index when applicable.
     */
    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("GPA: " + gpa);
        System.out.println("Year: " + year);
        System.out.println("Major: " + major);
        if (isResearcher()) {
            System.out.println("Researcher (h-index: " + getHIndex() + ")");
        }
    }
}
