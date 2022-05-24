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
	private Integer dentistId;
	private String name;
	private String surname;
	private String turn;
	private String specialty;
	private List<Patient> patients;
	private List<Appointment> appointments;
	private Integer userId;
	
	public Dentist() {
		super();
		patients=new ArrayList<Patient>();
		appointments=new ArrayList<Appointment>();
	}
	
	public Dentist(Integer id, String name, String surname, String turn, String specialty) {
		super();
		this.dentistId = id;
		this.name = name;
		this.surname = surname;
		this.turn = turn;
		this.specialty = specialty;
	}
	
	public Dentist (Dentist d) {
		this.setName(d.getName());
		this.setSurname(d.getSurname());
		this.setTurn(d.getTurn());
		this.setSpeciality(d.getSpecialty());
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
		this.dentistId = id;
		this.turn = turn;
		this.specialty = specialty;
	}

	public Integer getId() {
		return dentistId;
	}

	public void setId(Integer id) {
		this.dentistId = id;
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

	public String getSpecialty() {
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
		return Objects.hash(dentistId);
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
		return Objects.equals(dentistId, other.dentistId);
	}

	@Override
	public String toString() {
		return "Dentist [id=" + dentistId + ", name=" + name + ", surname=" + surname + ", turn=" + turn + ", speciality="
				+ specialty + ", patients=" + patients + "]\n";
	}

	
	
	
}
