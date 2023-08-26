package pe.edu.vallegrande.app.prueba.teacher;

import pe.edu.vallegrande.app.model.Teacher;
import pe.edu.vallegrande.app.service.CrudTeacherService;

public class CrudTeacherServiceTest {

    public static void main(String[] args) {
        // Crear instancia de CrudTeacherService
        CrudTeacherService service = new CrudTeacherService();

        // ID del profesor a reactivar
        String teacherId = "1";

        // Reactivar al profesor
        Teacher reactivatedTeacher = service.reactivate(teacherId);

        // Verificar si el profesor fue reactivado correctamente
        if (reactivatedTeacher != null) {
            System.out.println("El profesor con ID " + teacherId + " se reactivó correctamente.");
        } else {
            System.out.println("No se encontró ningún registro con el ID " + teacherId + ".");
        }
    }
}