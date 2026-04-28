package models;

import java.io.Serializable;
import java.util.List;

import enums.ResearchField;

public class ResearchPaper implements Serializable {
   private static final long serialVersionUID = 1L;

   private String paperId;
   private String title;
   private List<String> authors;
   private int citations;
   private String journal;
   private int pages;
   private ResearchField field;

   public ResearchPaper(String paperId, String title, List<String> authors, int citations, String journal, int pages, ResearchField field) {
      this.paperId = paperId;
      this.title = title;
      this.authors = authors;
      this.citations = citations;
      this.journal = journal;
      this.pages = pages;
      this.field = field;
   }

   public String getPaperId() { 
      return paperId; 
   }
   public String getTitle() { 
      return title; 
   }
   public List<String> getAuthors() { 
      return authors; 
   }
   public int getCitations() { 
      return citations; 
   }
   public String getJournal() { 
      return journal; 
   }
   public int getPages() { 
      return pages; 
   }
   public ResearchField getField() { 
      return field; 
   }

   public void setCitations(int citations) { 
      this.citations = citations; 
   }

   @Override
   public String toString() {
      return String.format("[%s] \"%s\" — %s | cited: %d", paperId, title, journal, citations);
   }
}