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
	private String speciality;
	private List<Patient> patients;
	
	public Dentist() {
		super();
		patients=new ArrayList<Patient>();
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
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public List<Patient> getPatients() {
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
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
				+ speciality + ", patients=" + patients + "]";
	}
	
	
	
}
