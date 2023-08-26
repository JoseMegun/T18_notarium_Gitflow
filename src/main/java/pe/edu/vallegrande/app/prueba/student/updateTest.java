package pe.edu.vallegrande.app.prueba.student;

import pe.edu.vallegrande.app.model.Student;
import pe.edu.vallegrande.app.service.CrudStudentService;

public class updateTest {
    public static void main(String[] args) {
        // Crear objeto de prueba
        Student bean = new Student();
        bean.setId_student(1);  // ID del estudiante que deseas actualizar
        bean.setNames("Juan");
        bean.setSurname("Lopez");
        bean.setDni("87654321");
        bean.setDirection("Calle Los Olivos");
        bean.setEmail("juan.lopez@example.com");
        bean.setCell_phone("987654321");
        bean.setId_year(2);
        bean.setId_section(1);

        // Actualizar estudiante
        CrudStudentService service = new CrudStudentService();
        service.update(bean);

        System.out.println("Estudiante actualizado exitosamente.");
    }
}
