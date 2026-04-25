package interfaces;

import models.ResearchPaper;

import java.util.Comparator;
import java.util.List;

public interface Researcher {
   void addPaper(ResearchPaper paper);
   List<ResearchPaper> getPapers();
   int getHIndex();
   void printPapers(Comparator<ResearchPaper> comparator);
}