package pe.edu.vallegrande.app.prueba;

import java.sql.Connection;

import pe.edu.vallegrande.app.db.AccesoDB;

public class ConecctionTest {
	
	public static void main(String[] args) {
		try {
			Connection cn = AccesoDB.getConnection();
			if (cn != null) {
				System.out.println("Conexión exitosa con la base de datos Futora.");
				cn.close();
			} else {
				System.out.println("No se pudo establecer la conexión con la base de datos Futora.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}