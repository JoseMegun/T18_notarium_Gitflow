package pe.edu.vallegrande.app.model;

public class Teacher {

	private Integer id_teacher;
	private String names;
	private String surname;
	private String dni;
	private String email;
	private String cell_phone;
	private String directions;
	private String ubigeous;
	private String teacher_status;

	public Teacher() {
	}
	
	public Teacher(Integer id_teacher, String names, String surname, String dni, String email, String cell_phone,
			String directions, String ubigeous, String teacher_status) {
		super();
		this.id_teacher = id_teacher;
		this.names = names;
		this.surname = surname;
		this.dni = dni;
		this.email = email;
		this.cell_phone = cell_phone;
		this.directions = directions;
		this.ubigeous = ubigeous;
		this.teacher_status = teacher_status;
	}

	public Integer getId_teacher() {
		return id_teacher;
	}

	public void setId_teacher(Integer id_teacher) {
		this.id_teacher = id_teacher;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCell_phone() {
		return cell_phone;
	}

	public void setCell_phone(String cell_phone) {
		this.cell_phone = cell_phone;
	}

	public String getDirections() {
		return directions;
	}

	public void setDirections(String directions) {
		this.directions = directions;
	}

	public String getUbigeous() {
		return ubigeous;
	}

	public void setUbigeous(String ubigeous) {
		this.ubigeous = ubigeous;
	}

	public String getTeacher_status() {
		return teacher_status;
	}

	public void setTeacher_status(String teacher_status) {
		this.teacher_status = teacher_status;
	}

	@Override
	public String toString() {
		String data = "[id_teacher: " + this.id_teacher;
		data += ", names: " + this.names;
		data += ", surname: " + this.surname;
		data += ", dni: " + this.dni;
		data += ", email: " + this.email;
		data += ", cell_phone: " + this.cell_phone;
		data += ", directions: " + this.directions;
		data += ", ubigeous: " + this.ubigeous;
		data += ", teacher_status: " + this.teacher_status + "]";
		return data;

	}
	
}
