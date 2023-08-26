package pe.edu.vallegrande.app.prueba.teacher;

import pe.edu.vallegrande.app.model.Teacher;
import pe.edu.vallegrande.app.service.CrudTeacherService;

public class ForIdTest {
    public static void main(String[] args) {
        CrudTeacherService service = new CrudTeacherService();

        // Obtener un registro por su ID
        String id = "3"; // ID del registro a obtener
        Teacher teacher = service.getForId(id);

        // Mostrar los datos del profesor obtenido
        if (teacher != null) {
            System.out.println("ID: " + teacher.getId_teacher());
            System.out.println("Nombres: " + teacher.getNames());
            System.out.println("Apellido: " + teacher.getSurname());
            System.out.println("DNI: " + teacher.getDni());
            System.out.println("Email: " + teacher.getEmail());
            System.out.println("Celular: " + teacher.getCell_phone());
            System.out.println("Dirección: " + teacher.getDirections());
            System.out.println("Distrito: " + teacher.getUbigeous());
            System.out.println("Estado: " + teacher.getTeacher_status());
        } else {
            System.out.println("No se encontró ningún registro con el ID proporcionado.");
        }
    }
}

