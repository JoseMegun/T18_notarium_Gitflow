package pe.edu.vallegrande.app.prueba.student;

import pe.edu.vallegrande.app.model.Student;
import pe.edu.vallegrande.app.service.CrudStudentService;

public class CrudStudentServiceTest {

    public static void main(String[] args) {
        // Crear instancia de CrudStudentService
        CrudStudentService service = new CrudStudentService();

        // ID del estudiante a reactivar
        String studentId = "4";

        // Reactivar al estudiante
        Student reactivatedStudent = service.reactivate(studentId);

        // Verificar si el estudiante fue reactivado correctamente
        if (reactivatedStudent != null) {
            System.out.println("El estudiante con ID " + studentId + " se reactivó correctamente.");
        } else {
            System.out.println("No se encontró ningún registro con el ID " + studentId + ".");
        }
    }
}
