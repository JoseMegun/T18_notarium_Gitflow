package pe.edu.vallegrande.app.prueba.teacher;

import pe.edu.vallegrande.app.model.Teacher;
import pe.edu.vallegrande.app.service.CrudTeacherService;

public class InsertTest {
    public static void main(String[] args) {
        // Crear objeto de prueba
        Teacher bean = new Teacher();
        bean.setNames("Maria");
		bean.setSurname("Perez");
		bean.setDni("12345678");
		bean.setEmail("juan.perez@example.com");
		bean.setCell_phone("987654321");
		bean.setDirections("Calle Pinos");
		bean.setUbigeous("Miraflores");
		bean.setTeacher_status("A");

        // Registrar nuevo profesor
        CrudTeacherService service = new CrudTeacherService();
        service.insert(bean);

        System.out.println("Profesor registrado exitosamente.");
    }
}
