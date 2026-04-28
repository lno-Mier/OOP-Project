package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResearchProject implements Serializable {
   private static final long serialVersionUID = 1L;

   private String projectId;
   private String topic;
   private List<ResearchPaper> papers;
   private List<User> participants;  // Teacher, Student, EmployeeResearcher

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
   public List<User> getParticipants() { 
      return participants; 
   }

   public void addPaper(ResearchPaper paper) { 
      papers.add(paper); 
   }
   public void addParticipant(User user) { 
      participants.add(user); 
   }

   @Override
   public String toString() {
      return String.format("[%s] \"%s\" | papers: %d | participants: %d", projectId, topic, papers.size(), participants.size());
   }
}