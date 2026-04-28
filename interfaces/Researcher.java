package interfaces;

import models.ResearchPaper;
import models.ResearchProject;

import java.util.Comparator;
import java.util.List;

public interface Researcher {
   void addPaper(ResearchPaper paper);
   List<ResearchPaper> getPapers();
   int getHIndex();
   void printPapers(Comparator<ResearchPaper> comparator);

   void addProject(ResearchProject project);
   List<ResearchProject> getProjects();
}