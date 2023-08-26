package pe.edu.vallegrande.app.prueba.teacher;

import pe.edu.vallegrande.app.model.Teacher;
import pe.edu.vallegrande.app.service.CrudTeacherService;

import java.util.List;

public class filtersInactivoTest {
    public static void main(String[] args) {
        // Crear objeto de prueba
        Teacher bean = new Teacher();
        bean.setNames(" ");
        bean.setSurname(" ");

        // Realizar la búsqueda por filtros
        CrudTeacherService service = new CrudTeacherService();
        List<Teacher> result = service.getInactivo(bean);

        // Imprimir resultados
        System.out.println("Resultados de búsqueda:");
        System.out.println("-------------------------");
        for (Teacher teacher : result) {
            System.out.println("Names: " + teacher.getNames());
            System.out.println("-------------------------");
            System.out.println("Last Name: " + teacher.getSurname());
            System.out.println("-------------------------");
            // Imprimir los demás campos que deseas mostrar
            System.out.println("DNI: " + teacher.getDni());
            System.out.println("-------------------------");
            System.out.println("Email: " + teacher.getEmail());
            System.out.println("-------------------------");
            System.out.println("Cell Phone: " + teacher.getCell_phone());
            System.out.println("-------------------------");
            System.out.println("Directions: " + teacher.getDirections());
            System.out.println("-------------------------");
            System.out.println("District: " + teacher.getUbigeous());
            System.out.println("-------------------------");
            System.out.println("States: " + teacher.getTeacher_status());
            System.out.println("-------------------------");
        }
    }
}
