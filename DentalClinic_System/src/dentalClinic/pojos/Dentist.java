package dentalClinic.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//A POJO has... 
public class Dentist implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6511392629863269824L;
	private Integer id;
	private String name;
	private String surname;
	private String turn;
	private String specialty;
	private List<Patient> patients;
	private List<Appointment> appointments;
	
	public Dentist() {
		super();
		patients=new ArrayList<Patient>();
		appointments=new ArrayList<Appointment>();
	}
	
	public Dentist(Integer id, String name, String surname, String turn, String specialty) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.turn = turn;
		this.specialty = specialty;
	}
	
	

	public Dentist(String name, String surname, String turn, String specialty) {
		super();
		this.name = name;
		this.surname = surname;
		this.turn = turn;
		this.specialty = specialty;
	}

	public Dentist(Integer id, String turn, String specialty) {
		super();
		this.id = id;
		this.turn = turn;
		this.specialty = specialty;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getTurn() {
		return turn;
	}

	public void setTurn(String turn) {
		this.turn = turn;
	}

	public String getSpeciality() {
		return specialty;
	}

	public void setSpeciality(String specialty) {
		this.specialty = specialty;
	}

	public List<Patient> getPatients() {
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}
	

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dentist other = (Dentist) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Dentist [id=" + id + ", name=" + name + ", surname=" + surname + ", turn=" + turn + ", speciality="
				+ specialty + ", patients=" + patients + "]";
	}

	
	
	
}
