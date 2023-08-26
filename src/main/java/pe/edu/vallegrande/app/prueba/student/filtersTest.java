package pe.edu.vallegrande.app.prueba.student;

import pe.edu.vallegrande.app.model.Student;
import pe.edu.vallegrande.app.service.CrudStudentService;

import java.util.List;

public class filtersTest {
    public static void main(String[] args) {
        // Crear objeto de prueba
        Student bean = new Student();
        bean.setNames("M");
        bean.setSurname(" ");

        // Realizar la búsqueda por filtros
        CrudStudentService service = new CrudStudentService();
        List<Student> result = service.get(bean);

        // Imprimir resultados
        System.out.println("Resultados de búsqueda:");
        for (Student student : result) {
            System.out.println("Names: " + student.getNames());
            System.out.println("Last Name: " + student.getSurname());
            // Imprimir los demás campos que deseas mostrar
            System.out.println("DNI: " + student.getDni());
            System.out.println("Direction: " + student.getDirection());
            System.out.println("Email: " + student.getEmail());
            System.out.println("Cell Phone: " + student.getCell_phone());
            System.out.println("States: " + student.getStates());
            System.out.println("Information: " + student.getNameYear());
            System.out.println("-------------------------");
        }
    }
}
