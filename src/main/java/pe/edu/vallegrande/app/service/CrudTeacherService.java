package pe.edu.vallegrande.app.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pe.edu.vallegrande.app.db.AccesoDB;

import pe.edu.vallegrande.app.model.Teacher;
import pe.edu.vallegrande.app.service.spec.CrudServiceSpec2;
import pe.edu.vallegrande.app.service.spec.RowMapper2;

public class CrudTeacherService implements CrudServiceSpec2<Teacher>, RowMapper2<Teacher> {

	// Definiendo consultas sql
		private final String SQL_SELECT_BASE = "SELECT id_teacher, names, surname, dni, directions, ubigeous, email, cell_phone, teacher_status FROM teacher";
		private final String SQL_INSERT = "INSERT INTO teacher(names, surname, dni, email, cell_phone, directions, ubigeous, teacher_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		private final String SQL_UPDATE = "UPDATE teacher SET names=?, surname=?, dni=?, email=?, cell_phone=?, directions=?, ubigeous=? WHERE id_teacher=?";
		private final String SQL_DELETE = "UPDATE teacher SET teacher_status='I' WHERE id_teacher=?";
		private final String SQL_SELECT_ACTIVO = "SELECT id_teacher, names, surname, dni, directions, ubigeous, email, cell_phone, teacher_status FROM teacher WHERE teacher_status='A'";
		private final String SQL_SELECT_INACTIVO = "SELECT id_teacher, names, surname, dni, directions, ubigeous, email, cell_phone, teacher_status FROM teacher WHERE teacher_status='I'";
	
		// Obtiene todos los datos de una tabla	
		@Override
		public List<Teacher> getAll() {
			// Variables
			Connection cn = null;
			List<Teacher> lista = new ArrayList<>();
			PreparedStatement pstm = null;
			ResultSet rs = null;
			Teacher bean;
			// Proceso
			try {
				cn = AccesoDB.getConnection();
				pstm = cn.prepareStatement(SQL_SELECT_BASE);
				rs = pstm.executeQuery();
				while (rs.next()) {
					bean = mapRow(rs);
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
		public Teacher getForId(String id) {
			// Variables
			Connection cn = null;
			PreparedStatement pstm = null;
			ResultSet rs = null;
			Teacher bean = null;
			String sql;
			// Proceso
			try {
				cn = AccesoDB.getConnection();
				sql = SQL_SELECT_BASE + " WHERE id_teacher=?";
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
		public List<Teacher> get(Teacher bean) {
			// Variables
			Connection cn = null;
			List<Teacher> lista = new ArrayList<>();
			PreparedStatement pstm = null;
			ResultSet rs = null;
			Teacher item;
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
					item = mapRow(rs);
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
		public Teacher reactivate(String id) {
			Teacher teacher = null; // Variable para almacenar el objeto Teacher

			String sql = "UPDATE TEACHER SET teacher_status = ? WHERE id_teacher = ?";

			try (Connection conn = AccesoDB.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setString(1, "A"); // Estado activo
				pstmt.setString(2, id); // Establecer el valor del ID como cadena

				int rowsUpdated = pstmt.executeUpdate();
				if (rowsUpdated > 0) {
					System.out.println("Registro reactivado correctamente.");

					// Obtener el objeto Teacher actualizado
					teacher = getForId(id);
				} else {
					System.out.println("No se encontró ningún registro con el ID especificado.");
				}

			} catch (SQLException e) {
				// Manejar la excepción
				e.printStackTrace();
			}

			return teacher; // Retornar el objeto Teacher actualizado (o null si no se encontró ningún
							// registro)
		}

		public List<Teacher> getInactivo(Teacher bean) {
			// Variables
			Connection cn = null;
			List<Teacher> lista = new ArrayList<>();
			PreparedStatement pstm = null;
			ResultSet rs = null;
			Teacher item;
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
					item = mapRow(rs);
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
		public void insert(Teacher bean) {
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
				pstm.setString(4, bean.getEmail());
				pstm.setString(5, bean.getCell_phone());
				pstm.setString(6, bean.getDirections());
				pstm.setString(7, bean.getUbigeous());

				// Establecer el valor "A" para el atributo "teacher_status" si no se proporciona un
				// valor explícito
				String teacher_status = bean.getTeacher_status();
				if (teacher_status == null || teacher_status.isEmpty()) {
					pstm.setString(8, "A");
				} else {
					pstm.setString(8, teacher_status);
				}

				resultado = pstm.executeUpdate();
				pstm.close();
				if (resultado == 0) {
					throw new RuntimeException("No se pudo registrar al profesor.");
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
		public void update(Teacher bean) {
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
				pstm.setString(4, bean.getEmail());
				pstm.setString(5, bean.getCell_phone());
				pstm.setString(6, bean.getDirections());
				pstm.setString(7, bean.getUbigeous());
				pstm.setInt(8, bean.getId_teacher());
				resultado = pstm.executeUpdate();
				pstm.close();
				if (resultado == 0) {
					throw new RuntimeException("No se pudo actualizar al profesor.");
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
					throw new RuntimeException("No se pudo eliminar al profesor.");
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

		// Mapea una fila de resultados a un objeto Teacher
		@Override
		public Teacher mapRow(ResultSet rs) throws SQLException {
			Teacher bean = new Teacher();
			bean.setId_teacher(rs.getInt("id_teacher"));
			bean.setNames(rs.getString("names"));
			bean.setSurname(rs.getString("surname"));
			bean.setDni(rs.getString("dni"));
			bean.setEmail(rs.getString("email"));
			bean.setCell_phone(rs.getString("cell_phone"));
			bean.setDirections(rs.getString("directions"));
			bean.setUbigeous(rs.getString("ubigeous"));
			bean.setTeacher_status(rs.getString("teacher_status"));
			return bean;
		}
	}
