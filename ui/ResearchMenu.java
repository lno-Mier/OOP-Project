package ui;

import java.util.List;
import java.util.Scanner;

import interfaces.Researcher;
import models.*;

public class ResearchMenu extends Menu {
   private Researcher researcher;
   private User user;

   public ResearchMenu(Scanner scanner, User user, Researcher researcher) {
      super(scanner);
      this.user       = user;
      this.researcher = researcher;
   }

   @Override
   public void show() {
      while (true) {
         printHeader("Research Menu — " + user.getFirstName() + " | H-index: " + researcher.getHIndex());
         System.out.println("1. View my papers");
         System.out.println("2. View my projects");
         System.out.println("3. Add paper");
         System.out.println("4. Create project");
         System.out.println("5. Join project");
         System.out.println("0. Back");
         printDivider();

         int choice = readInt("Choice: ");
         switch (choice) {
               case 1 -> viewPapers();
               case 2 -> viewProjects();
               case 3 -> addPaper();
               case 4 -> createProject();
               case 5 -> joinProject();
               case 0 -> { 
                  return;
               }
               default -> System.out.println("Invalid choice.");
         }
      }
   }

   private void viewPapers() {
      List<ResearchPaper> papers = researcher.getPapers();
      if (papers.isEmpty()) { 
         System.out.println("  No papers."); 
         return; 
      }
      papers.forEach(p -> System.out.println("  " + p));
   }

   private void viewProjects() {
      List<ResearchProject> projects = researcher.getProjects();
      if (projects.isEmpty()) { 
         System.out.println("  No projects."); 
         return; 
      }
      projects.forEach(p -> System.out.println("  " + p));
   }

   private void addPaper() {
      String id = readLine("Paper ID: ");
      String title = readLine("Title: ");
      String journal = readLine("Journal: ");
      int citations = Integer.parseInt(readLine("Citations: "));
      int pages = Integer.parseInt(readLine("Pages: "));

      System.out.println("Fields: " + java.util.Arrays.toString(enums.ResearchField.values()));
      enums.ResearchField field = enums.ResearchField.valueOf(readLine("Field: ").toUpperCase());

      ResearchPaper paper = new ResearchPaper(id, title, List.of(user.getFirstName() + " " + user.getLastName()), citations, journal, pages, field);
      researcher.addPaper(paper);
      System.out.println("Paper added.");
   }

   private void createProject() {
      String id = readLine("Project ID: ");
      String topic = readLine("Topic: ");
      ResearchProject project = new ResearchProject(id, topic);
      project.addParticipant(user);
      researcher.addProject(project);
      db.addProject(project);  // нужно добавить в Database
      System.out.println("Project created.");
   }

   private void joinProject() {
      List<ResearchProject> all = db.getProjects();  // нужно добавить в Database
      if (all.isEmpty()) { 
         System.out.println("No projects available."); 
         return; 
      }
      all.forEach(p -> System.out.println("  " + p));
      String id = readLine("Project ID to join: ");
      for (ResearchProject p : all) {
         if (p.getProjectId().equals(id)) {
               p.addParticipant(user);
               researcher.addProject(p);
               System.out.println("Joined: " + p.getTopic());
               return;
         }
      }
      System.out.println("Not found.");
   }
}