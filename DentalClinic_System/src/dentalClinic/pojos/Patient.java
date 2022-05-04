package dentalClinic.pojos;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Patient implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3821302205864515536L;
	 //atributes of the pojo
	private Integer id;
	private String name;
	private String surname;
	private String gender;
	private Date birthDate;
	private String address;
	private String bloodType; //deberia ser enum??
	private String allergies;
	private String background;
	//one to many relationship
	private List<Treatment> treatments;
	//many to many relationship
	private List<Dentist> dentists;
	private List<Appointment> appointments;
	
	//empty constructor
	public Patient() {
		super();
		dentists=new ArrayList<Dentist>();
		appointments=new ArrayList<Appointment>();
		treatments=new ArrayList<Treatment>();
	}
	
	public Patient (int id, String name, String surname, String gender, Date birthDate, 
			String address, String bloodType, String allergies, String background) {
		super();
		this.id = id;
		this.name = name; 
		this.surname = surname;
		this.gender = gender;
		this.birthDate = birthDate; 
		this.address = address;
		this.bloodType = bloodType;
		this.allergies = allergies; 
		this.background = background;
		dentists=new ArrayList<Dentist>();
		appointments=new ArrayList<Appointment>();
		treatments=new ArrayList<Treatment>();			
	}
	
	public Patient (String name, String surname, String gender, Date birthDate, 
			String address, String bloodType, String allergies, String background) {
		super();
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.birthDate = birthDate; 
		this.address = address;
		this.bloodType = bloodType;
		this.allergies = allergies; 
		this.background = background;
		dentists=new ArrayList<Dentist>();
		appointments=new ArrayList<Appointment>();
		treatments=new ArrayList<Treatment>();			
	}
	
	//equals
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
		Patient other = (Patient) obj;
		return Objects.equals(id, other.id);
	}
	
	@Override
	public String toString() {
		return "Patient [id=" + id + ", name=" + name + ", surname=" + surname + ", gender=" + gender + ", bithDate="
				+ birthDate + ", address=" + address + ", bloodType=" + bloodType + ", allergies=" + allergies
				+ ", blackground=" + background + ", treatments=" + treatments + ", dentists=" + dentists
				+ ", appointments=" + appointments + "]";
	}
	
	//getters and setters
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getBithDate() {
		return birthDate;
	}
	public void setBithDate(Date bithDate) {
		this.birthDate = bithDate;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBloodType() {
		return bloodType;
	}
	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}
	public String getAllergies() {
		return allergies;
	}
	public void setAllergies(String allergies) {
		this.allergies = allergies;
	}
	public String getBlackground() {
		return background;
	}
	public void setBlackground(String blackground) {
		this.background = blackground;
	}

	public List<Dentist> getDentists() {
		return dentists;
	}

	public void setDentists(List<Dentist> dentists) {
		this.dentists = dentists;
	}

	public List<Treatment> getTreatments() {
		return treatments;
	}

	public void setTreatments(List<Treatment> treatments) {
		this.treatments = treatments;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}
	
	

}
