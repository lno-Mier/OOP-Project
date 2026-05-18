package patterns;

import java.util.List;

import models.Student;

public class ReportGenerator {
   private ReportStrategy strategy;

   public ReportGenerator(ReportStrategy strategy) {
      this.strategy = strategy;
   }

   public void setStrategy(ReportStrategy strategy) {
      this.strategy = strategy;
   }

   public String generateReport(List<Student> students) {
      if (strategy == null) {
         return "No report strategy set.";
      }
      return strategy.generate(students);
   }
}
