package pe.edu.vallegrande.app.prueba.student;

import pe.edu.vallegrande.app.model.Student;
import pe.edu.vallegrande.app.service.CrudStudentService;

import java.util.List;

public class getAllTest {

    public static void main(String[] args) {
        // Arrange
        CrudStudentService service = new CrudStudentService();

        // Act
        List<Student> students = service.getAll();

        // Assert
        if (students != null && !students.isEmpty()) {
            System.out.println("Students count: " + students.size());
            for (Student student : students) {
                System.out.println(student);
            }
        } else {
            System.out.println("No students found.");
        }
    }
}
