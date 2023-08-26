package pe.edu.vallegrande.app.prueba.student;

import pe.edu.vallegrande.app.service.CrudStudentService;

public class deleteLogicTest {
    public static void main(String[] args) {
        // ID del estudiante que deseas eliminar
        String id = "1";

        // Eliminar estudiante
        CrudStudentService service = new CrudStudentService();
        service.delete(id);

        System.out.println("Estudiante eliminado exitosamente.");
    }
}
