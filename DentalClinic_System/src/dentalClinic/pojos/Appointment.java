package dentalClinic.pojos;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import dentalClinic.xml.utils.SQLDateAdapter;


@XmlRootElement(name = "Appointment")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"date","type","duration","time"})
public class Appointment implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4272748927970790147L;
	
	
	//we make the id transient to be able to import data from a XML file
	@XmlTransient
	private Integer id;
	@XmlElement
	@XmlJavaTypeAdapter(SQLDateAdapter.class)
	private Date date;
	@XmlElement
	private String type;
	@XmlElement
	private Integer duration;
	@XmlElement
	private Time time;
	@XmlTransient
	private Dentist dentist;
	@XmlTransient
	private Patient patient;
	
	public Appointment() {
		super();
	}
	
	public Appointment(Integer id, Date date, String type, Integer duration, Time time) {
		super();
		this.id = id;
		this.date = date;
		this.type = type;
		this.duration = duration;
		this.time = time;
	}
	
	
	
	public Appointment(Date date, String type, Integer duration, Time time, Dentist dentist) {
		super();
		this.date = date;
		this.type = type;
		this.duration = duration;
		this.time = time;
		this.dentist = dentist;
	}

	public Appointment(Integer id, String type, Integer duration, Time time, Dentist dentist) {
		super();
		this.id = id;
		this.type = type;
		this.duration = duration;
		this.time = time;
		this.dentist = dentist;
	}

	public Appointment(String type, Integer duration, Dentist dentist, Patient patient) {
		super();
		this.type = type;
		this.duration = duration;
		this.dentist = dentist;
		this.patient = patient;
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
		Appointment other = (Appointment) obj;
		return Objects.equals(id, other.id);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public Dentist getDentist() {
		return dentist;
	}

	public void setDentist(Dentist dentist) {
		this.dentist = dentist;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@Override
	public String toString() {
		return "Appointment [id=" + id + ", date=" + date + ", type=" + type + ", duration=" + duration + ", time="
				+ time + ", dentist=" + dentist + ", patient=" + patient + "]";
	}
	
	
	
}
