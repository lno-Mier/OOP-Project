package ui;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import comparators.PaperComparators;
import enums.ResearchField;
import exceptions.NotAResearcherException;
import interfaces.Researcher;
import models.ResearchPaper;
import models.ResearchProject;
import models.User;
import services.Logger;

public class ResearchMenu extends Menu {
    private Researcher researcher;
    private User user;

    public ResearchMenu(Scanner scanner, User user, Researcher researcher) {
        super(scanner);
        this.user = user;
        this.researcher = researcher;
    }

    @Override
    public void show() {
        while (true) {
            printHeader("Research Menu - " + user.getFirstName() + " | H-index: " + researcher.getHIndex());
            System.out.println("1. View my papers");
            System.out.println("2. View my papers sorted");
            System.out.println("3. View my projects");
            System.out.println("4. Add paper");
            System.out.println("5. Create project");
            System.out.println("6. Join project");
            System.out.println("0. Back");
            printDivider();

            int choice = readInt("Choice: ");
            switch (choice) {
                case 1:
                    viewPapers();
                    break;
                case 2:
                    viewPapersSorted();
                    break;
                case 3:
                    viewProjects();
                    break;
                case 4:
                    addPaper();
                    break;
                case 5:
                    createProject();
                    break;
                case 6:
                    joinProject();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void viewPapers() {
        List<ResearchPaper> papers = researcher.getPapers();
        if (papers.isEmpty()) {
            System.out.println("No papers.");
            return;
        }
        for (ResearchPaper p : papers) {
            System.out.println("  " + p);
        }
    }

    private void viewPapersSorted() {
        if (researcher.getPapers().isEmpty()) {
            System.out.println("No papers.");
            return;
        }
        System.out.println("\n1. By date (newest first)");
        System.out.println("2. By citations (most cited first)");
        System.out.println("3. By pages (longest first)");
        int choice = readInt("Choice: ");
        switch (choice) {
            case 1:
                researcher.printPapers(PaperComparators.BY_DATE);
                break;
            case 2:
                researcher.printPapers(PaperComparators.BY_CITATIONS);
                break;
            case 3:
                researcher.printPapers(PaperComparators.BY_PAGES);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void viewProjects() {
        List<ResearchProject> projects = researcher.getProjects();
        if (projects.isEmpty()) {
            System.out.println("No projects.");
            return;
        }
        for (ResearchProject p : projects) {
            System.out.println("  " + p);
        }
    }

    private void addPaper() {
        String id = readLine("Paper ID: ");
        String title = readLine("Title: ");
        String journal = readLine("Journal: ");
        int citations = readInt("Citations: ");
        int pages = readInt("Pages: ");
        int year = readInt("Year of publication: ");
        int month = readInt("Month (1-12): ");
        int day = readInt("Day: ");
        LocalDate date;
        try {
            date = LocalDate.of(year, month, day);
        } catch (Exception e) {
            System.out.println("Invalid date.");
            return;
        }
        String doi = readLine("DOI: ");
        System.out.println("Fields: " + Arrays.toString(ResearchField.values()));
        ResearchField field;
        try {
            field = ResearchField.valueOf(readLine("Field: ").toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid field.");
            return;
        }

        ResearchPaper paper = new ResearchPaper(id, title, List.of(user.getFirstName() + " " + user.getLastName()), citations, journal, pages, date, doi, field);
        researcher.addPaper(paper);
        System.out.println("Paper added.");
        Logger.getInstance().log("Added paper " + id, user.getLogin());
    }

    private void createProject() {
        String id = readLine("Project ID: ");
        String topic = readLine("Topic: ");
        ResearchProject project = new ResearchProject(id, topic);
        try {
            project.addParticipant(user);
            researcher.addProject(project);
            db.addProject(project);
            System.out.println("Project created.");
            Logger.getInstance().log("Created project " + id, user.getLogin());
        } catch (NotAResearcherException e) {
            System.out.println("Failed: " + e.getMessage());
        }
    }

    private void joinProject() {
        List<ResearchProject> all = db.getProjects();
        if (all.isEmpty()) {
            System.out.println("No projects available.");
            return;
        }
        for (ResearchProject p : all) {
            System.out.println("  " + p);
        }
        String id = readLine("Project ID: ");
        for (ResearchProject p : all) {
            if (p.getProjectId().equals(id)) {
                try {
                    p.addParticipant(user);
                    researcher.addProject(p);
                    System.out.println("Joined: " + p.getTopic());
                } catch (NotAResearcherException e) {
                    System.out.println("Failed: " + e.getMessage());
                }
                return;
            }
        }
        System.out.println("Project not found.");
    }
}
