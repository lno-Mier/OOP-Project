package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import exceptions.NotAResearcherException;
import interfaces.Researcher;

public class ResearchProject implements Serializable {
    private static final long serialVersionUID = 1L;

    private String projectId;
    private String topic;
    private List<ResearchPaper> papers;
    private List<Researcher> participants;

    public ResearchProject(String projectId, String topic) {
        this.projectId = projectId;
        this.topic = topic;
        this.papers = new ArrayList<>();
        this.participants = new ArrayList<>();
    }

    public String getProjectId() {
        return projectId;
    }

    public String getTopic() {
        return topic;
    }

    public List<ResearchPaper> getPapers() {
        return papers;
    }

    public List<Researcher> getParticipants() {
        return participants;
    }

    public void addPaper(ResearchPaper paper) {
        papers.add(paper);
    }

    public void addParticipant(Object user) {
        if (!(user instanceof Researcher)) {
            throw new NotAResearcherException("Cannot join project: user is not a researcher.");
        }
        Researcher researcher = (Researcher) user;
        if (user instanceof Student) {
            Student s = (Student) user;
            if (!s.isResearcher()) {
                throw new NotAResearcherException("Student is not a researcher (no profile).");
            }
        }
        if (user instanceof Teacher) {
            Teacher t = (Teacher) user;
            if (!t.isResearcher()) {
                throw new NotAResearcherException("Teacher is not a researcher (no profile).");
            }
        }
        participants.add(researcher);
    }

    @Override
    public String toString() {
        return String.format("[%s] \"%s\" | papers: %d | participants: %d", projectId, topic, papers.size(), participants.size());
    }
}
