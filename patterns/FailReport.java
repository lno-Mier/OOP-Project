package patterns;

import java.util.List;

import models.Student;

public class FailReport implements ReportStrategy {

   @Override
   public String generate(List<Student> students) {
      if (students.isEmpty()) {
         return "No students to report.";
      }
      StringBuilder sb = new StringBuilder();
      sb.append("=== FAIL REPORT ===\n");

      int totalFails = 0;
      int studentsWithFails = 0;
      int atRiskCount = 0;

      for (Student s : students) {
         int fails = s.getFailCount();
         if (fails > 0) {
            studentsWithFails++;
            totalFails += fails;
            sb.append(String.format("  %s %s: %d fail(s)%n", s.getFirstName(), s.getLastName(), fails));
         }
         if (fails >= 2) {
            atRiskCount++;
         }
      }

      sb.append("\n");
      sb.append("Total students: ").append(students.size()).append("\n");
      sb.append("Students with fails: ").append(studentsWithFails).append("\n");
      sb.append("Total fails: ").append(totalFails).append("\n");
      sb.append("At-risk students (2+ fails): ").append(atRiskCount).append("\n");
      return sb.toString();
   }
}
