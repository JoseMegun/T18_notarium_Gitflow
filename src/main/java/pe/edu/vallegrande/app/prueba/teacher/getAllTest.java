package pe.edu.vallegrande.app.prueba.teacher;

import pe.edu.vallegrande.app.model.Teacher;
import pe.edu.vallegrande.app.service.CrudTeacherService;

import java.util.List;

public class getAllTest {

    public static void main(String[] args) {
        // Arrange
        CrudTeacherService service = new CrudTeacherService();

        // Act
        List<Teacher> teachers = service.getAll();

        // Assert
        if (teachers != null && !teachers.isEmpty()) {
            System.out.println("Teachers count: " + teachers.size());
            for (Teacher teacher : teachers) {
                System.out.println(teacher);
            }
        } else {
            System.out.println("No teachers found.");
        }
    }
}
