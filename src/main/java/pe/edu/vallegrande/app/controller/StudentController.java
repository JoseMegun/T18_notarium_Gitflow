package pe.edu.vallegrande.app.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import pe.edu.vallegrande.app.model.Student;
import pe.edu.vallegrande.app.service.CrudStudentService;

@WebServlet({ "/studentBuscar", "/studentBuscar2", "/studentProcesar", "/studentBuscarInactivos", "/studentReactivar" })
public class StudentController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CrudStudentService service = new CrudStudentService();

	// Metodos url
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getServletPath();
		switch (path) {
		case "/studentBuscar":
			buscar(request, response);
			break;
		case "/studentBuscar2":
			buscar2(request, response);
			break;
		case "/studentBuscarInactivos":
			buscarInactivo(request, response);
			break;
		case "/studentProcesar":
			procesar(request, response);
			break;
		case "/studentReactivar":
			reactivar(request, response);
			break;
		}
	}

	// Procesar
	private void procesar(HttpServletRequest request, HttpServletResponse response) {
		// Datos
		String action = request.getParameter("action");
		Student bean = new Student();

		// Validar y convertir los campos numéricos solo si no están vacíos
		String id_student = request.getParameter("id_student");
		if (id_student != null && !id_student.isEmpty()) {
			bean.setId_student(Integer.parseInt(id_student));
		}

		bean.setNames(request.getParameter("names"));
		bean.setSurname(request.getParameter("surname"));
		bean.setDni(request.getParameter("dni"));
		bean.setDirection(request.getParameter("direction"));
		bean.setEmail(request.getParameter("email"));
		bean.setCell_phone(request.getParameter("cell_phone"));
		bean.setStates(request.getParameter("states"));
		bean.setNameYear(request.getParameter("nameYear"));
		String idYear = request.getParameter("id_year");
		if (idYear != null && !idYear.isEmpty()) {
			bean.setId_year(Integer.parseInt(idYear));
		}

		String idSection = request.getParameter("id_section");
		if (idSection != null && !idSection.isEmpty()) {
			bean.setId_section(Integer.parseInt(idSection));
		}

		// Proceso
		try {
			switch (action) {
			case "NUEVO":
				service.insert(bean);
				break;
			case "EDITAR":
				service.update(bean);
				break;
			case "ELIMINAR":
				service.delete(bean.getId_student().toString());
				break;
			case "REACTIVAR":
			    service.reactivate(request.getParameter("id_student"));
			    ControllerUtil.responseJson(response, "Estudiante reactivado correctamente.");
			    break;

			default:
				throw new IllegalArgumentException("Unexpected value: " + action);
			}
			ControllerUtil.responseJson(response, "Proceso ok.");
		} catch (Exception e) {
			ControllerUtil.responseJson(response, e.getMessage());
		}
	}

	private void buscar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Datos
		String names = request.getParameter("names");
		String surname = request.getParameter("surname");
		// Proceso
		Student bean = new Student();
		bean.setNames(names);
		bean.setSurname(surname);
		List<Student> lista = service.get(bean);
		// Reporte
		request.setAttribute("listado", lista);
		RequestDispatcher rd = request.getRequestDispatcher("student.jsp");
		rd.forward(request, response);
	}

	// Buscar
	private void buscar2(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Datos
		String names = request.getParameter("names");
		String surname = request.getParameter("surname");
		// Proceso
		Student bean = new Student();
		bean.setNames(names);
		bean.setSurname(surname);
		List<Student> lista = service.get(bean);
		// Preparando el JSON
		Gson gson = new Gson();

		String data = gson.toJson(lista);
		// Reporte
		ControllerUtil.responseJson(response, data);
	}

	// Buscar Inactivos
	private void buscarInactivo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Datos
		String names = request.getParameter("names");
		String surname = request.getParameter("surname");
		// Proceso
		Student bean = new Student();
		bean.setNames(names);
		bean.setSurname(surname);
		List<Student> lista = service.getInactivo(bean);
		// Preparando el JSON
		Gson gson = new Gson();

		String data = gson.toJson(lista);
		// Reporte
		ControllerUtil.responseJson(response, data);
	}

	// Agrega el siguiente método en el controlador
	private void reactivar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Obtiene el ID del estudiante a reactivar
		String id = request.getParameter("id");

		// Proceso
		CrudStudentService service = new CrudStudentService();
		Student student = service.reactivate(id);

		// Preparando el JSON
		Gson gson = new Gson();
		String data = gson.toJson(student);

		// Reporte
		ControllerUtil.responseJson(response, data);
	}
}
