package dentalClinic.pojos;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Treatment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2391513895154656638L;
	private Integer id;
	private String name;
	private String diagnosis;
	private Integer consultDuration;
	private Date startDate;
	private Date finishDate;
	//many to one relationship
	private Patient patient;
	private List<Medication> medications;
	
	public Treatment() {
		super();
		setMedications(new ArrayList<Medication>());
	}
	public Treatment(int id, String name, String diagnosis, int duration , Date startDate, Date finishDate){
		super();
		setId(id);
		setName(name);
		setDiagnosis(diagnosis);
		setConsultDuration(duration);
		this.startDate= startDate;
		this.finishDate = finishDate;
	}
	
	public Treatment(Integer id, String name, String diagnosis, Integer consultDuration, Date startDate, Date finishDate,
			Patient patient) {
		super();
		this.id = id;
		this.name = name;
		this.diagnosis = diagnosis;
		this.consultDuration = consultDuration;
		this.startDate = startDate;
		this.finishDate = finishDate;
		this.patient = patient;
	}
	public Treatment(String name, String diagnosis, Integer consultDuration, Date startDate, Date finishDate,
			Patient p) {
		super();
		this.name = name;
		this.diagnosis = diagnosis;
		this.consultDuration = consultDuration;
		this.startDate = startDate;
		this.finishDate = finishDate;
		this.patient = patient;
	}
	public Treatment(String name, String diagnosis, Integer consultDuration, Date startDate, Date finishDate) {
		super();
		this.name = name;
		this.diagnosis = diagnosis;
		this.consultDuration = consultDuration;
		this.startDate = startDate;
		this.finishDate = finishDate;
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
	
	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public Integer getConsultDuration() {
		return consultDuration;
	}

	public void setConsultDuration(Integer consultDuration) {
		this.consultDuration = consultDuration;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public List<Medication> getMedications() {
		return medications;
	}

	public void setMedications(List<Medication> medications) {
		this.medications = medications;
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
		Treatment other = (Treatment) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Treatment [id=" + id + ", diagnosis=" + diagnosis + ", consultDuration="
				+ consultDuration + ", startDate=" + startDate + ", finishDate=" + finishDate + ", patientId="
				+ ", medications=" + medications + "]";
	}	
}
