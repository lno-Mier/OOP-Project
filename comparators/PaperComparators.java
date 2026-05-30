package comparators;

import java.util.Comparator;

import models.ResearchPaper;

public class PaperComparators {

    public static final Comparator<ResearchPaper> BY_DATE = new Comparator<ResearchPaper>() {
        @Override
        public int compare(ResearchPaper a, ResearchPaper b) {
            if (a.getDate() == null && b.getDate() == null) {
                return 0;
            }
            if (a.getDate() == null) {
                return 1;
            }
            if (b.getDate() == null) {
                return -1;
            }
            return b.getDate().compareTo(a.getDate());
        }
    };

    public static final Comparator<ResearchPaper> BY_CITATIONS = new Comparator<ResearchPaper>() {
        @Override
        public int compare(ResearchPaper a, ResearchPaper b) {
            return Integer.compare(b.getCitations(), a.getCitations());
        }
    };

    public static final Comparator<ResearchPaper> BY_PAGES = new Comparator<ResearchPaper>() {
        @Override
        public int compare(ResearchPaper a, ResearchPaper b) {
            return Integer.compare(b.getPages(), a.getPages());
        }
    };

    public static final Comparator<ResearchPaper> BY_TITLE = new Comparator<ResearchPaper>() {
        @Override
        public int compare(ResearchPaper a, ResearchPaper b) {
            return a.getTitle().compareTo(b.getTitle());
        }
    };
}
