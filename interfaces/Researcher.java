package interfaces;

import models.ResearchPaper;
import models.ResearchProject;

import java.util.Comparator;
import java.util.List;

public interface Researcher {
   void addPaper(ResearchPaper paper);
   void addProject(ResearchProject project);
   List<ResearchPaper> getPapers();
   List<ResearchProject> getProjects();
   int getHIndex();
   void printPapers(Comparator<ResearchPaper> comparator);
}