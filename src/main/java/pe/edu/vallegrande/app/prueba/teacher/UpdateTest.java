package pe.edu.vallegrande.app.prueba.teacher;

import pe.edu.vallegrande.app.model.Teacher;
import pe.edu.vallegrande.app.service.CrudTeacherService;

public class UpdateTest {
    public static void main(String[] args) {
        // Crear objeto de prueba
        Teacher bean = new Teacher();
        bean.setId_teacher(1);  // ID del estudiante que deseas actualizar
        bean.setNames("Juan");
		bean.setSurname("Lopez");
		bean.setDni("87654321");
		bean.setEmail("juan.lopez@example.com");
		bean.setCell_phone("987654321");
		bean.setDirections("Calle Los Olivos");
		bean.setUbigeous("Lima");

        // Actualizar profesor
        CrudTeacherService service = new CrudTeacherService();
        service.update(bean);

        System.out.println("Profesor actualizado exitosamente.");
    }
}
