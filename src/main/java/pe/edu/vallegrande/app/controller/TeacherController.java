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

import pe.edu.vallegrande.app.model.Teacher;
import pe.edu.vallegrande.app.service.CrudTeacherService;

@WebServlet({ "/teacherBuscar", "/teacherBuscar2", "/teacherProcesar", "/teacherBuscarInactivos", "/teacherReactivar" })
public class TeacherController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CrudTeacherService service = new CrudTeacherService();

	// Metodos url
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getServletPath();
		switch (path) {
		case "/teacherBuscar":
			buscar(request, response);
			break;
		case "/teacherBuscar2":
			buscar2(request, response);
			break;
		case "/teacherBuscarInactivos":
			buscarInactivo(request, response);
			break;
		case "/teacherProcesar":
			procesar(request, response);
			break;
		case "/teacherReactivar":
			reactivar(request, response);
			break;
		}
	}

	// Procesar
	private void procesar(HttpServletRequest request, HttpServletResponse response) {
		// Datos
		String action = request.getParameter("action");
		Teacher bean = new Teacher();

		// Validar y convertir los campos numéricos solo si no están vacíos
		String id_teacher = request.getParameter("id_teacher");
        if (id_teacher != null && !id_teacher.isEmpty()) {
            bean.setId_teacher(Integer.parseInt(id_teacher));
        }

        bean.setNames(request.getParameter("names"));
        bean.setSurname(request.getParameter("surname"));
        bean.setDni(request.getParameter("dni"));
        bean.setEmail(request.getParameter("email"));
        bean.setCell_phone(request.getParameter("cell_phone"));
        bean.setDirections(request.getParameter("directions"));
        bean.setUbigeous(request.getParameter("ubigeous"));
        bean.setTeacher_status(request.getParameter("teacher_status"));
		
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
				service.delete(bean.getId_teacher().toString());
				break;
			case "REACTIVAR":
			    service.reactivate(request.getParameter("id_teacher"));
			    ControllerUtil.responseJson(response, "Profesor reactivado correctamente.");
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
		Teacher bean = new Teacher();
		bean.setNames(names);
		bean.setSurname(surname);
		List<Teacher> lista = service.get(bean);
		// Reporte
		request.setAttribute("listado", lista);
		RequestDispatcher rd = request.getRequestDispatcher("Teacher.jsp");
		rd.forward(request, response);
	}

	// Buscar
	private void buscar2(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Datos
		String names = request.getParameter("names");
		String surname = request.getParameter("surname");
		// Proceso
		Teacher bean = new Teacher();
		bean.setNames(names);
		bean.setSurname(surname);
		List<Teacher> lista = service.get(bean);
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
		Teacher bean = new Teacher();
		bean.setNames(names);
		bean.setSurname(surname);
		List<Teacher> lista = service.getInactivo(bean);
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
		CrudTeacherService service = new CrudTeacherService();
		Teacher teacher = service.reactivate(id);

		// Preparando el JSON
		Gson gson = new Gson();
		String data = gson.toJson(teacher);

		// Reporte
		ControllerUtil.responseJson(response, data);
	}
}
