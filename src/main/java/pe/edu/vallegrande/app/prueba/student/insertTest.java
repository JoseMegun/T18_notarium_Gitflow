package pe.edu.vallegrande.app.prueba.student;

import pe.edu.vallegrande.app.model.Student;
import pe.edu.vallegrande.app.service.CrudStudentService;

public class insertTest {
    public static void main(String[] args) {
        // Crear objeto de prueba
        Student bean = new Student();
        bean.setNames("Juan");
        bean.setSurname("Perez");
        bean.setDni("12345678");
        bean.setDirection("Calle Los Pinos");
        bean.setEmail("juan.perez@example.com");
        bean.setCell_phone("987654321");
        bean.setStates("A");
        bean.setId_year(1);
        bean.setId_section(2);

        // Registrar nuevo estudiante
        CrudStudentService service = new CrudStudentService();
        service.insert(bean);

        System.out.println("Estudiante registrado exitosamente.");
    }
}
