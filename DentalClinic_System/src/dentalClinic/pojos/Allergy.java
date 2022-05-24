package dentalClinic.pojos;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Allergy implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6526834759252802852L;
	
	private Integer allergyId;
	private String name;
	private List<Patient> patients;
	
	public Allergy(Integer allergyId, String name) {
		super();
		this.allergyId = allergyId;
		this.name = name;
	}
	
	public Allergy(String name) {
		super();
		this.name = name;
	}
	
	
	
	public Allergy(Integer allergyId, String name, List<Patient> patients) {
		super();
		this.allergyId = allergyId;
		this.name = name;
		this.patients = patients;
	}

	public Allergy(String name, List<Patient> patients) {
		super();
		this.name = name;
		this.patients = patients;
	}

	public Allergy() {
		super();
	}
	public Integer getAllergyId() {
		return allergyId;
	}
	public void setAllergyId(Integer allergyId) {
		this.allergyId = allergyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Patient> getPatients() {
		return patients;
	}
	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(allergyId, name, patients);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Allergy other = (Allergy) obj;
		return Objects.equals(allergyId, other.allergyId) && Objects.equals(name, other.name)
				&& Objects.equals(patients, other.patients);
	}
	@Override
	public String toString() {
		return "Allergy [allergyId=" + allergyId + ", name=" + name + ", patient=" + patients + "]";
	}
}
