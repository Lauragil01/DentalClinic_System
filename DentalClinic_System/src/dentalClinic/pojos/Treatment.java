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
	private String diagnosis;
	private Integer consultDuration;
	private Date startDate;
	private Date finishDate;
	 //one to many relationship
	private List<Patient> patients;
	private List<Medication> medications;
	
	public Treatment() {
		super();
		patients=new ArrayList<Patient>();
		setMedications(new ArrayList<Medication>());
	}
	public Treatment(int id, String diagnosis, int duration , Date startDate, Date finishDate) throws Exception {
		super();
		setId(id);
		setDiagnosis(diagnosis);
		setConsultDuration(duration);
		this.startDate= startDate;
		this.finishDate = finishDate;
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public List<Patient> getPatients() {
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
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
				+ consultDuration + ", startDate=" + startDate + ", finishDate=" + finishDate + ", patients=" + patients
				+ ", medications=" + medications + "]";
	}
	
	
	
}
