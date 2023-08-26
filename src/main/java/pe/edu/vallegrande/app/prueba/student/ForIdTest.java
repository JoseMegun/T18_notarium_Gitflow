package pe.edu.vallegrande.app.prueba.student;

import pe.edu.vallegrande.app.model.Student;
import pe.edu.vallegrande.app.service.CrudStudentService;

public class ForIdTest {
    public static void main(String[] args) {
        CrudStudentService service = new CrudStudentService();

        // Obtener un registro por su ID
        String id = "18"; // ID del registro a obtener
        Student student = service.getForId(id);

        // Mostrar los datos del estudiante obtenido
        if (student != null) {
            System.out.println("ID: " + student.getId_student());
            System.out.println("Nombres: " + student.getNames());
            System.out.println("Apellido: " + student.getSurname());
            System.out.println("DNI: " + student.getDni());
            System.out.println("Dirección: " + student.getDirection());
            System.out.println("Email: " + student.getEmail());
            System.out.println("Teléfono: " + student.getCell_phone());
            System.out.println("Estado: " + student.getStates());
            System.out.println("ID Año: " + student.getId_year());
            System.out.println("ID Sección: " + student.getId_section());
        } else {
            System.out.println("No se encontró ningún registro con el ID proporcionado.");
        }
    }
}
