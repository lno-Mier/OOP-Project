package models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import enums.School;
import interfaces.Observer;
import interfaces.Researcher;

/**
 * Represents an employee whose sole role is research — neither a
 * teacher nor a manager.
 * <p>
 * The task specification states: "there can be an employee who is
 * neither a Teacher nor Student, but he is a Researcher". This class
 * models that case.
 * <p>
 * Unlike {@link Student} and {@link Teacher}, which receive a
 * researcher profile lazily, an EmployeeResearcher always has a
 * {@link ResearcherProfile} — being a researcher is its only purpose.
 * <p>
 * Implements {@link Researcher} (research behavior) and
 * {@link Observer} (to receive notifications).
 */
public class EmployeeResearcher extends Employee implements Researcher, Observer {
    private static final long serialVersionUID = 1L;

    /** Mandatory research profile — never null for this class. */
    private ResearcherProfile researcherProfile;

    /**
     * Creates a new employee researcher. The research profile is
     * initialized immediately, since research is the employee's only
     * role.
     *
     * @param id         unique identifier
     * @param firstName  first name
     * @param lastName   last name
     * @param login      login
     * @param password   password
     * @param salary     monthly salary
     * @param department school the researcher is affiliated with
     */
    public EmployeeResearcher(String id, String firstName, String lastName, String login, String password, double salary, School department) {
        super(id, firstName, lastName, login, password, salary, department);
        this.researcherProfile = new ResearcherProfile();
    }

    /** @return the researcher profile (never null) */
    public ResearcherProfile getResearcherProfile() {
        return researcherProfile;
    }

    /** Adds a research paper to the profile. */
    @Override
    public void addPaper(ResearchPaper paper) {
        researcherProfile.addPaper(paper);
    }

    /** Adds a research project to the profile. */
    @Override
    public void addProject(ResearchProject project) {
        researcherProfile.addProject(project);
    }

    /** @return live list of papers from the profile */
    @Override
    public List<ResearchPaper> getPapers() {
        return researcherProfile.getPapers();
    }

    /** @return live list of projects from the profile */
    @Override
    public List<ResearchProject> getProjects() {
        return researcherProfile.getProjects();
    }

    /** @return h-index computed from the profile */
    @Override
    public int getHIndex() {
        return researcherProfile.getHIndex();
    }

    /**
     * Prints the researcher's papers, sorted by the provided
     * comparator.
     *
     * @param comparator ordering to apply before printing
     */
    @Override
    public void printPapers(Comparator<ResearchPaper> comparator) {
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
     * Prints the researcher's full profile, including h-index and
     * total number of papers.
     */
    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("Role: Employee Researcher");
        System.out.println("H-index: " + getHIndex());
        System.out.println("Papers: " + getPapers().size());
    }
}
