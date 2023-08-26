package pe.edu.vallegrande.app.prueba.teacher;

import pe.edu.vallegrande.app.service.CrudTeacherService;

public class deleteLogicTest {
    public static void main(String[] args) {
        // ID del profesor que deseas eliminar
        String id = "6";

        // Eliminar estudiante
        CrudTeacherService service = new CrudTeacherService();
        service.delete(id);

        System.out.println("Profesor eliminado exitosamente.");
    }
}
