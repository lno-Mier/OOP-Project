package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import enums.ResearchField;

public class ResearchPaper implements Serializable, Comparable<ResearchPaper> {
    private static final long serialVersionUID = 1L;

    private String paperId;
    private String title;
    private List<String> authors;
    private int citations;
    private String journal;
    private int pages;
    private LocalDate date;
    private String doi;
    private ResearchField field;

    public ResearchPaper(String paperId, String title, List<String> authors, int citations, String journal, int pages, LocalDate date, String doi, ResearchField field) {
        this.paperId = paperId;
        this.title = title;
        this.authors = authors;
        this.citations = citations;
        this.journal = journal;
        this.pages = pages;
        this.date = date;
        this.doi = doi;
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

    public LocalDate getDate() {
        return date;
    }

    public String getDoi() {
        return doi;
    }

    public ResearchField getField() {
        return field;
    }

    public void setCitations(int citations) {
        this.citations = citations;
    }

    @Override
    public int compareTo(ResearchPaper other) {
        return this.title.compareTo(other.title);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResearchPaper that = (ResearchPaper) o;
        return Objects.equals(paperId, that.paperId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paperId);
    }

    @Override
    public String toString() {
        return String.format("[%s] \"%s\" - %s | cited: %d | pages: %d | %s", paperId, title, journal, citations, pages, date);
    }
}
