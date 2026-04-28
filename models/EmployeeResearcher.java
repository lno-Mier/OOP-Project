package models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import enums.School;
import interfaces.Researcher;

public class EmployeeResearcher extends Employee implements Researcher {
   private static final long serialVersionUID = 1L;

   private ResearcherProfile researcherProfile;

   public EmployeeResearcher(String id, String firstName, String lastName, String login, String password, double salary, School department) {
      super(id, firstName, lastName, login, password, salary, department);
      this.researcherProfile = new ResearcherProfile();
   }

   public ResearcherProfile getResearcherProfile() { 
      return researcherProfile; 
   }

   // Реализация интерфейса Researcher
   @Override
   public void addPaper(ResearchPaper paper) { 
      researcherProfile.addPaper(paper); 
   }

   @Override
   public void addProject(ResearchProject project) { 
      researcherProfile.addProject(project); 
   }

   @Override
   public List<ResearchPaper> getPapers() { 
      return researcherProfile.getPapers(); 
   }

   @Override
   public List<ResearchProject> getProjects() { 
      return researcherProfile.getProjects(); 
   }

   @Override
   public int getHIndex() { 
      return researcherProfile.getHIndex(); 
   }

   @Override
   public void printPapers(Comparator<ResearchPaper> comparator) {
      List<ResearchPaper> sorted = new ArrayList<>(researcherProfile.getPapers());
      sorted.sort(comparator);
      sorted.forEach(p -> System.out.println("  " + p));
   }

   @Override
   public void printInfo() {
      super.printInfo();
      System.out.println("Role: Employee Researcher");
      System.out.println("H-index: " + getHIndex());
      System.out.println("Papers: " + getPapers().size());
   }
}