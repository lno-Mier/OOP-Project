package patterns;

import java.util.List;

import models.Student;

public interface ReportStrategy {
   String generate(List<Student> students);
}
