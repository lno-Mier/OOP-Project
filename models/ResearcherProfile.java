package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResearcherProfile implements Serializable {
   private static final long serialVersionUID = 1L;

   private List<ResearchPaper> papers = new ArrayList<>();
   private List<ResearchProject> projects = new ArrayList<>();

   public List<ResearchPaper> getPapers() { 
      return papers; 
   }
   public List<ResearchProject> getProjects() { 
      return projects; 
   }

   public void addPaper(ResearchPaper paper) { 
      papers.add(paper); 
   }
   public void addProject(ResearchProject project) { 
      projects.add(project); 
   }

   // h-index: наибольшее h, при котором h статей имеют >= h цитирований
   public int getHIndex() {
      List<Integer> cited = new ArrayList<>();
      for (ResearchPaper p : papers)
         cited.add(p.getCitations());
      cited.sort((a, b) -> b - a);  // сортировка по убыванию

      int h = 0;
      for (int i = 0; i < cited.size(); i++) {
         if (cited.get(i) >= i + 1) 
            h = i + 1;
         else 
            break;
      }
      return h;
   }

   public void printPapers() {
      if (papers.isEmpty()) {
         System.out.println("  No papers."); 
         return;
      }  
      for (ResearchPaper p : papers)
         System.out.println("  " + p);
   }

   public void printProjects() {
      if (projects.isEmpty()) { 
         System.out.println("  No projects."); 
         return; 
      }
      for (ResearchProject p : projects)
         System.out.println("  " + p);
   }
}