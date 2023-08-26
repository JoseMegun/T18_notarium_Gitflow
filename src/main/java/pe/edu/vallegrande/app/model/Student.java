package pe.edu.vallegrande.app.model;

public class Student {

    private Integer id_student;
    private String names;
    private String surname;
    private String dni;
    private String direction;
    private String email;
    private String cell_phone;
    private String states;
    private Integer id_year;
    private String nameYear;
    private Integer id_section;
    private String nameSection;
    
    public Student() {
    }

    public Student(Integer id_student, String names, String surname, String dni, String direction, String email,
                   String cell_phone, String states, Integer id_year, String nameYear, Integer id_section, String nameSection) {
        super();
        this.id_student = id_student;
        this.names = names;
        this.surname = surname;
        this.dni = dni;
        this.direction = direction;
        this.email = email;
        this.cell_phone = cell_phone;
        this.states = states;
        this.id_year = id_year;
        this.nameYear = nameYear;
        this.id_section = id_section;
        this.nameSection = nameSection;
    }

    public Integer getId_student() {
        return id_student;
    }

    public void setId_student(Integer id_student) {
        this.id_student = id_student;
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

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
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

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public Integer getId_year() {
        return id_year;
    }

    public void setId_year(Integer id_year) {
        this.id_year = id_year;
    }
    
    public String getNameYear() {
        return nameYear;
    }
    
    public void setNameYear(String nameYear) {
        this.nameYear = nameYear;
    }
    
    public Integer getId_section() {
        return id_section;
    }

    public void setId_section(Integer id_section) {
        this.id_section = id_section;
    }
    
    public String getNameSection() {
        return nameSection;
    }
    
    public void setNameSection(String nameSection) {
        this.nameSection = nameSection;
    }

    
    @Override
    public String toString() {
        String data = "[id_student: " + this.id_student;
        data += ", names: " + this.names;
        data += ", surname: " + this.surname;
        data += ", dni: " + this.dni;
        data += ", direction: " + this.direction;
        data += ", email: " + this.email;
        data += ", cell_phone: " + this.cell_phone;
        data += ", states: " + this.states;
        data += ", id_year: " + this.id_year;
        data += ", id_year: " + this.nameYear;
        data += ", id_section: " + this.id_section;
        data += ", nameSection: " + this.nameSection + "]";
        return data;
    }
}
