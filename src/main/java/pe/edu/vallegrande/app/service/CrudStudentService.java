package pe.edu.vallegrande.app.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pe.edu.vallegrande.app.db.AccesoDB;
import pe.edu.vallegrande.app.model.Student;
import pe.edu.vallegrande.app.service.spec.CrudServiceSpec;
import pe.edu.vallegrande.app.service.spec.RowMapper;

public class CrudStudentService implements CrudServiceSpec<Student>, RowMapper<Student> {

	// Definiendo consultas sql
	private final String SQL_SELECT_BASE = "SELECT id_student, names, surname, dni, direction, email, cell_phone, states, id_year, id_section FROM student";
	private final String SQL_SELECT_LISTAR = "SELECT * FROM Datos_Estudiante where states = 'A' ORDER BY id_student DESC";
	private final String SQL_INSERT = "INSERT INTO student(names, surname, dni, direction, email, cell_phone, states, id_year, id_section) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private final String SQL_UPDATE = "UPDATE student SET names=?, surname=?, dni=?, direction=?, email=?, cell_phone=?, id_year=?, id_section=? WHERE id_student=?";
	private final String SQL_DELETE = "UPDATE student SET states='I' WHERE id_student=?";
	private final String SQL_SELECT_ACTIVO = "SELECT id_student, names, surname, dni, direction, email, cell_phone, states, information, id_year, id_section FROM Datos_Estudiante WHERE states='A'";
	private final String SQL_SELECT_INACTIVO = "SELECT id_student, names, surname, dni, direction, email, cell_phone, states, information, id_year, id_section FROM Datos_Estudiante WHERE states='I'";

