package patterns;

import java.util.List;

import models.Student;

public class GpaReport implements ReportStrategy {

   @Override
   public String generate(List<Student> students) {
      if (students.isEmpty()) {
         return "No students to report.";
      }
      StringBuilder sb = new StringBuilder();
      sb.append("=== GPA REPORT ===\n");

      double totalGpa = 0;
      double minGpa = Double.MAX_VALUE;
      double maxGpa = -1;
      Student topStudent = null;

      for (Student s : students) {
         double gpa = s.getGpa();
         totalGpa += gpa;
         if (gpa < minGpa) {
            minGpa = gpa;
         }
         if (gpa > maxGpa) {
            maxGpa = gpa;
            topStudent = s;
         }
      }

      double avg = totalGpa / students.size();
      sb.append("Total students: ").append(students.size()).append("\n");
      sb.append(String.format("Average GPA: %.2f%n", avg));
      sb.append(String.format("Min GPA: %.2f%n", minGpa));
      sb.append(String.format("Max GPA: %.2f%n", maxGpa));
      if (topStudent != null) {
         sb.append("Top student: ").append(topStudent.getFirstName()).append(" ").append(topStudent.getLastName()).append("\n");
      }
      return sb.toString();
   }
}
