package dentalClinic.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name = "Dentist")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "name", "specialty", "appointment"})

public class Dentist implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6511392629863269824L;

	
	@XmlTransient
	private Integer id;
	 
	@XmlAttribute
	private Integer dentistId;
	private String name;
	@XmlAttribute
	private String surname;
	@XmlElement
	private String turn;
	@XmlElement
	private String specialty;
	@XmlElement(name = "Patient")
    @XmlElementWrapper(name = "Patients")
	private List<Patient> patients=null;
	@XmlElement(name = "Appointment")
    @XmlElementWrapper(name = "Appointments")
	private List<Appointment> appointments=null;
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
				+ specialty + ", patients=" + patients + "]";
	}

	
	
	
}