	// Obtiene todos los datos de una tabla
	@Override
	public List<Student> getAll() {
		// Variables
		Connection cn = null;
		List<Student> lista = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Student bean;
		// Proceso
		try {
			cn = AccesoDB.getConnection();
			pstm = cn.prepareStatement(SQL_SELECT_LISTAR);
			rs = pstm.executeQuery();
			while (rs.next()) {
				bean = mapRow2(rs);
				lista.add(bean);
			}
			rs.close();
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				cn.close();
			} catch (Exception e2) {
			}
		}
		return lista;
	}

	// Obtiene los datos por el id del registro
	@Override
	public Student getForId(String id) {
		// Variables
		Connection cn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Student bean = null;
		String sql;
		// Proceso
		try {
			cn = AccesoDB.getConnection();
			sql = SQL_SELECT_BASE + " WHERE id_student=?";
			pstm = cn.prepareStatement(sql);
			pstm.setInt(1, Integer.parseInt(id));
			rs = pstm.executeQuery();
			if (rs.next()) {
				bean = mapRow(rs);
			}
			rs.close();
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if (cn != null) {
					cn.close();
				}
			} catch (Exception e2) {
				// Manejar excepción al cerrar la conexión
			}
		}
		return bean;
	}

	// Realiza la búsqueda por filtros (solo por names y surname)
	@Override
	public List<Student> get(Student bean) {
		// Variables
		Connection cn = null;
		List<Student> lista = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Student item;
		String sql;
		String names;
		String surname;
		// Preparar los datos
		names = "%" + UtilService.setStringVacio(bean.getNames()) + "%";
		surname = "%" + UtilService.setStringVacio(bean.getSurname()) + "%";
		// Proceso
		try {
			// Conexion
			cn = AccesoDB.getConnection();
			// La consulta
			sql = SQL_SELECT_ACTIVO + " AND names LIKE ? AND surname LIKE ?";
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, names);
			pstm.setString(2, surname);
			rs = pstm.executeQuery();
			while (rs.next()) {
				item = mapRow2(rs);
				lista.add(item);
			}
			rs.close();
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				cn.close();
			} catch (Exception e2) {
			}
		}
		return lista;
	}

	@Override
	public Student reactivate(String id) {
		Student student = null; // Variable para almacenar el objeto Student

		String sql = "UPDATE STUDENT SET states = ? WHERE id_student = ?";

		try (Connection conn = AccesoDB.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, "A"); // Estado activo
			pstmt.setString(2, id); // Establecer el valor del ID como cadena

			int rowsUpdated = pstmt.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("Registro reactivado correctamente.");

				// Obtener el objeto Student actualizado
				student = getForId(id);
			} else {
				System.out.println("No se encontró ningún registro con el ID especificado.");
			}

		} catch (SQLException e) {
			// Manejar la excepción
			e.printStackTrace();
		}

		return student; // Retornar el objeto Student actualizado (o null si no se encontró ningún
						// registro)
	}

	public List<Student> getInactivo(Student bean) {
		// Variables
		Connection cn = null;
		List<Student> lista = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Student item;
		String sql;
		String names;
		String surname;
		// Preparar los datos
		names = "%" + UtilService.setStringVacio(bean.getNames()) + "%";
		surname = "%" + UtilService.setStringVacio(bean.getSurname()) + "%";
		// Proceso
		try {
			// Conexion
			cn = AccesoDB.getConnection();
			// La consulta
			sql = SQL_SELECT_INACTIVO + " AND names LIKE ? AND surname LIKE ?";
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, names);
			pstm.setString(2, surname);
			rs = pstm.executeQuery();
			while (rs.next()) {
				item = mapRow2(rs);
				lista.add(item);
			}
			rs.close();
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				cn.close();
			} catch (Exception e2) {
			}
		}
		return lista;
	}

	// Registra un nuevo registro
	@Override
	public void insert(Student bean) {
		// Variables
		Connection cn = null;
		PreparedStatement pstm = null;
		int resultado;
		// Proceso
		try {
			cn = AccesoDB.getConnection();
			pstm = cn.prepareStatement(SQL_INSERT);
			pstm.setString(1, bean.getNames());
			pstm.setString(2, bean.getSurname());
			pstm.setString(3, bean.getDni());
			pstm.setString(4, bean.getDirection());
			pstm.setString(5, bean.getEmail());
			pstm.setString(6, bean.getCell_phone());

			// Establecer el valor "A" para el atributo "states" si no se proporciona un
			// valor explícito
			String states = bean.getStates();
			if (states == null || states.isEmpty()) {
				pstm.setString(7, "A");
			} else {
				pstm.setString(7, states);
			}

			pstm.setInt(8, bean.getId_year());
			pstm.setInt(9, bean.getId_section());
			resultado = pstm.executeUpdate();
			pstm.close();
			if (resultado == 0) {
				throw new RuntimeException("No se pudo registrar al estudiante.");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if (cn != null) {
					cn.close();
				}
			} catch (Exception e2) {
				// Manejar excepción al cerrar la conexión
			}
		}
	}

	// Actualiza un registro existente
	@Override
	public void update(Student bean) {
		// Variables
		Connection cn = null;
		PreparedStatement pstm = null;
		int resultado;
		// Proceso
		try {
			cn = AccesoDB.getConnection();
			pstm = cn.prepareStatement(SQL_UPDATE);
			pstm.setString(1, bean.getNames());
			pstm.setString(2, bean.getSurname());
			pstm.setString(3, bean.getDni());
			pstm.setString(4, bean.getDirection());
			pstm.setString(5, bean.getEmail());
			pstm.setString(6, bean.getCell_phone());
			pstm.setInt(7, bean.getId_year());
			pstm.setInt(8, bean.getId_section());
			pstm.setInt(9, bean.getId_student());
			resultado = pstm.executeUpdate();
			pstm.close();
			if (resultado == 0) {
				throw new RuntimeException("No se pudo actualizar al estudiante.");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			// Cerrar la conexión y los recursos en el orden inverso al que fueron abiertos
			try {
				if (pstm != null) {
					pstm.close();
				}
			} catch (SQLException e) {
				// Manejar excepción al cerrar el PreparedStatement
			}
			try {
				if (cn != null) {
					cn.close();
				}
			} catch (SQLException e) {
				// Manejar excepción al cerrar la conexión
			}
		}
	}

	// Elimina un registro por el id
	@Override
	public void delete(String id) {
		// Variables
		Connection cn = null;
		PreparedStatement pstm = null;
		int resultado;
		// Proceso
		try {
			cn = AccesoDB.getConnection();
			pstm = cn.prepareStatement(SQL_DELETE);
			pstm.setInt(1, Integer.parseInt(id));
			resultado = pstm.executeUpdate();
			pstm.close();
			if (resultado == 0) {
				throw new RuntimeException("No se pudo eliminar al estudiante.");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if (cn != null) {
					cn.close();
				}
			} catch (Exception e2) {
				// Manejar excepción al cerrar la conexión
			}
		}
	}

	// Mapea una fila de resultados a un objeto Student
	@Override
	public Student mapRow(ResultSet rs) throws SQLException {
		Student bean = new Student();
		bean.setId_student(rs.getInt("id_student"));
		bean.setNames(rs.getString("names"));
		bean.setSurname(rs.getString("surname"));
		bean.setDni(rs.getString("dni"));
		bean.setDirection(rs.getString("direction"));
		bean.setEmail(rs.getString("email"));
		bean.setCell_phone(rs.getString("cell_phone"));
		bean.setStates(rs.getString("states"));
		bean.setId_year(rs.getInt("id_year"));
		bean.setId_section(rs.getInt("id_section"));

		return bean;
	}

	// Mapea una fila de resultados a un objeto Student
	@Override
	public Student mapRow2(ResultSet rs) throws SQLException {
		Student bean = new Student();
		bean.setId_student(rs.getInt("id_student"));
		bean.setNames(rs.getString("names"));
		bean.setSurname(rs.getString("surname"));
		bean.setDni(rs.getString("dni"));
		bean.setDirection(rs.getString("direction"));
		bean.setEmail(rs.getString("email"));
		bean.setCell_phone(rs.getString("cell_phone"));
		bean.setStates(rs.getString("states"));
		bean.setId_year(rs.getInt("id_year"));
		bean.setId_section(rs.getInt("id_section"));
		bean.setNameYear(rs.getString("information"));
		return bean;
	}
}
