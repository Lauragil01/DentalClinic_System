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
	private Integer patientId;
	private String name;
	private String surname;
	private String gender;
	private Date birthDate;
	private String address;
	private String bloodType;
	private String background;
	private List<Allergy> allergies;
	private List<Treatment> treatments;  //one to many relationship
	private List<Dentist> dentists; 	//many to many relationship
	private List<Appointment> appointments;
	private Integer userId;
	
	public Patient(Integer id, String name, String surname, String gender, Date birthDate, String address,
			String bloodType, String background) {
		super();
		this.patientId = id;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.birthDate = birthDate;
		this.address = address;
		this.bloodType = bloodType;
		this.background = background;
		//this.allergies = new ArrayList<Allergy>();
		//this.treatments = new ArrayList<Treatment>();
	}
	

	public Patient (Patient p) {
		this.setName(p.getName());
		this.setSurname(p.getSurname());
		this.setGender(p.getGender());
		this.setBithDate(p.getBithDate());
		this.setAddress(p.getAddress());
		this.setBloodType(p.getBloodType());
		this.setBackground(p.getBackground());
	}
	
	public Patient(Integer id, String name, String surname, String gender, String address, String bloodType,
			String background) {
		super();
		this.patientId = id;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.address = address;
		this.bloodType = bloodType;
		this.background = background;
	}

	public Patient(String name, String surname, String gender, String address, String bloodType,
			String background, List<Allergy> allergies) {
		super();
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.address = address;
		this.bloodType = bloodType;
		this.background = background;
		this.allergies = allergies;
	}

	public Patient(Integer id, String name, String surname, String gender, String address, String bloodType,
			String background, List<Allergy> allergies) {
		super();
		this.patientId = id;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.address = address;
		this.bloodType = bloodType;
		this.background = background;
		this.allergies = allergies;
	}

	public Patient(Integer id, String name, String surname, String gender, Date date, String address, String bloodType,
			String background, List<Allergy> allergies) {
		super();
		this.patientId = id;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.birthDate = date;
		this.address = address;
		this.bloodType = bloodType;
		this.background = background;
		this.allergies = allergies;
	}

	//empty constructor
	public Patient() {
		super();
		allergies= new ArrayList<Allergy>();
		dentists=new ArrayList<Dentist>();
		appointments=new ArrayList<Appointment>();
		treatments=new ArrayList<Treatment>();
	}
	
	public Patient (int id, String name, String surname, String gender, Date birthDate, 
			String address, String bloodType, ArrayList<Allergy> allergies, String background) {
		super();
		this.patientId = id;
		this.name = name; 
		this.surname = surname;
		this.gender = gender;
		this.birthDate = birthDate; 
		this.address = address;
		this.bloodType = bloodType;
		this.background = background;
		allergies = new ArrayList<Allergy>();
		dentists=new ArrayList<Dentist>();
		appointments=new ArrayList<Appointment>();
		treatments=new ArrayList<Treatment>();			
	}
	
	
	
	public Patient(Integer id, String name, String surname, String gender, Date birthDate, String address,
			String bloodType, List<Allergy> allergies, String background) {
		super();
		this.patientId = id;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.birthDate = birthDate;
		this.address = address;
		this.bloodType = bloodType;
		this.allergies = allergies;
		this.background = background;
	}

	public Patient (String name, String surname, String gender, Date birthDate, 
			String address, String bloodType, String background) {
		super();
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.birthDate = birthDate; 
		this.address = address;
		this.bloodType = bloodType;
		this.background = background;
		allergies = new ArrayList<Allergy>();
		dentists=new ArrayList<Dentist>();
		appointments=new ArrayList<Appointment>();
		treatments=new ArrayList<Treatment>();			
	}
	

	//equals
	@Override
	public int hashCode() {
		return Objects.hash(patientId);
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
		return Objects.equals(patientId, other.patientId);
	}
	
	@Override
	public String toString() {
		return ("\nId: " + patientId + "\nName: " + name + "\nSurname: " + surname + "\nGender: " + gender + "\nBirthDate: "
				+ birthDate + "\nAddress: " + address + "\nBloodType: " + bloodType + "\nAllergies: " + allergies
				+ "\nBackground: " + background + "\n");
	}
	
	//getters and setters
	public Integer getId() {
		return patientId;
	}
	public void setId(Integer id) {
		this.patientId = id;
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
	public List<Allergy> getAllergies() {
		return allergies;
	}
	public void setAllergies(List<Allergy> allergies) {
		this.allergies = allergies;
	}
	public String getBackground() {
		return background;
	}
	public void setBackground(String background) {
		this.background = background;
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
